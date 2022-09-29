package org.fenixsoft.jvm.chapter3;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class SafePointTest {
//        private static volatile int num = 0;
    public static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        /* lambda表达式导致JIT优化退回？
        <deoptimized thread='11332' reason='constraint' pc='0x000001d108c8929b' compile_id='259' compile_kind='osr' compiler='c1' level='3'>
            <jvms bci='108' method='org.fenixsoft.jvm.chapter3.SafePointTest lambda$main$0 ()V' bytes='142' count='2' backedge_count='1925976' iicount='2' decompiles='1' unstable_if_traps='2'/>
        </deoptimized>
         */

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName()+" 开始！");

            for (int i = 0; i < 200000000; i++) {
                if(i%10000000 == 0){
//                    System.out.println(new Date().toString()+" "+Thread.currentThread().getName()+" 循环到:"+i);//这个输出会影响jdk8下的安全点，会使线程在安全点停下来，因为这里调用了别的方法，在每个方法的结束位置有安全点
                }
                num.getAndAdd(1);
//                num++;
            }
            System.out.println(Thread.currentThread().getName()+" 结束！");
        };
        Thread t1 = new Thread(runnable, "测试01线程");
        Thread t2 = new Thread(runnable, "测试02线程");
        t1.start();
        t2.start();
        System.out.println("主线程开始睡觉");
        //记录睡眠时间
        long start = System.currentTimeMillis();
        Thread.sleep(1000);

        System.out.println("睡醒了, 一共睡了 : " + (System.currentTimeMillis() - start) + " 毫秒");
        System.out.println("打印一下num 看看结果是多少: " + num);
    }
}

/*
// for循环中参数i是int型
 C:\Users\xufeng\.jdks\corretto-1.8.0_312\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53217,suspend=y,server=n -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\charsets.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\access-bridge-64.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\cldrdata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\dnsns.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\jaccess.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\jfxrt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\localedata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\nashorn.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunec.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunjce_provider.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunmscapi.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunpkcs11.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\zipfs.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jce.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jfr.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jfxswt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jsse.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\management-agent.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\resources.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\rt.jar;D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
 Connected to the target VM, address: '127.0.0.1:53217', transport: 'socket'
 主线程开始睡觉
 测试01线程结束！
 测试02线程结束！
 睡醒了, 一共睡了 : 3595 毫秒
 打印一下num 看看结果是多少: 400000000
 */

/*
C:\Users\xufeng\.jdks\corretto-1.8.0_342\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53947,suspend=y,server=n -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=10 -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -XX:-UseBiasedLocking -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\charsets.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\access-bridge-64.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\cldrdata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\dnsns.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\jaccess.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\jfxrt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\localedata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\nashorn.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunec.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunjce_provider.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunmscapi.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunpkcs11.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\zipfs.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jce.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jfr.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jfxswt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jsse.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\management-agent.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\resources.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\rt.jar;D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.088: ForceSafepoint                   [       7          0              1    ]      [     0     0     0     0     0    ]  0
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.088: ChangeBreakpoints                [       8          1              0    ]      [     7     0     7     0     0    ]  0
Connected to the target VM, address: '127.0.0.1:53947', transport: 'socket'
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.190: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.191: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
主线程开始睡觉
测试02线程 开始！
测试01线程 开始！
//以下是加了这二个参数后的效果： -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=10
# SafepointSynchronize::begin: Timeout detected:
# SafepointSynchronize::begin: Timed out while spinning to reach a safepoint.
# SafepointSynchronize::begin: Threads which did not reach the safepoint:
# "测试02线程" #14 prio=5 os_prio=0 tid=0x0000012a5e4c9c80 nid=0x2a6c runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

# "测试01线程" #13 prio=5 os_prio=0 tid=0x0000012a5e4c9850 nid=0x2c3c runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

# SafepointSynchronize::begin: (End of list)
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
1.200: no vm operation                  [      14          2              2    ]      [  2657     0  2658     0     0    ]  0
测试02线程 结束！
测试01线程 结束！
睡醒了, 一共睡了 : 3562 毫秒
打印一下num 看看结果是多少: 400000000
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
3.860: no vm operation                  [       9          0              1    ]      [     0     0     0     0     0    ]  0
//no vm operation （jdk13以后改为 Cleanup） ： 表示由jvm自己触发进入安全点，默认每秒触发一次，但是由于二个线程都不能进入安全点，所以中间有2秒多没有进入安全点。
Polling page always armed
ForceSafepoint                     1
ChangeBreakpoints                  3
    0 VM operations coalesced during safepoint
Maximum sync time   2658 ms
Maximum vm operation time (except for Exit VM operation)      0 ms
Disconnected from the target VM, address: '127.0.0.1:53947', transport: 'socket'

Process finished with exit code 0

 */



/*
// 在jdk11下，参数i是int类型，也不会导致长时间的STW
C:\Users\xufeng\.jdks\corretto-11.0.16.1\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53418,suspend=y,server=n -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
Connected to the target VM, address: '127.0.0.1:53418', transport: 'socket'
主线程开始睡觉
睡醒了, 一共睡了 : 1012 毫秒
打印一下num 看看结果是多少: 121688217
测试02线程结束！
测试01线程结束！
*/

/*
-XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1

C:\Users\xufeng\.jdks\corretto-1.8.0_312\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53571,suspend=y,server=n -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\charsets.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\access-bridge-64.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\cldrdata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\dnsns.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\jaccess.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\jfxrt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\localedata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\nashorn.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunec.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunjce_provider.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunmscapi.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunpkcs11.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\zipfs.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jce.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jfr.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jfxswt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jsse.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\management-agent.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\resources.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\rt.jar;D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.079: ForceSafepoint                   [       8          1              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001232 seconds, Stopping threads took: 0.0000676 seconds
Application time: 0.0000455 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.080: ChangeBreakpoints                [       8          1              1    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000512 seconds, Stopping threads took: 0.0000139 seconds
Connected to the target VM, address: '127.0.0.1:53571', transport: 'socket'
Application time: 0.0865399 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.166: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000592 seconds, Stopping threads took: 0.0000190 seconds
Application time: 0.0022680 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.168: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000626 seconds, Stopping threads took: 0.0000176 seconds
主线程开始睡觉
Application time: 1.0042065 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
1.183: no vm operation                  [      14          2              2    ]      [  2509     0  2509     0     0    ]  0
Total time for which application threads were stopped: 2.5094208 seconds, Stopping threads took: 2.5093019 seconds
测试02线程结束！
睡醒了, 一共睡了 : 3429 毫秒
测试01线程结束！
打印一下num 看看结果是多少: 400000000
Application time: 0.0017610 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
3.694: no vm operation                  [      11          0              1    ]      [     0     0     0     0     0    ]  0

Polling page always armed
ForceSafepoint                     1
ChangeBreakpoints                  3
    0 VM operations coalesced during safepoint
Maximum sync time   2509 ms
Maximum vm operation time (except for Exit VM operation)      0 ms
Disconnected from the target VM, address: '127.0.0.1:53571', transport: 'socket'
 */

/*
// 把循环的参数i从int类型改为long类型：
C:\Users\xufeng\.jdks\corretto-1.8.0_312\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53663,suspend=y,server=n -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\charsets.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\access-bridge-64.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\cldrdata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\dnsns.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\jaccess.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\jfxrt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\localedata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\nashorn.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunec.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunjce_provider.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunmscapi.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunpkcs11.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\zipfs.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jce.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jfr.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jfxswt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jsse.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\management-agent.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\resources.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\rt.jar;D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
Connected to the target VM, address: '127.0.0.1:53663', transport: 'socket'
主线程开始睡觉
睡醒了, 一共睡了 : 1006 毫秒
打印一下num 看看结果是多少: 122849181
测试01线程结束！
测试02线程结束！

---------- 以下是明细参数输出
C:\Users\xufeng\.jdks\corretto-1.8.0_312\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53639,suspend=y,server=n -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\charsets.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\access-bridge-64.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\cldrdata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\dnsns.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\jaccess.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\jfxrt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\localedata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\nashorn.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunec.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunjce_provider.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunmscapi.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\sunpkcs11.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\ext\zipfs.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jce.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jfr.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jfxswt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\jsse.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\management-agent.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\resources.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_312\jre\lib\rt.jar;D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.086: ForceSafepoint                   [       7          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000710 seconds, Stopping threads took: 0.0000141 seconds
Application time: 0.0001578 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.086: ChangeBreakpoints                [       8          1              1    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0001075 seconds, Stopping threads took: 0.0000719 seconds
Connected to the target VM, address: '127.0.0.1:53639', transport: 'socket'
Application time: 0.0799066 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.166: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000652 seconds, Stopping threads took: 0.0000190 seconds
Application time: 0.0012768 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.167: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
Total time for which application threads were stopped: 0.0000641 seconds, Stopping threads took: 0.0000175 seconds
主线程开始睡觉
Application time: 0.9938907 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
1.171: no vm operation                  [      14          2              2    ]      [     0     0     0     0     0    ]  2
Total time for which application threads were stopped: 0.0001648 seconds, Stopping threads took: 0.0000771 seconds
睡醒了, 一共睡了 : 1007 毫秒
打印一下num 看看结果是多少: 121937573
测试02线程结束！
测试01线程结束！
Application time: 2.5113170 seconds
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
3.683: no vm operation                  [      11          0              1    ]      [     0     0     0     0     0    ]  0

Polling page always armed
ForceSafepoint                     1
ChangeBreakpoints                  3
    0 VM operations coalesced during safepoint
Maximum sync time      0 ms
Maximum vm operation time (except for Exit VM operation)      0 ms
 */


/*
加参数：   -Xlog:gc*=info::time,tags,tid -Xlog:safepoint=info::time,tags,tid
C:\Users\xufeng\.jdks\corretto-11.0.16.1\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53465,suspend=y,server=n "-Xlog:gc*=info::time,tags,tid" -Xlog:safepoint=info::time,tags,tid -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
[2022-09-18T16:52:46.244+0800][1680][gc,heap] Heap region size: 1M
[2022-09-18T16:52:46.249+0800][1680][gc     ] Using G1
[2022-09-18T16:52:46.249+0800][1680][gc,heap,coops] Heap address: 0x000000070a000000, size: 3936 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
[2022-09-18T16:52:46.359+0800][8260][safepoint    ] Entering safepoint region: ChangeBreakpoints
[2022-09-18T16:52:46.359+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:46.359+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0051300 seconds, Stopping threads took: 0.0049065 seconds
[2022-09-18T16:52:46.359+0800][8260][safepoint    ] Application time: 0.0001641 seconds
[2022-09-18T16:52:46.359+0800][8260][safepoint    ] Entering safepoint region: ThreadsSuspendJVMTI
[2022-09-18T16:52:46.359+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:46.359+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0000431 seconds, Stopping threads took: 0.0000078 seconds
Connected to the target VM, address: '127.0.0.1:53465', transport: 'socket'
[2022-09-18T16:52:46.407+0800][8260][safepoint    ] Application time: 0.0480042 seconds
[2022-09-18T16:52:46.407+0800][8260][safepoint    ] Entering safepoint region: ChangeBreakpoints
[2022-09-18T16:52:46.407+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:46.407+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0001312 seconds, Stopping threads took: 0.0000094 seconds
[2022-09-18T16:52:46.409+0800][8260][safepoint    ] Application time: 0.0015981 seconds
[2022-09-18T16:52:46.409+0800][8260][safepoint    ] Entering safepoint region: ChangeBreakpoints
[2022-09-18T16:52:46.409+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:46.409+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0000963 seconds, Stopping threads took: 0.0000105 seconds
[2022-09-18T16:52:46.418+0800][8260][safepoint    ] Application time: 0.0089680 seconds
[2022-09-18T16:52:46.418+0800][8260][safepoint    ] Entering safepoint region: EnableBiasedLocking
[2022-09-18T16:52:46.418+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:46.418+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0001517 seconds, Stopping threads took: 0.0000075 seconds
主线程开始睡觉
[2022-09-18T16:52:47.420+0800][8260][safepoint    ] Application time: 1.0017886 seconds
[2022-09-18T16:52:47.420+0800][8260][safepoint    ] Entering safepoint region: Cleanup
[2022-09-18T16:52:47.420+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:47.420+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0003280 seconds, Stopping threads took: 0.0002155 seconds
睡醒了, 一共睡了 : 1012 毫秒
打印一下num 看看结果是多少: 124344960
[2022-09-18T16:52:48.438+0800][8260][safepoint    ] Application time: 1.0176822 seconds
[2022-09-18T16:52:48.438+0800][8260][safepoint    ] Entering safepoint region: Cleanup
[2022-09-18T16:52:48.438+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:48.438+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0003796 seconds, Stopping threads took: 0.0002508 seconds
[2022-09-18T16:52:49.796+0800][8260][safepoint    ] Application time: 1.3569912 seconds
[2022-09-18T16:52:49.796+0800][8260][safepoint    ] Entering safepoint region: RevokeBias
[2022-09-18T16:52:49.796+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:49.796+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0001347 seconds, Stopping threads took: 0.0000457 seconds
[2022-09-18T16:52:49.796+0800][8260][safepoint    ] Application time: 0.0002273 seconds
[2022-09-18T16:52:49.796+0800][8260][safepoint    ] Entering safepoint region: RevokeBias
[2022-09-18T16:52:49.796+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:49.796+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0001325 seconds, Stopping threads took: 0.0000642 seconds
测试01线程结束！
[2022-09-18T16:52:49.800+0800][8260][safepoint    ] Application time: 0.0034472 seconds
[2022-09-18T16:52:49.800+0800][8260][safepoint    ] Entering safepoint region: RevokeBias
[2022-09-18T16:52:49.800+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:49.800+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0001189 seconds, Stopping threads took: 0.0000451 seconds
测试02线程结束！
[2022-09-18T16:52:49.810+0800][8260][safepoint    ] Application time: 0.0104778 seconds
[2022-09-18T16:52:49.810+0800][8260][safepoint    ] Entering safepoint region: RevokeBias
[2022-09-18T16:52:49.810+0800][8260][safepoint    ] Leaving safepoint region
[2022-09-18T16:52:49.810+0800][8260][safepoint    ] Total time for which application threads were stopped: 0.0000703 seconds, Stopping threads took: 0.0000063 seconds
[2022-09-18T16:52:49.811+0800][1680][gc,heap,exit ] Heap
[2022-09-18T16:52:49.811+0800][1680][gc,heap,exit ]  garbage-first heap   total 251904K, used 4096K [0x000000070a000000, 0x0000000800000000)
[2022-09-18T16:52:49.811+0800][1680][gc,heap,exit ]   region size 1024K, 5 young (5120K), 0 survivors (0K)
[2022-09-18T16:52:49.811+0800][1680][gc,heap,exit ]  Metaspace       used 5708K, capacity 5841K, committed 6016K, reserved 1056768K
[2022-09-18T16:52:49.811+0800][1680][gc,heap,exit ]   class space    used 495K, capacity 548K, committed 640K, reserved 1048576K
[2022-09-18T16:52:49.811+0800][8260][safepoint    ] Application time: 0.0008578 seconds
Disconnected from the target VM, address: '127.0.0.1:53465', transport: 'socket'
[2022-09-18T16:52:49.819+0800][8260][safepoint    ] Entering safepoint region: Halt

Process finished with exit code 0

 */


/*
//禁用偏向锁
// -Xlog:gc*=info::time,tags,tid -Xlog:safepoint=info::time,tags,tid -XX:-UseBiasedLocking
// 观察日志，是每秒钟都会进一次安全点，因为jvm默认会每秒置进入安全点的标记，jvm需要做一些内部清理。
C:\Users\xufeng\.jdks\corretto-11.0.16.1\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53789,suspend=y,server=n "-Xlog:gc*=info::time,tags,tid" -Xlog:safepoint=info::time,tags,tid -XX:-UseBiasedLocking -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
[2022-09-18T17:13:42.955+0800][15932][gc,heap] Heap region size: 1M
[2022-09-18T17:13:42.960+0800][15932][gc     ] Using G1
[2022-09-18T17:13:42.960+0800][15932][gc,heap,coops] Heap address: 0x000000070a000000, size: 3936 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
[2022-09-18T17:13:43.063+0800][6324 ][safepoint    ] Entering safepoint region: ThreadsSuspendJVMTI
[2022-09-18T17:13:43.063+0800][6324 ][safepoint    ] Leaving safepoint region
[2022-09-18T17:13:43.063+0800][6324 ][safepoint    ] Total time for which application threads were stopped: 0.0002175 seconds, Stopping threads took: 0.0000362 seconds
[2022-09-18T17:13:43.064+0800][6324 ][safepoint    ] Application time: 0.0003788 seconds
[2022-09-18T17:13:43.064+0800][6324 ][safepoint    ] Entering safepoint region: ChangeBreakpoints
[2022-09-18T17:13:43.064+0800][6324 ][safepoint    ] Leaving safepoint region
[2022-09-18T17:13:43.064+0800][6324 ][safepoint    ] Total time for which application threads were stopped: 0.0002687 seconds, Stopping threads took: 0.0001518 seconds
Connected to the target VM, address: '127.0.0.1:53789', transport: 'socket'
[2022-09-18T17:13:43.082+0800][6324 ][safepoint    ] Application time: 0.0178559 seconds
[2022-09-18T17:13:43.082+0800][6324 ][safepoint    ] Entering safepoint region: ChangeBreakpoints
[2022-09-18T17:13:43.082+0800][6324 ][safepoint    ] Leaving safepoint region
[2022-09-18T17:13:43.082+0800][6324 ][safepoint    ] Total time for which application threads were stopped: 0.0001039 seconds, Stopping threads took: 0.0000092 seconds
[2022-09-18T17:13:43.082+0800][6324 ][safepoint    ] Application time: 0.0004707 seconds
[2022-09-18T17:13:43.083+0800][6324 ][safepoint    ] Entering safepoint region: ChangeBreakpoints
[2022-09-18T17:13:43.083+0800][6324 ][safepoint    ] Leaving safepoint region
[2022-09-18T17:13:43.083+0800][6324 ][safepoint    ] Total time for which application threads were stopped: 0.0000913 seconds, Stopping threads took: 0.0000080 seconds
主线程开始睡觉
[2022-09-18T17:13:44.088+0800][6324 ][safepoint    ] Application time: 0.9963506 seconds
[2022-09-18T17:13:44.088+0800][6324 ][safepoint    ] Entering safepoint region: Cleanup
[2022-09-18T17:13:44.088+0800][6324 ][safepoint    ] Leaving safepoint region
[2022-09-18T17:13:44.088+0800][6324 ][safepoint    ] Total time for which application threads were stopped: 0.0001503 seconds, Stopping threads took: 0.0000890 seconds
睡醒了, 一共睡了 : 1015 毫秒
打印一下num 看看结果是多少: 125291644
[2022-09-18T17:13:45.098+0800][6324 ][safepoint    ] Application time: 1.0095836 seconds
[2022-09-18T17:13:45.098+0800][6324 ][safepoint    ] Entering safepoint region: Cleanup
[2022-09-18T17:13:45.098+0800][6324 ][safepoint    ] Leaving safepoint region
[2022-09-18T17:13:45.098+0800][6324 ][safepoint    ] Total time for which application threads were stopped: 0.0002016 seconds, Stopping threads took: 0.0001047 seconds
测试01线程结束！
测试02线程结束！
[2022-09-18T17:13:46.572+0800][15932][gc,heap,exit ] Heap
[2022-09-18T17:13:46.572+0800][15932][gc,heap,exit ]  garbage-first heap   total 251904K, used 5120K [0x000000070a000000, 0x0000000800000000)
[2022-09-18T17:13:46.572+0800][15932][gc,heap,exit ]   region size 1024K, 6 young (6144K), 0 survivors (0K)
[2022-09-18T17:13:46.572+0800][15932][gc,heap,exit ]  Metaspace       used 5705K, capacity 5841K, committed 6016K, reserved 1056768K
[2022-09-18T17:13:46.572+0800][15932][gc,heap,exit ]   class space    used 495K, capacity 548K, committed 640K, reserved 1048576K
[2022-09-18T17:13:46.573+0800][6324 ][safepoint    ] Application time: 1.4749088 seconds
[2022-09-18T17:13:46.573+0800][6324 ][safepoint    ] Entering safepoint region: Halt
 */


/*
C:\Users\xufeng\.jdks\corretto-11.0.16.1\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:57767,suspend=y,server=n -Xlog:jit+compilation=debug -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -XX:-UseBiasedLocking -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
OpenJDK 64-Bit Server VM warning: Option PrintSafepointStatistics was deprecated in version 11.0 and will likely be removed in a future release.
OpenJDK 64-Bit Server VM warning: Option PrintSafepointStatisticsCount was deprecated in version 11.0 and will likely be removed in a future release.
[0.059s][debug][jit,compilation]    1       3       java.lang.StringLatin1::hashCode (42 bytes)
[0.059s][debug][jit,compilation]    2       3       java.util.concurrent.ConcurrentHashMap::tabAt (22 bytes)
[0.061s][debug][jit,compilation]    3       3       java.lang.Object::<init> (1 bytes)
[0.062s][debug][jit,compilation]    4       3       java.lang.String::coder (15 bytes)
[0.062s][debug][jit,compilation]    5       3       java.lang.String::isLatin1 (19 bytes)
[0.062s][debug][jit,compilation]    6       3       java.lang.String::hashCode (49 bytes)
[0.063s][debug][jit,compilation]    7       3       java.lang.Math::floorMod (10 bytes)
[0.063s][debug][jit,compilation]    8       3       java.lang.Math::floorDiv (22 bytes)
[0.064s][debug][jit,compilation]    9       3       java.util.ImmutableCollections$SetN::probe (56 bytes)
[0.064s][debug][jit,compilation]   11       3       java.lang.StringLatin1::equals (36 bytes)
[0.066s][debug][jit,compilation]   10       3       java.lang.String::equals (65 bytes)
[0.066s][debug][jit,compilation]   12       3       java.util.ImmutableCollections$SetN::hashCode (46 bytes)
[0.069s][debug][jit,compilation]   15       4       java.lang.StringLatin1::hashCode (42 bytes)
[0.069s][debug][jit,compilation]   14       3       java.util.ImmutableCollections::emptySet (4 bytes)
[0.070s][debug][jit,compilation]   13       3       java.util.Objects::equals (23 bytes)
[0.072s][debug][jit,compilation]    1       3       java.lang.StringLatin1::hashCode (42 bytes)   made not entrant
[0.072s][debug][jit,compilation]   16       3       java.util.Objects::requireNonNull (14 bytes)
[0.074s][debug][jit,compilation]   19       4       java.lang.Object::<init> (1 bytes)
[0.074s][debug][jit,compilation]   17       3       java.util.Set::of (4 bytes)
[0.074s][debug][jit,compilation]   20       3       java.util.ImmutableCollections$AbstractImmutableCollection::<init> (5 bytes)
[0.075s][debug][jit,compilation]    3       3       java.lang.Object::<init> (1 bytes)   made not entrant
[0.075s][debug][jit,compilation]   18       3       java.util.AbstractCollection::<init> (5 bytes)
[0.075s][debug][jit,compilation]   21       3       java.lang.module.ModuleDescriptor$Exports::<init> (20 bytes)
[0.077s][debug][jit,compilation]   22       1       java.lang.module.ModuleDescriptor::name (5 bytes)
[0.077s][debug][jit,compilation]   23       3       java.lang.String::charAt (25 bytes)
[0.078s][debug][jit,compilation]   24       3       java.lang.StringLatin1::charAt (28 bytes)
[0.078s][debug][jit,compilation]   25       1       java.lang.module.ModuleReference::descriptor (5 bytes)
[0.082s][debug][jit,compilation]   26     n 0       java.lang.System::arraycopy (native)   (static)
[0.082s][debug][jit,compilation]   27       3       java.lang.StringLatin1::indexOf (61 bytes)
[0.084s][debug][jit,compilation]   28       3       java.lang.String::length (11 bytes)
[0.085s][debug][jit,compilation]   29       3       java.util.concurrent.ConcurrentHashMap::spread (10 bytes)
[0.085s][debug][jit,compilation]   30       3       java.util.ImmutableCollections$SetN$SetNIterator::hasNext (13 bytes)
[0.085s][debug][jit,compilation]   32       3       java.util.ImmutableCollections$SetN$SetNIterator::next (47 bytes)
[0.086s][debug][jit,compilation]   31       3       java.util.ImmutableCollections$SetN$SetNIterator::nextIndex (56 bytes)
[0.094s][debug][jit,compilation]   33     n 0       java.lang.invoke.MethodHandle::linkToStatic(LLLLLLL)L (native)   (static)
[0.096s][debug][jit,compilation]   34       1       java.lang.Enum::ordinal (5 bytes)
[0.096s][debug][jit,compilation]   35     n 0       java.lang.invoke.MethodHandle::linkToStatic(LL)I (native)   (static)
[0.096s][debug][jit,compilation]   36     n 0       java.lang.Object::hashCode (native)
[0.096s][debug][jit,compilation]   37   !   3       java.util.concurrent.ConcurrentHashMap::putVal (432 bytes)
[0.096s][debug][jit,compilation]   38     n 0       java.lang.invoke.MethodHandle::invokeBasic(LLLLLL)L (native)
[0.097s][debug][jit,compilation]   39     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LLLLLLLL)L (native)   (static)
[0.148s][debug][jit,compilation]   40     n 0       jdk.internal.misc.Unsafe::compareAndSetLong (native)
[0.151s][debug][jit,compilation]   41       1       java.lang.Class::getClassLoader0 (5 bytes)
[0.151s][debug][jit,compilation]   42       3       java.lang.Math::min (11 bytes)
[0.152s][debug][jit,compilation]   43     n 0       java.lang.invoke.MethodHandle::linkToStatic(LLL)L (native)   (static)
[0.152s][debug][jit,compilation]   44     n 0       java.lang.invoke.MethodHandle::linkToStatic(LLLL)L (native)   (static)
[0.153s][debug][jit,compilation]   45       1       java.lang.invoke.MethodType$ConcurrentWeakInternSet$WeakEntry::hashCode (5 bytes)
[0.153s][debug][jit,compilation]   46     n 0       java.lang.Class::isPrimitive (native)
[0.155s][debug][jit,compilation]   47       1       java.lang.invoke.MethodType::returnType (5 bytes)
[0.156s][debug][jit,compilation]   48     n 0       java.lang.invoke.MethodHandle::invokeBasic()L (native)
[0.156s][debug][jit,compilation]   49     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LL)L (native)   (static)
[0.157s][debug][jit,compilation]   50       3       java.lang.StringLatin1::canEncode (13 bytes)
[0.158s][debug][jit,compilation]   51       3       java.lang.invoke.MemberName::testFlags (16 bytes)
[0.158s][debug][jit,compilation]   52     n 0       java.lang.invoke.MethodHandle::linkToStatic(LLL)I (native)   (static)
[0.158s][debug][jit,compilation]   53       3       java.lang.invoke.MethodTypeForm::canonicalize (233 bytes)
[0.158s][debug][jit,compilation]   54     n 0       java.lang.invoke.MethodHandle::linkToStatic(LL)L (native)   (static)
[0.158s][debug][jit,compilation]   55     n 0       java.lang.invoke.MethodHandle::invokeBasic(L)L (native)
[0.159s][debug][jit,compilation]   56     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LLL)L (native)   (static)
[0.159s][debug][jit,compilation]   57       3       java.lang.ref.Reference::<init> (25 bytes)
[0.159s][debug][jit,compilation]   58     n 0       java.lang.invoke.MethodHandle::linkToVirtual(LL)L (native)   (static)
[0.159s][debug][jit,compilation]   59       4       java.lang.String::isLatin1 (19 bytes)
[0.159s][debug][jit,compilation]    5       3       java.lang.String::isLatin1 (19 bytes)   made not entrant
[0.161s][debug][jit,compilation]   60       3       java.lang.invoke.MemberName::isInvocable (7 bytes)
[0.161s][debug][jit,compilation]   63     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LL)V (native)   (static)
[0.161s][debug][jit,compilation]   61       3       java.lang.invoke.MemberName::testAnyFlags (15 bytes)
[0.161s][debug][jit,compilation]   65     n 0       java.lang.invoke.MethodHandle::linkToInterface(LLL)I (native)   (static)
[0.161s][debug][jit,compilation]   62   !   3       java.lang.ref.ReferenceQueue::poll (28 bytes)
[0.162s][debug][jit,compilation]   64       1       java.lang.invoke.MemberName::getDeclaringClass (5 bytes)
[0.162s][debug][jit,compilation]   66       3       java.lang.String::<init> (15 bytes)
[0.162s][debug][jit,compilation]   67       3       jdk.internal.org.objectweb.asm.ByteVector::putShort (52 bytes)
[0.163s][debug][jit,compilation]   68       3       jdk.internal.org.objectweb.asm.Item::set (219 bytes)
[0.163s][debug][jit,compilation]   70     n 0       jdk.internal.misc.Unsafe::compareAndSetObject (native)
[0.163s][debug][jit,compilation]   69       3       jdk.internal.org.objectweb.asm.ClassWriter::get (49 bytes)
[0.164s][debug][jit,compilation]   71       3       java.lang.ref.Reference::<init> (7 bytes)
[0.164s][debug][jit,compilation]   72       3       jdk.internal.org.objectweb.asm.ByteVector::putUTF8 (142 bytes)
[0.165s][debug][jit,compilation]   75     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LL)I (native)   (static)
[0.165s][debug][jit,compilation]   76       4       java.lang.String::charAt (25 bytes)
[0.165s][debug][jit,compilation]   73       3       java.lang.AbstractStringBuilder::ensureCapacityInternal (39 bytes)
[0.166s][debug][jit,compilation]   74       3       java.lang.invoke.MethodType::checkPtype (19 bytes)
[0.166s][debug][jit,compilation]   23       3       java.lang.String::charAt (25 bytes)   made not entrant
[0.166s][debug][jit,compilation]   77       3       java.util.concurrent.ConcurrentHashMap::putIfAbsent (8 bytes)
[0.167s][debug][jit,compilation]   78       3       jdk.internal.org.objectweb.asm.Item::<init> (66 bytes)
[0.167s][debug][jit,compilation]   79       3       jdk.internal.org.objectweb.asm.ClassWriter::put (152 bytes)
[0.167s][debug][jit,compilation]   80       3       jdk.internal.org.objectweb.asm.ClassWriter::newUTF8 (70 bytes)
[0.168s][debug][jit,compilation]   81       3       java.util.Arrays::copyOfRange (63 bytes)
[0.168s][debug][jit,compilation]   82       1       java.util.stream.FindOps$FindSink::cancellationRequested (5 bytes)
[0.169s][debug][jit,compilation]   83       1       java.util.Spliterators$IteratorSpliterator::characteristics (5 bytes)
[0.169s][debug][jit,compilation]   84       1       java.util.ImmutableCollections$SetN::size (5 bytes)
[0.169s][debug][jit,compilation]   85       3       java.util.ImmutableCollections$MapN::probe (60 bytes)
[0.170s][debug][jit,compilation]   88       3       java.util.HashMap::putVal (300 bytes)
[0.171s][debug][jit,compilation]   86       3       java.util.HashMap::hash (20 bytes)
[0.171s][debug][jit,compilation]   94       3       java.util.HashMap::getNode (148 bytes)
[0.171s][debug][jit,compilation]   89       3       java.util.HashMap$Node::<init> (26 bytes)
[0.172s][debug][jit,compilation]   90       3       java.util.HashMap::put (13 bytes)
[0.172s][debug][jit,compilation]   93       1       java.lang.module.ModuleDescriptor$Requires::name (5 bytes)
[0.172s][debug][jit,compilation]   91       3       java.util.HashMap::newNode (13 bytes)
[0.172s][debug][jit,compilation]   95       3       java.util.HashMap::afterNodeInsertion (1 bytes)
[0.172s][debug][jit,compilation]   97       3       java.util.HashMap::get (23 bytes)
[0.173s][debug][jit,compilation]  102       3       java.util.HashMap$HashIterator::nextNode (100 bytes)
[0.173s][debug][jit,compilation]   96       3       java.lang.module.ModuleDescriptor::hashCode (170 bytes)
[0.174s][debug][jit,compilation]   99       3       java.util.HashMap$HashIterator::hasNext (13 bytes)
[0.174s][debug][jit,compilation]  110       4       java.util.HashMap::afterNodeInsertion (1 bytes)
[0.174s][debug][jit,compilation]   95       3       java.util.HashMap::afterNodeInsertion (1 bytes)   made not entrant
[0.174s][debug][jit,compilation]  109       4       java.lang.String::hashCode (49 bytes)
[0.175s][debug][jit,compilation]  106       3       java.util.AbstractCollection::isEmpty (13 bytes)
[0.175s][debug][jit,compilation]  107       3       java.lang.module.ModuleDescriptor$Exports::isQualified (18 bytes)
[0.175s][debug][jit,compilation]  105       1       java.lang.module.ModuleDescriptor$Exports::targets (5 bytes)
[0.175s][debug][jit,compilation]  103       3       jdk.internal.module.ModuleReferenceImpl::hashCode (56 bytes)
[0.176s][debug][jit,compilation]  104       1       java.lang.module.ModuleDescriptor$Exports::source (5 bytes)
[0.176s][debug][jit,compilation]   92       1       java.lang.module.ModuleDescriptor$Requires::modifiers (5 bytes)
[0.177s][debug][jit,compilation]    6       3       java.lang.String::hashCode (49 bytes)   made not entrant
[0.177s][debug][jit,compilation]  112       4       java.util.ImmutableCollections$SetN$SetNIterator::hasNext (13 bytes)
[0.177s][debug][jit,compilation]  111       3       java.util.HashMap::putIfAbsent (13 bytes)
[0.177s][debug][jit,compilation]   30       3       java.util.ImmutableCollections$SetN$SetNIterator::hasNext (13 bytes)   made not entrant
[0.178s][debug][jit,compilation]  108       3       java.util.ImmutableCollections$Set12::size (13 bytes)
[0.178s][debug][jit,compilation]  113       3       java.util.ImmutableCollections$Set12::contains (28 bytes)
[0.178s][debug][jit,compilation]  115       4       java.util.ImmutableCollections$SetN$SetNIterator::nextIndex (56 bytes)
[0.178s][debug][jit,compilation]   98       1       java.lang.module.ModuleDescriptor::requires (5 bytes)
[0.179s][debug][jit,compilation]  118       3       java.util.HashMap::resize (356 bytes)
[0.179s][debug][jit,compilation]   31       3       java.util.ImmutableCollections$SetN$SetNIterator::nextIndex (56 bytes)   made not entrant
[0.179s][debug][jit,compilation]  121       4       java.util.ImmutableCollections$SetN$SetNIterator::next (47 bytes)
[0.180s][debug][jit,compilation]  117       3       java.util.ImmutableCollections$SetN::contains (26 bytes)
[0.180s][debug][jit,compilation]  100       3       java.util.HashSet::add (20 bytes)
[0.180s][debug][jit,compilation]  114       3       java.util.HashMap$KeyIterator::next (8 bytes)
[0.181s][debug][jit,compilation]  101       1       java.lang.module.ModuleDescriptor::isAutomatic (5 bytes)
[0.181s][debug][jit,compilation]   87       3       java.util.ImmutableCollections$MapN::get (35 bytes)
[0.182s][debug][jit,compilation]   32       3       java.util.ImmutableCollections$SetN$SetNIterator::next (47 bytes)   made not entrant
[0.182s][debug][jit,compilation]  123       4       java.lang.String::equals (65 bytes)
[0.182s][debug][jit,compilation]  124       3       java.util.ImmutableCollections$Set12$1::hasNext (13 bytes)
[0.182s][debug][jit,compilation]  127       3       java.util.HashMap$HashIterator::<init> (79 bytes)
[0.182s][debug][jit,compilation]  120       3       java.util.HashMap::containsKey (18 bytes)
[0.183s][debug][jit,compilation]  122       1       java.lang.module.ResolvedModule::reference (5 bytes)
[0.183s][debug][jit,compilation]  119       1       java.lang.module.ModuleDescriptor::exports (5 bytes)
[0.183s][debug][jit,compilation]   10       3       java.lang.String::equals (65 bytes)   made not entrant
[0.183s][debug][jit,compilation]  128       4       java.util.AbstractCollection::isEmpty (13 bytes)
[0.183s][debug][jit,compilation]  125       1       java.lang.module.ModuleDescriptor::uses (5 bytes)
[0.183s][debug][jit,compilation]  116       1       java.lang.module.ModuleDescriptor::provides (5 bytes)
[0.183s][debug][jit,compilation]  129       3       java.util.HashMap::keySet (25 bytes)
[0.184s][debug][jit,compilation]  134       3       java.lang.module.ResolvedModule::hashCode (16 bytes)
[0.184s][debug][jit,compilation]  106       3       java.util.AbstractCollection::isEmpty (13 bytes)   made not entrant
[0.184s][debug][jit,compilation]  126       4       java.util.HashMap::hash (20 bytes)
[0.184s][debug][jit,compilation]  130       3       java.util.HashMap$KeySet::iterator (12 bytes)
[0.184s][debug][jit,compilation]  131       3       java.util.HashMap$KeyIterator::<init> (11 bytes)
[0.184s][debug][jit,compilation]  135       1       java.util.KeyValueHolder::getKey (5 bytes)
[0.185s][debug][jit,compilation]  136       1       java.util.KeyValueHolder::getValue (5 bytes)
[0.185s][debug][jit,compilation]  133       1       java.lang.module.ModuleDescriptor$Provides::service (5 bytes)
[0.185s][debug][jit,compilation]  137       3       java.util.HashSet::contains (9 bytes)
[0.186s][debug][jit,compilation]   86       3       java.util.HashMap::hash (20 bytes)   made not entrant
[0.186s][debug][jit,compilation]  132       4       java.lang.module.ModuleDescriptor$Exports::isQualified (18 bytes)
[0.186s][debug][jit,compilation]  138       3       java.util.concurrent.ConcurrentHashMap::addCount (289 bytes)
[0.187s][debug][jit,compilation]  107       3       java.lang.module.ModuleDescriptor$Exports::isQualified (18 bytes)   made not entrant
[0.187s][debug][jit,compilation]  140       3       java.util.concurrent.ConcurrentHashMap::casTabAt (21 bytes)
[0.187s][debug][jit,compilation]  139       3       java.lang.String::indexOf (29 bytes)
[0.188s][debug][jit,compilation]  141       3       java.util.Optional::of (9 bytes)
[0.189s][debug][jit,compilation]  142       3       java.util.Optional::<init> (13 bytes)
[0.190s][debug][jit,compilation]  143       3       java.util.ImmutableCollections$SetN::iterator (9 bytes)
[0.190s][debug][jit,compilation]  144       1       java.lang.module.ModuleDescriptor::packages (5 bytes)
[0.190s][debug][jit,compilation]  145       1       java.lang.module.ModuleDescriptor$Version::toString (5 bytes)
[0.190s][debug][jit,compilation]  146       3       java.lang.AbstractStringBuilder::putStringAt (29 bytes)
[0.191s][debug][jit,compilation]  150       3       java.util.ImmutableCollections$Set12$1::next (92 bytes)
[0.191s][debug][jit,compilation]  152       4       java.util.HashMap::putVal (300 bytes)
[0.191s][debug][jit,compilation]  149       3       java.util.AbstractMap::<init> (5 bytes)
[0.192s][debug][jit,compilation]  154       3       java.util.AbstractSet::<init> (5 bytes)
[0.193s][debug][jit,compilation]  155       3       java.util.HashMap::<init> (11 bytes)
[0.193s][debug][jit,compilation]  148       3       java.util.HashSet::<init> (16 bytes)
[0.194s][debug][jit,compilation]  151       3       java.util.ImmutableCollections$Set12::iterator (9 bytes)
[0.194s][debug][jit,compilation]  158       3       java.util.ImmutableCollections$Set12$1::<init> (21 bytes)
[0.194s][debug][jit,compilation]  157       1       java.lang.module.ModuleDescriptor::isOpen (5 bytes)
[0.195s][debug][jit,compilation]  156       1       java.lang.module.ResolvedModule::configuration (5 bytes)
[0.195s][debug][jit,compilation]  160       3       java.util.ImmutableCollections$SetN$SetNIterator::<init> (40 bytes)
[0.195s][debug][jit,compilation]  163     n 0       java.lang.Module::addExportsToAllUnnamed0 (native)   (static)
[0.195s][debug][jit,compilation]  161       3       jdk.internal.module.ModuleBootstrap$2::next (52 bytes)
[0.196s][debug][jit,compilation]  162       3       jdk.internal.module.ModuleBootstrap$2::hasNext (30 bytes)
[0.196s][debug][jit,compilation]  164       3       java.util.concurrent.ConcurrentHashMap::get (162 bytes)
[0.197s][debug][jit,compilation]  159       1       java.lang.Module::getDescriptor (5 bytes)
[0.197s][debug][jit,compilation]  147       3       java.lang.AbstractStringBuilder::getCoder (15 bytes)
          vmop                            [ threads:    total initially_running wait_to_block ][ time:    spin   block    sync cleanup    vmop ] page_trap_count
   0.198: ThreadsSuspendJVMTI             [                12                 0             1 ][             0       0       0       0       0 ]               0
          vmop                            [ threads:    total initially_running wait_to_block ][ time:    spin   block    sync cleanup    vmop ] page_trap_count
   0.199: ChangeBreakpoints               [                13                 1             0 ][             4       0       4       0       0 ]               0
[0.208s][debug][jit,compilation]   88       3       java.util.HashMap::putVal (300 bytes)   made not entrant
[0.208s][debug][jit,compilation]  153       4       java.util.HashMap::newNode (13 bytes)
Connected to the target VM, address: '127.0.0.1:57767', transport: 'socket'
[0.211s][debug][jit,compilation]   91       3       java.util.HashMap::newNode (13 bytes)   made not entrant
          vmop                            [ threads:    total initially_running wait_to_block ][ time:    spin   block    sync cleanup    vmop ] page_trap_count
   0.216: ChangeBreakpoints               [                13                 0             0 ][             0       0       0       0       0 ]               0
          vmop                            [ threads:    total initially_running wait_to_block ][ time:    spin   block    sync cleanup    vmop ] page_trap_count
   0.218: ChangeBreakpoints               [                13                 0             0 ][             0       0       0       0       0 ]               0
[0.219s][debug][jit,compilation]   59       4       java.lang.String::isLatin1 (19 bytes)   made not entrant
[0.219s][debug][jit,compilation]  165       3       java.lang.StringUTF16::getChars (42 bytes)
[0.219s][debug][jit,compilation]  166       4       java.lang.StringUTF16::getChar (60 bytes)
[0.220s][debug][jit,compilation]  168       3       java.lang.String::isLatin1 (19 bytes)
[0.220s][debug][jit,compilation]  167       3       java.lang.String::toCharArray (25 bytes)
[0.225s][debug][jit,compilation]  169       3       java.lang.StringLatin1::replace (196 bytes)
[0.226s][debug][jit,compilation]  172     n 0       java.lang.Class::initClassName (native)
[0.226s][debug][jit,compilation]  170       3       java.lang.Class::getName (18 bytes)
[0.226s][debug][jit,compilation]  171       3       java.lang.String::replace (42 bytes)
[0.227s][debug][jit,compilation]  173       3       org.jetbrains.capture.org.objectweb.asm.Type::getInternalName (12 bytes)
[0.227s][debug][jit,compilation]  174       3       java.lang.StringBuilder::append (8 bytes)
[0.227s][debug][jit,compilation]  175       3       java.lang.AbstractStringBuilder::append (45 bytes)
[0.228s][debug][jit,compilation]  176       3       java.lang.System::getSecurityManager (4 bytes)
[0.230s][debug][jit,compilation]  177       3       java.lang.CharacterDataLatin1::getProperties (11 bytes)
[0.234s][debug][jit,compilation]  178       1       java.lang.Class::getModule (5 bytes)
[0.234s][debug][jit,compilation]  180     n 0       java.lang.invoke.MethodHandle::linkToStatic(L)V (native)   (static)
[0.234s][debug][jit,compilation]  181       3       java.lang.ref.WeakReference::<init> (6 bytes)
[0.235s][debug][jit,compilation]  182       3       java.lang.invoke.MethodType::parameterCount (6 bytes)
[0.235s][debug][jit,compilation]  179       1       java.lang.invoke.MethodType::form (5 bytes)
主线程开始睡觉
测试01线程 开始！
[0.237s][debug][jit,compilation]  183       3       java.lang.String::checkBoundsOffCount (61 bytes)
测试02线程 开始！
[0.239s][debug][jit,compilation]  184       3       java.io.BufferedInputStream::getBufIfOpen (21 bytes)
[0.239s][debug][jit,compilation]  185       3       java.lang.StringUTF16::compress (50 bytes)
[0.240s][debug][jit,compilation]  187       3       java.io.DataInputStream::readUTF (501 bytes)
[0.241s][debug][jit,compilation]  198       4       java.io.BufferedInputStream::getBufIfOpen (21 bytes)
[0.242s][debug][jit,compilation]  184       3       java.io.BufferedInputStream::getBufIfOpen (21 bytes)   made not entrant
[0.242s][debug][jit,compilation]  195       3       java.io.DataInputStream::readFully (63 bytes)
[0.242s][debug][jit,compilation]  186  s    3       java.io.BufferedInputStream::read (49 bytes)
[0.243s][debug][jit,compilation]  197       3       java.io.DataInputStream::readShort (40 bytes)
[0.243s][debug][jit,compilation]  196  s    3       java.io.BufferedInputStream::read (113 bytes)
[0.244s][debug][jit,compilation]  193       3       java.io.BufferedInputStream::read1 (108 bytes)
[0.244s][debug][jit,compilation]  190       3       java.lang.String::<init> (14 bytes)
[0.245s][debug][jit,compilation]  191       3       java.lang.String::rangeCheck (9 bytes)
[0.245s][debug][jit,compilation]  192       3       java.io.DataInputStream::readUnsignedShort (39 bytes)
[0.245s][debug][jit,compilation]  194       3       java.io.DataInputStream::readUTF (5 bytes)
[0.245s][debug][jit,compilation]  188       3       java.lang.String::<init> (74 bytes)
[0.246s][debug][jit,compilation]  189       3       java.lang.StringUTF16::compress (20 bytes)
[0.248s][debug][jit,compilation]  199       3       sun.util.locale.LocaleUtils::isUpper (18 bytes)
[0.249s][debug][jit,compilation]  200       3       java.util.concurrent.ConcurrentHashMap$Node::<init> (20 bytes)
[0.249s][debug][jit,compilation]  202     n 0       jdk.internal.misc.Unsafe::putObjectVolatile (native)
[0.250s][debug][jit,compilation]  201       3       jdk.internal.misc.Unsafe::putObjectRelease (9 bytes)
[0.250s][debug][jit,compilation]  203       3       java.util.concurrent.ConcurrentHashMap::setTabAt (20 bytes)
[0.253s][debug][jit,compilation]  204       1       sun.instrument.TransformerManager::getSnapshotTransformerList (5 bytes)
[0.253s][debug][jit,compilation]  205       1       sun.instrument.TransformerManager$TransformerInfo::transformer (5 bytes)
[0.254s][debug][jit,compilation]  206       3       java.lang.String::indexOf (7 bytes)
[0.259s][debug][jit,compilation]  207       3       java.lang.invoke.MethodType::hashCode (53 bytes)
[0.259s][debug][jit,compilation]  208       3       java.lang.AbstractStringBuilder::isLatin1 (19 bytes)
[0.260s][debug][jit,compilation]  211       3       java.lang.StringLatin1::newString (17 bytes)
[0.261s][debug][jit,compilation]  214       3       java.util.StringTokenizer::scanToken (179 bytes)
[0.261s][debug][jit,compilation]  216       4       java.util.HashMap::resize (356 bytes)
[0.262s][debug][jit,compilation]  213       3       java.util.StringTokenizer::skipDelimiters (116 bytes)
[0.263s][debug][jit,compilation]  212       3       java.util.StringTokenizer::hasMoreTokens (29 bytes)
[0.263s][debug][jit,compilation]  215       3       java.util.StringTokenizer::nextToken (92 bytes)
[0.264s][debug][jit,compilation]  209       3       java.lang.String::checkBoundsBeginEnd (60 bytes)
[0.264s][debug][jit,compilation]  225     n 0       jdk.internal.misc.Unsafe::getByte (native)
[0.264s][debug][jit,compilation]  219       3       java.nio.Buffer::hasRemaining (17 bytes)
[0.264s][debug][jit,compilation]  220       3       java.lang.ref.Reference::reachabilityFence (1 bytes)
[0.265s][debug][jit,compilation]  221   !   3       java.nio.DirectByteBuffer::get (28 bytes)
[0.265s][debug][jit,compilation]  222       3       java.nio.Buffer::nextGetIndex (30 bytes)
[0.265s][debug][jit,compilation]  223       3       java.nio.DirectByteBuffer::ix (10 bytes)
[0.265s][debug][jit,compilation]  224       3       jdk.internal.misc.Unsafe::getByte (7 bytes)
[0.266s][debug][jit,compilation]  226       1       java.nio.Buffer::limit (5 bytes)
[0.266s][debug][jit,compilation]  210       3       java.lang.String::substring (58 bytes)
[0.267s][debug][jit,compilation]  229       3       java.lang.StringBuilder::append (8 bytes)
[0.267s][debug][jit,compilation]  230       3       java.lang.AbstractStringBuilder::append (77 bytes)
[0.268s][debug][jit,compilation]  218       1       java.lang.invoke.MethodType::ptypes (5 bytes)
[0.268s][debug][jit,compilation]  217       1       java.lang.invoke.MethodHandle::type (5 bytes)
[0.270s][debug][jit,compilation]  231       3       java.lang.StringBuilder::toString (35 bytes)
[0.275s][debug][jit,compilation]  232       3       java.lang.StringBuilder::<init> (7 bytes)
[0.277s][debug][jit,compilation]  233       3       java.lang.CharacterData::of (120 bytes)
[0.278s][debug][jit,compilation]  187       3       java.io.DataInputStream::readUTF (501 bytes)   made not entrant

// 代码因为code deoptimization反优化进入安全点
          vmop                            [ threads:    total initially_running wait_to_block ][ time:    spin   block    sync cleanup    vmop ] page_trap_count
   0.278: Deoptimize                      [                15                 0             0 ][             0       0       0       0       0 ]               0
[0.281s][debug][jit,compilation]  234     n 0       java.lang.Object::clone (native)
[0.281s][debug][jit,compilation]  118       3       java.util.HashMap::resize (356 bytes)   made not entrant
[0.281s][debug][jit,compilation]  228       4       java.lang.ref.Reference::reachabilityFence (1 bytes)
[0.282s][debug][jit,compilation]  220       3       java.lang.ref.Reference::reachabilityFence (1 bytes)   made not entrant
[0.282s][debug][jit,compilation]  227       4       java.util.Objects::requireNonNull (14 bytes)
[0.282s][debug][jit,compilation]   16       3       java.util.Objects::requireNonNull (14 bytes)   made not entrant
[0.282s][debug][jit,compilation]  235       3       java.util.zip.ZipUtils::SH (21 bytes)
[0.282s][debug][jit,compilation]  236   !   3       java.util.zip.ZipFile$Source::checkUTF8 (43 bytes)
[0.283s][debug][jit,compilation]  237       3       java.util.zip.ZipFile$Source::hashN (26 bytes)
[0.283s][debug][jit,compilation]  239       3       java.lang.Character::toLowerCase (9 bytes)
[0.283s][debug][jit,compilation]  240       3       java.lang.CharacterDataLatin1::toLowerCase (39 bytes)
[0.283s][debug][jit,compilation]  238       1       java.util.zip.ZipCoder$UTF8::isUTF8 (2 bytes)
[0.286s][debug][jit,compilation]  242   !   3       sun.instrument.TransformerManager::transform (100 bytes)
[0.286s][debug][jit,compilation]  241       3       sun.instrument.InstrumentationImpl::transform (73 bytes)
[0.289s][debug][jit,compilation]  243       3       java.lang.invoke.MethodType::checkSlotCount (33 bytes)
[0.290s][debug][jit,compilation]  246       3       jdk.internal.org.objectweb.asm.ByteVector::putByte (39 bytes)
[0.290s][debug][jit,compilation]  244       3       java.lang.Class::getClassLoader (28 bytes)
[0.291s][debug][jit,compilation]  152       4       java.util.HashMap::putVal (300 bytes)   made not entrant
[0.291s][debug][jit,compilation]  245       3       java.lang.invoke.MemberName::testAllFlags (7 bytes)
[0.291s][debug][jit,compilation]  248       1       java.util.LinkedHashMap::removeEldestEntry (2 bytes)
[0.291s][debug][jit,compilation]  247       4       java.util.HashMap::putVal (300 bytes)
[0.291s][debug][jit,compilation]  249       3       java.util.LinkedHashMap::newNode (23 bytes)
[0.292s][debug][jit,compilation]  250       3       java.util.LinkedHashMap$Entry::<init> (10 bytes)
[0.292s][debug][jit,compilation]  251       3       java.util.LinkedHashMap::linkNodeLast (33 bytes)
[0.292s][debug][jit,compilation]  252       3       java.util.LinkedHashMap::afterNodeInsertion (40 bytes)
Sun Sep 25 10:50:43 CST 2022 测试01线程 循环到:0
[0.295s][debug][jit,compilation]  254       3       java.lang.StringUTF16::checkBoundsOffCount (10 bytes)
Sun Sep 25 10:50:43 CST 2022 测试02线程 循环到:0
[0.296s][debug][jit,compilation]  255 %     3       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 @ 32 (142 bytes)
[0.323s][debug][jit,compilation]  253       4       java.util.HashMap::put (13 bytes)
[0.324s][debug][jit,compilation]   90       3       java.util.HashMap::put (13 bytes)   made not entrant
[0.324s][debug][jit,compilation]  256 %     4       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 @ 32 (142 bytes)
[0.326s][debug][jit,compilation]  255 %     3       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 @ 32 (142 bytes)   made not entrant
[0.326s][debug][jit,compilation]  257       4       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 (142 bytes)
[0.454s][debug][jit,compilation]  256 %     4       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 @ 32 (142 bytes)   made not entrant
Sun Sep 25 10:50:44 CST 2022 测试02线程 循环到:10000000
[0.454s][debug][jit,compilation]  258       1       java.nio.Buffer::position (5 bytes)
[0.454s][debug][jit,compilation]  259 %     3       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 @ 32 (142 bytes)
[0.457s][debug][jit,compilation]  260 %     4       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 @ 32 (142 bytes)
Sun Sep 25 10:50:44 CST 2022 测试01线程 循环到:10000000
[0.461s][debug][jit,compilation]  261       3       java.lang.String::getBytes (44 bytes)
[0.464s][debug][jit,compilation]  259 %     3       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 @ 32 (142 bytes)   made not entrant
Sun Sep 25 10:50:44 CST 2022 测试02线程 循环到:20000000
Sun Sep 25 10:50:44 CST 2022 测试01线程 循环到:20000000
Sun Sep 25 10:50:44 CST 2022 测试02线程 循环到:30000000
Sun Sep 25 10:50:44 CST 2022 测试01线程 循环到:30000000
[0.766s][debug][jit,compilation]  262       3       java.lang.StringUTF16::putChar (54 bytes)
Sun Sep 25 10:50:44 CST 2022 测试02线程 循环到:40000000
Sun Sep 25 10:50:44 CST 2022 测试01线程 循环到:40000000
Sun Sep 25 10:50:44 CST 2022 测试02线程 循环到:50000000
Sun Sep 25 10:50:44 CST 2022 测试01线程 循环到:50000000[1.066s][debug][jit,compilation]  263       3       java.nio.Buffer::position (52 bytes)

Sun Sep 25 10:50:44 CST 2022 测试02线程 循环到:60000000
Sun Sep 25 10:50:44 CST 2022 测试01线程 循环到:60000000
睡醒了, 一共睡了 : 1008 毫秒
打印一下num 看看结果是多少: 82800529
          vmop                            [ threads:    total initially_running wait_to_block ][ time:    spin   block    sync cleanup    vmop ] page_trap_count
   1.290: no vm operation                 [                15                 2             2 ][             0       0       0       0       0 ]               2
Sun Sep 25 10:50:44 CST 2022 测试02线程 循环到:70000000
Sun Sep 25 10:50:45 CST 2022 测试01线程 循环到:70000000
Sun Sep 25 10:50:45 CST 2022 测试02线程 循环到:80000000
Sun Sep 25 10:50:45 CST 2022 测试01线程 循环到:80000000
Sun Sep 25 10:50:45 CST 2022 测试02线程 循环到:90000000
Sun Sep 25 10:50:45 CST 2022 测试01线程 循环到:90000000
Sun Sep 25 10:50:45 CST 2022 测试02线程 循环到:100000000
Sun Sep 25 10:50:45 CST 2022 测试01线程 循环到:100000000
Sun Sep 25 10:50:45 CST 2022 测试02线程 循环到:110000000
Sun Sep 25 10:50:45 CST 2022 测试01线程 循环到:110000000
[2.057s][debug][jit,compilation]  264       4       java.lang.String::length (11 bytes)
[2.058s][debug][jit,compilation]   28       3       java.lang.String::length (11 bytes)   made not entrant
Sun Sep 25 10:50:45 CST 2022 测试02线程 循环到:120000000
Sun Sep 25 10:50:45 CST 2022 测试01线程 循环到:120000000
[2.230s][debug][jit,compilation]  265       3       java.nio.ByteBuffer::arrayOffset (35 bytes)
Sun Sep 25 10:50:45 CST 2022 测试02线程 循环到:130000000
Sun Sep 25 10:50:46 CST 2022 测试01线程 循环到:130000000
Sun Sep 25 10:50:46 CST 2022 测试02线程 循环到:140000000
Sun Sep 25 10:50:46 CST 2022 测试01线程 循环到:140000000
[2.523s][debug][jit,compilation]  266       3       java.lang.AbstractStringBuilder::newCapacity (55 bytes)
Sun Sep 25 10:50:46 CST 2022 测试02线程 循环到:150000000
[2.546s][debug][jit,compilation]  267       3       java.lang.Character::isSurrogate (18 bytes)
Sun Sep 25 10:50:46 CST 2022 测试01线程 循环到:150000000
Sun Sep 25 10:50:46 CST 2022 测试02线程 循环到:160000000
Sun Sep 25 10:50:46 CST 2022 测试01线程 循环到:160000000
Sun Sep 25 10:50:46 CST 2022 测试02线程 循环到:170000000
[2.883s][debug][jit,compilation]  268       3       java.lang.AbstractStringBuilder::<init> (39 bytes)
Sun Sep 25 10:50:46 CST 2022 测试01线程 循环到:170000000
Sun Sep 25 10:50:46 CST 2022 测试02线程 循环到:180000000
[3.043s][debug][jit,compilation]  269       3       java.nio.CharBuffer::arrayOffset (35 bytes)
Sun Sep 25 10:50:46 CST 2022 测试01线程 循环到:180000000
[3.180s][debug][jit,compilation]  270       3       java.lang.Character::toUpperCase (6 bytes)
[3.181s][debug][jit,compilation]  271       3       java.lang.Character::toUpperCase (9 bytes)
Sun Sep 25 10:50:46 CST 2022 测试02线程 循环到:190000000
          vmop                            [ threads:    total initially_running wait_to_block ][ time:    spin   block    sync cleanup    vmop ] page_trap_count
   3.302: no vm operation                 [                15                 2             2 ][             0       0       0       0       0 ]               2
Sun Sep 25 10:50:46 CST 2022 测试01线程 循环到:190000000
[3.341s][debug][jit,compilation]  260 %     4       org.fenixsoft.jvm.chapter3.SafePointTest::lambda$main$0 @ 32 (142 bytes)   made not entrant
测试02线程 结束！
测试01线程 结束！
Disconnected from the target VM, address: '127.0.0.1:57767', transport: 'socket'
          vmop                            [ threads:    total initially_running wait_to_block ][ time:    spin   block    sync cleanup    vmop ] page_trap_count
   3.388: no vm operation                 [                 9                 0             0 ][             0       0       0       0      10 ]               0

Polling page always armed
Deoptimize                         1
ChangeBreakpoints                  3
ThreadsSuspendJVMTI                1
    0 VM operations coalesced during safepoint
Maximum sync time      4 ms
Maximum vm operation time (except for Exit VM operation)      0 ms

Process finished with exit code 0

 */


/*
//加这二个参数可以解决当前场景下主线程一直睡眠的问题： -XX:+UnlockDiagnosticVMOptions -XX:GuaranteedSafepointInterval=2000， 因为主线程睡眠1秒后，没有被设置进入安全点的标记，所以会继续运行，直到结束。
C:\Users\xufeng\.jdks\corretto-1.8.0_342\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:54399,suspend=y,server=n -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=10 -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -XX:-UseBiasedLocking -XX:+UnlockDiagnosticVMOptions -XX:+LogCompilation -XX:GuaranteedSafepointInterval=1500 -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\charsets.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\access-bridge-64.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\cldrdata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\dnsns.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\jaccess.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\jfxrt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\localedata.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\nashorn.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunec.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunjce_provider.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunmscapi.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\sunpkcs11.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\ext\zipfs.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jce.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jfr.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jfxswt.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\jsse.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\management-agent.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\resources.jar;C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre\lib\rt.jar;D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.086: ForceSafepoint                   [       8          1              0    ]      [     0     0     0     0     0    ]  0
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.087: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
Connected to the target VM, address: '127.0.0.1:54399', transport: 'socket'
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.178: ChangeBreakpoints                [       8          0              1    ]      [     0     0     0     0     0    ]  0
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.179: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0
主线程开始睡觉
测试01线程 开始！
测试02线程 开始！
睡醒了, 一共睡了 : 1007 毫秒
打印一下num 看看结果是多少: 113956561

# SafepointSynchronize::begin: Timeout detected:
# SafepointSynchronize::begin: Timed out while spinning to reach a safepoint.
# SafepointSynchronize::begin: Threads which did not reach the safepoint:
# "测试02线程" #14 prio=5 os_prio=0 tid=0x0000020f6f0378d0 nid=0xa08 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

# "测试01线程" #13 prio=5 os_prio=0 tid=0x0000020f6f0374a0 nid=0x3b38 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

# SafepointSynchronize::begin: (End of list)
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
1.687: no vm operation                  [      14          2              2    ]      [  2089     0  2089     0     0    ]  0
测试01线程 结束！
测试02线程 结束！
Disconnected from the target VM, address: '127.0.0.1:54399', transport: 'socket'
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
3.778: no vm operation                  [      11          0              0    ]      [     0     0     0     0     0    ]  0

Polling page always armed
ForceSafepoint                     1
ChangeBreakpoints                  3
    0 VM operations coalesced during safepoint
Maximum sync time   2089 ms
Maximum vm operation time (except for Exit VM operation)      0 ms

Process finished with exit code 0

 */


/*
C:\Users\xufeng\.jdks\corretto-17.0.4.1\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:55118,suspend=y,server=n "-Xlog:gc*=info::time,tags,tid" -Xlog:safepoint=info::time,tags,tid -XX:-UseBiasedLocking -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=10 -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.SafePointTest
[2022-09-25T15:05:13.991+0800][12888][gc] Using G1
[2022-09-25T15:05:13.993+0800][12888][gc,init] Version: 17.0.4.1+9-LTS (release)
[2022-09-25T15:05:13.993+0800][12888][gc,init] CPUs: 6 total, 6 available
[2022-09-25T15:05:13.993+0800][12888][gc,init] Memory: 15741M
[2022-09-25T15:05:13.993+0800][12888][gc,init] Large Page Support: Disabled
[2022-09-25T15:05:13.993+0800][12888][gc,init] NUMA Support: Disabled
[2022-09-25T15:05:13.993+0800][12888][gc,init] Compressed Oops: Enabled (Zero based)
[2022-09-25T15:05:13.993+0800][12888][gc,init] Heap Region Size: 2M
[2022-09-25T15:05:13.993+0800][12888][gc,init] Heap Min Capacity: 8M
[2022-09-25T15:05:13.993+0800][12888][gc,init] Heap Initial Capacity: 246M
[2022-09-25T15:05:13.993+0800][12888][gc,init] Heap Max Capacity: 3936M
[2022-09-25T15:05:13.993+0800][12888][gc,init] Pre-touch: Disabled
[2022-09-25T15:05:13.993+0800][12888][gc,init] Parallel Workers: 6
[2022-09-25T15:05:13.993+0800][12888][gc,init] Concurrent Workers: 2
[2022-09-25T15:05:13.993+0800][12888][gc,init] Concurrent Refinement Workers: 6
[2022-09-25T15:05:13.993+0800][12888][gc,init] Periodic GC: Disabled
[2022-09-25T15:05:13.993+0800][12888][gc,metaspace] CDS archive(s) mapped at: [0x0000000800000000-0x0000000800bc0000-0x0000000800bc0000), size 12320768, SharedBaseAddress: 0x0000000800000000, ArchiveRelocationMode: 0.
[2022-09-25T15:05:13.993+0800][12888][gc,metaspace] Compressed class space mapped at: 0x0000000800c00000-0x0000000840c00000, reserved size: 1073741824
[2022-09-25T15:05:13.993+0800][12888][gc,metaspace] Narrow klass base: 0x0000000800000000, Narrow klass shift: 0, Narrow klass range: 0x100000000
[2022-09-25T15:05:14.048+0800][4156 ][safepoint   ] Safepoint "ChangeBreakpoints", Time since last: 49594700 ns, Reaching safepoint: 185600 ns, At safepoint: 88700 ns, Total: 274300 ns
[2022-09-25T15:05:14.135+0800][4156 ][safepoint   ] Safepoint "ChangeBreakpoints", Time since last: 87305400 ns, Reaching safepoint: 25300 ns, At safepoint: 45500 ns, Total: 70800 ns
OpenJDK 64-Bit Server VM warning: Option UseBiasedLocking was deprecated in version 15.0 and will likely be removed in a future release.
Connected to the target VM, address: '127.0.0.1:55118', transport: 'socket'
[2022-09-25T15:05:14.137+0800][4156 ][safepoint   ] Safepoint "ChangeBreakpoints", Time since last: 1416500 ns, Reaching safepoint: 28400 ns, At safepoint: 40900 ns, Total: 69300 ns
主线程开始睡觉
测试01线程 开始！
测试02线程 开始！
[2022-09-25T15:05:15.146+0800][4156 ][safepoint   ] Safepoint "Cleanup", Time since last: 1008606500 ns, Reaching safepoint: 135999 ns, At safepoint: 155001 ns, Total: 291000 ns
睡醒了, 一共睡了 : 1011 毫秒
打印一下num 看看结果是多少: 121679691
// 这里的 “Cleanup” 等同于jdk13前的 “no vm operation”
[2022-09-25T15:05:16.146+0800][4156 ][safepoint   ] Safepoint "Cleanup", Time since last: 1000354400 ns, Reaching safepoint: 119900 ns, At safepoint: 23700 ns, Total: 143600 ns
测试02线程 结束！
测试01线程 结束！
[2022-09-25T15:05:17.600+0800][12888][gc,heap,exit] Heap
[2022-09-25T15:05:17.600+0800][12888][gc,heap,exit]  garbage-first heap   total 251904K, used 4510K [0x000000070a000000, 0x0000000800000000)
[2022-09-25T15:05:17.600+0800][12888][gc,heap,exit]   region size 2048K, 3 young (6144K), 0 survivors (0K)
[2022-09-25T15:05:17.600+0800][12888][gc,heap,exit]  Metaspace       used 468K, committed 640K, reserved 1056768K
[2022-09-25T15:05:17.600+0800][12888][gc,heap,exit]   class space    used 28K, committed 128K, reserved 1048576K
Disconnected from the target VM, address: '127.0.0.1:55118', transport: 'socket'

Process finished with exit code 0

 */