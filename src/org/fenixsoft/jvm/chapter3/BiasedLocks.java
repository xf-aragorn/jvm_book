package org.fenixsoft.jvm.chapter3;


import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.locks.LockSupport;
import java.util.stream.Stream;

public class BiasedLocks {
    //jdk 8优化，synchronized同步线程会优先使用偏向锁
    private static synchronized void contend() {
        LockSupport.parkNanos(100_000);
    }

    // Run with: -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDetails
    // Notice that there are a lot of stop the world pauses, but no actual garbage collections
    // This is because PrintGCApplicationStoppedTime actually shows all the STW pauses

    // To see what's happening here, you may use the following arguments:
    // -XX:+PrintSafepointStatistics  -XX:PrintSafepointStatisticsCount=1
    // It will reveal that all the safepoints are due to biased lock revocations.

    // Biased locks are on by default, but you can disable them by -XX:-UseBiasedLocking
    // It is quite possible that in the modern massively parallel world, they should be
    // turned back off by default
    public class SimpleInt {
        private int state;
    }
    public static class SSimpleInt {
        private int state;
    }
    public class SimpleLong {
        private long state;
    }
    public static class Lock {}
    public class FieldsArrangement {
        private boolean first;
        private char second;
        private double third;
        private int fourth;
        private boolean fifth;
    }
    public static void main(String[] args) throws InterruptedException {
        System.out.println(ClassLayout.parseClass(SimpleInt.class).toPrintable());
        System.out.println(ClassLayout.parseClass(SSimpleInt.class).toPrintable());
        System.out.println(VM.current().details());
        SSimpleInt instance = new SSimpleInt();
        System.out.println(ClassLayout.parseInstance(instance).toPrintable());
        System.out.println("The identity hash code is " + System.identityHashCode(instance));
        System.out.println(ClassLayout.parseInstance(instance).toPrintable());
        System.out.println(ClassLayout.parseClass(SimpleLong.class).toPrintable());
        System.out.println(ClassLayout.parseClass(FieldsArrangement.class).toPrintable());
        Lock lock = new Lock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }

        Thread.sleep(5_000); // Because of BiasedLockingStartupDelay

        System.out.println(ClassLayout.parseClass(SimpleLong.class).toPrintable());
        Stream.generate(() -> new Thread(BiasedLocks::contend))
                .limit(10)
                .forEach(Thread::start);

        Thread.sleep(5_000_000); // Because of BiasedLockingStartupDelay
    }

}

/*
C:\Users\xufeng\.jdks\corretto-1.8.0_342\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:54043,suspend=y,server=n -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=10 -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDetails -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -XX:+UseBiasedLocking -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\charsets.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\access-bridge-64.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\cldrdata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\dnsns.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\jaccess.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\jfxrt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\localedata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\nashorn.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunec.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunjce_provider.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunmscapi.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunpkcs11.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\zipfs.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jce.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jfr.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jfxswt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jsse.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\management-agent.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\resources.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\rt.jar;D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.BiasedLocks
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.082: ChangeBreakpoints                [       8          2              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0003246 seconds, Stopping threads took: 0.0002562 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.082: ForceSafepoint                   [       8          0              1    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000722 seconds, Stopping threads took: 0.0000363 seconds
Connected to the target VM, address: '127.0.0.1:54043', transport: 'socket'
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.195: ChangeBreakpoints                [       8          1              1    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001029 seconds, Stopping threads took: 0.0000393 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.197: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000822 seconds, Stopping threads took: 0.0000186 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
1.211: no vm operation                  [      12          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001260 seconds, Stopping threads took: 0.0000276 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
4.254: EnableBiasedLocking              [      12          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001725 seconds, Stopping threads took: 0.0000354 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.284: RevokeBias                       [      19          2              3    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0003074 seconds, Stopping threads took: 0.0002399 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.285: RevokeBias                       [      21          2              2    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001917 seconds, Stopping threads took: 0.0001546 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.286: RevokeBias                       [      22          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000570 seconds, Stopping threads took: 0.0000236 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.302: RevokeBias                       [      21          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001383 seconds, Stopping threads took: 0.0000482 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.317: RevokeBias                       [      20          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001131 seconds, Stopping threads took: 0.0000402 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.332: RevokeBias                       [      19          0              2    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0002467 seconds, Stopping threads took: 0.0001144 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.348: RevokeBias                       [      18          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001495 seconds, Stopping threads took: 0.0000525 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.364: RevokeBias                       [      17          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001362 seconds, Stopping threads took: 0.0000498 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.379: RevokeBias                       [      16          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001204 seconds, Stopping threads took: 0.0000465 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.395: RevokeBias                       [      15          0              1    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001794 seconds, Stopping threads took: 0.0000924 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.411: RevokeBias                       [      14          0              1    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001650 seconds, Stopping threads took: 0.0000748 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.426: RevokeBias                       [      13          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001381 seconds, Stopping threads took: 0.0000527 seconds
Heap
 PSYoungGen      total 73728K, used 10175K [0x000000076e000000, 0x0000000773200000, 0x00000007c0000000)
  eden space 63488K, 16% used [0x000000076e000000,0x000000076e9eff00,0x0000000771e00000)
  from space 10240K, 0% used [0x0000000772800000,0x0000000772800000,0x0000000773200000)
  to   space 10240K, 0% used [0x0000000771e00000,0x0000000771e00000,0x0000000772800000)
 ParOldGen       total 167936K, used 0K [0x00000006ca000000, 0x00000006d4400000, 0x000000076e000000)
  object space 167936K, 0% used [0x00000006ca000000,0x00000006ca000000,0x00000006d4400000)
 Metaspace       used 4189K, capacity 4682K, committed 4864K, reserved 1056768K
  class space    used 457K, capacity 502K, committed 512K, reserved 1048576K
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
5.428: no vm operation                  [      11          0              1    ]      [     0     0     0     0     0    ]  0

Polling page always armed
ForceSafepoint                     1
EnableBiasedLocking                1
RevokeBias                        12
ChangeBreakpoints                  3
    0 VM operations coalesced during safepoint
Maximum sync time      0 ms
Maximum vm operation time (except for Exit VM operation)      0 ms
Disconnected from the target VM, address: '127.0.0.1:54043', transport: 'socket'

Process finished with exit code 0

 */
