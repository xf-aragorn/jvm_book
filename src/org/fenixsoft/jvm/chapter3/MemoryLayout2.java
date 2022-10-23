package org.fenixsoft.jvm.chapter3;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.locks.LockSupport;

public class MemoryLayout2 {
    public class SimpleInt {
        private int state;
    }

    public static class SSimpleInt {
        private int state;
    }

    public class SimpleLong {
        private long state;
    }

    public static class Lock {
    }

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
        System.out.println("初始状态"+ClassLayout.parseInstance(lock).toPrintable());
        synchronized (lock) {
            System.out.println("同步块中："+ClassLayout.parseInstance(lock).toPrintable());
        }
            new Thread(()->{
                synchronized (lock) {
                    System.out.println("在新线程同步块中：" + ClassLayout.parseInstance(lock).toPrintable());
                }

            }).start();
        LockSupport.parkNanos(1000);
        System.out.println("退出同步："+ClassLayout.parseInstance(lock).toPrintable());
    }
}


/*
C:\Users\xufeng\.jdks\corretto-11.0.16.1\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53007,suspend=y,server=n -Djdk.attach.allowAttachSelf -server -XX:-UseBiasedLocking -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC -XX:+UseBiasedLocking -XX:+UseCompressedOops -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.BiasedLocks
OpenJDK 64-Bit Server VM warning: Option UseConcMarkSweepGC was deprecated in version 9.0 and will likely be removed in a future release.
[0.006s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
-XX:InitialHeapSize=257905536 -XX:MaxHeapSize=4126488576 -XX:MaxNewSize=523452416 -XX:MaxTenuringThreshold=6 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:ReservedCodeCacheSize=251658240 -XX:+SegmentedCodeCache -XX:+UseBiasedLocking -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation
[0.032s][info   ][gc] Using Concurrent Mark Sweep
[0.032s][info   ][gc,heap,coops] Heap address: 0x000000070a000000, size: 3936 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
Connected to the target VM, address: '127.0.0.1:53007', transport: 'socket'
org.fenixsoft.jvm.chapter3.BiasedLocks$SimpleInt object internals:
OFF  SZ                                     TYPE DESCRIPTION               VALUE
  0   8                                          (object header: mark)     N/A
  8   4                                          (object header: class)    N/A
 12   4                                      int SimpleInt.state           N/A
 16   4   org.fenixsoft.jvm.chapter3.BiasedLocks SimpleInt.this$0          N/A
 20   4                                          (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

# Running 64-bit HotSpot VM.
# Using compressed oop with 3-bit shift.
# Using compressed klass with 0x0000000800000000 base address and 0-bit shift.
# Objects are 8 bytes aligned.
# Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]

 */


/*
C:\Users\xufeng\.jdks\corretto-11.0.16.1\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:52924,suspend=y,server=n -Djdk.attach.allowAttachSelf -server -XX:-UseBiasedLocking -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC -XX:+UseBiasedLocking -XX:-UseCompressedOops -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.BiasedLocks
[0.005s][warning][gc] -XX:+PrintGCDetails is deprecated. Will use -Xlog:gc* instead.
-XX:InitialHeapSize=257905536 -XX:MaxHeapSize=4126488576 -XX:MaxNewSize=523452416 -XX:MaxTenuringThreshold=6 -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:ReservedCodeCacheSize=251658240 -XX:+SegmentedCodeCache -XX:+UseBiasedLocking -XX:-UseCompressedOops -XX:+UseConcMarkSweepGC -XX:-UseLargePagesIndividualAllocation
[0.028s][info   ][gc] Using Concurrent Mark Sweep
OpenJDK 64-Bit Server VM warning: Option UseConcMarkSweepGC was deprecated in version 9.0 and will likely be removed in a future release.
Connected to the target VM, address: '127.0.0.1:52924', transport: 'socket'
# Running 64-bit HotSpot VM.
# Objects are 8 bytes aligned.
# Field sizes by type: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
 */

/*
C:\Users\xufeng\.jdks\corretto-11.0.17\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53242,suspend=y,server=n -Djdk.attach.allowAttachSelf -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.MemoryLayout2
Connected to the target VM, address: '127.0.0.1:53242', transport: 'socket'
org.fenixsoft.jvm.chapter3.BiasedLocks$SimpleInt object internals:
OFF  SZ                                     TYPE DESCRIPTION               VALUE
  0   8                                          (object header: mark)     N/A
  8   4                                          (object header: class)    N/A
 12   4                                      int SimpleInt.state           N/A
 16   4   org.fenixsoft.jvm.chapter3.BiasedLocks SimpleInt.this$0          N/A
 20   4                                          (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.BiasedLocks$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     N/A
  8   4        (object header: class)    N/A
 12   4    int SSimpleInt.state          N/A
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

# Running 64-bit HotSpot VM.
# Using compressed oop with 3-bit shift.
# Using compressed klass with 0x0000000800000000 base address and 0-bit shift.
# Objects are 8 bytes aligned.
# Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
//references take 4 bytes, booleans and bytes take 1 byte, shorts and chars take 2 bytes, ints and floats take 4 bytes, and finally, longs and doubles take 8 bytes

org.fenixsoft.jvm.chapter3.BiasedLocks$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   4        (object header: class)    0x00090458
 12   4    int SSimpleInt.state          0
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

The identity hash code is 1381713434
org.fenixsoft.jvm.chapter3.BiasedLocks$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000000525b461a01 (hash: 0x525b461a; age: 0)
  8   4        (object header: class)    0x00090458
 12   4    int SSimpleInt.state          0
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.BiasedLocks$SimpleLong object internals:
OFF  SZ                                     TYPE DESCRIPTION               VALUE
  0   8                                          (object header: mark)     N/A
  8   4                                          (object header: class)    N/A
 12   4   org.fenixsoft.jvm.chapter3.BiasedLocks SimpleLong.this$0         N/A
 16   8                                     long SimpleLong.state          N/A
Instance size: 24 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.BiasedLocks$FieldsArrangement object internals:
OFF  SZ                                     TYPE DESCRIPTION                VALUE
  0   8                                          (object header: mark)      N/A
  8   4                                          (object header: class)     N/A
 12   4                                      int FieldsArrangement.fourth   N/A
 16   8                                   double FieldsArrangement.third    N/A
 24   2                                     char FieldsArrangement.second   N/A
 26   1                                  boolean FieldsArrangement.first    N/A
 27   1                                  boolean FieldsArrangement.fifth    N/A
 28   4   org.fenixsoft.jvm.chapter3.BiasedLocks FieldsArrangement.this$0   N/A
Instance size: 32 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.BiasedLocks$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   4        (object header: class)    0x00090250
 12   4        (object alignment gap)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.BiasedLocks$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000001ffff048005 (biased: 0x000000007fffc120; epoch: 0; age: 0)
  8   4        (object header: class)    0x00090250
 12   4        (object alignment gap)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

 */

/*
C:\Users\xufeng\.jdks\corretto-11.0.17\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:53447,suspend=y,server=n -Djdk.attach.allowAttachSelf -XX:-UseCompressedOops -XX:-UseBiasedLocking -XX:+PrintCommandLineFlags -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.MemoryLayout2
-XX:G1ConcRefinementThreads=6 -XX:GCDrainStackTargetSize=64 -XX:InitialHeapSize=257905536 -XX:MaxHeapSize=4126488576 -XX:+PrintCommandLineFlags -XX:ReservedCodeCacheSize=251658240 -XX:+SegmentedCodeCache -XX:-UseBiasedLocking -XX:-UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation
Connected to the target VM, address: '127.0.0.1:53447', transport: 'socket'
org.fenixsoft.jvm.chapter3.MemoryLayout2$SimpleInt object internals:
OFF  SZ                                       TYPE DESCRIPTION               VALUE
  0   8                                            (object header: mark)     N/A
  8   8                                            (object header: class)    N/A
 16   4                                        int SimpleInt.state           N/A
 20   4                                            (alignment/padding gap)
 24   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 SimpleInt.this$0          N/A
Instance size: 32 bytes
Space losses: 4 bytes internal + 0 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     N/A
  8   8        (object header: class)    N/A
 16   4    int SSimpleInt.state          N/A
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

# Running 64-bit HotSpot VM.
# Objects are 8 bytes aligned.
# Field sizes by type: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]  // 注意这里是参数-XX:-UseCompressedOops，关闭了指针压缩

org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
  8   8        (object header: class)    0x000001e9b1bac810
 16   4    int SSimpleInt.state          0
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

The identity hash code is 1381713434
org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000000525b461a01 (hash: 0x525b461a; age: 0)
  8   8        (object header: class)    0x000001e9b1bac810
 16   4    int SSimpleInt.state          0
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$SimpleLong object internals:
OFF  SZ                                       TYPE DESCRIPTION               VALUE
  0   8                                            (object header: mark)     N/A
  8   8                                            (object header: class)    N/A
 16   8                                       long SimpleLong.state          N/A
 24   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 SimpleLong.this$0         N/A
Instance size: 32 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$FieldsArrangement object internals:
OFF  SZ                                       TYPE DESCRIPTION                VALUE
  0   8                                            (object header: mark)      N/A
  8   8                                            (object header: class)     N/A
 16   8                                     double FieldsArrangement.third    N/A
 24   4                                        int FieldsArrangement.fourth   N/A
 28   2                                       char FieldsArrangement.second   N/A
 30   1                                    boolean FieldsArrangement.first    N/A
 31   1                                    boolean FieldsArrangement.fifth    N/A
 32   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 FieldsArrangement.this$0   N/A
Instance size: 40 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)    // 禁用了偏向锁
  8   8        (object header: class)    0x000001e9b1bac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000000b39b1ff350 (thin lock: 0x000000b39b1ff350)  // 轻量级锁
  8   8        (object header: class)    0x000001e9b1bac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

Disconnected from the target VM, address: '127.0.0.1:53447', transport: 'socket'

Process finished with exit code 0

 */

/*
C:\Users\xufeng\.jdks\corretto-11.0.17\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:54047,suspend=y,server=n -Djdk.attach.allowAttachSelf -XX:-UseCompressedOops -XX:-UseBiasedLocking -XX:+PrintCommandLineFlags -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.MemoryLayout2
-XX:G1ConcRefinementThreads=6 -XX:GCDrainStackTargetSize=64 -XX:InitialHeapSize=257905536 -XX:MaxHeapSize=4126488576 -XX:+PrintCommandLineFlags -XX:ReservedCodeCacheSize=251658240 -XX:+SegmentedCodeCache -XX:-UseBiasedLocking -XX:-UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation
Connected to the target VM, address: '127.0.0.1:54047', transport: 'socket'
org.fenixsoft.jvm.chapter3.MemoryLayout2$SimpleInt object internals:
OFF  SZ                                       TYPE DESCRIPTION               VALUE
  0   8                                            (object header: mark)     N/A
  8   8                                            (object header: class)    N/A
 16   4                                        int SimpleInt.state           N/A
 20   4                                            (alignment/padding gap)
 24   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 SimpleInt.this$0          N/A
Instance size: 32 bytes
Space losses: 4 bytes internal + 0 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     N/A
  8   8        (object header: class)    N/A
 16   4    int SSimpleInt.state          N/A
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

# Running 64-bit HotSpot VM.
# Objects are 8 bytes aligned.
# Field sizes by type: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]

org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
  8   8        (object header: class)    0x000001f8609ac810
 16   4    int SSimpleInt.state          0
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

The identity hash code is 1381713434
org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000000525b461a01 (hash: 0x525b461a; age: 0)
  8   8        (object header: class)    0x000001f8609ac810
 16   4    int SSimpleInt.state          0
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$SimpleLong object internals:
OFF  SZ                                       TYPE DESCRIPTION               VALUE
  0   8                                            (object header: mark)     N/A
  8   8                                            (object header: class)    N/A
 16   8                                       long SimpleLong.state          N/A
 24   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 SimpleLong.this$0         N/A
Instance size: 32 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$FieldsArrangement object internals:
OFF  SZ                                       TYPE DESCRIPTION                VALUE
  0   8                                            (object header: mark)      N/A
  8   8                                            (object header: class)     N/A
 16   8                                     double FieldsArrangement.third    N/A
 24   4                                        int FieldsArrangement.fourth   N/A
 28   2                                       char FieldsArrangement.second   N/A
 30   1                                    boolean FieldsArrangement.first    N/A
 31   1                                    boolean FieldsArrangement.fifth    N/A
 32   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 FieldsArrangement.this$0   N/A
Instance size: 40 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

初始状态org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
  8   8        (object header: class)    0x000001f8609ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

同步块中：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000000082acff300 (thin lock: 0x000000082acff300)
  8   8        (object header: class)    0x000001f8609ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

退出同步：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000001f860b99002 (fat lock: 0x000001f860b99002)
  8   8        (object header: class)    0x000001f8609ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

在新线程中：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000001f860b99002 (fat lock: 0x000001f860b99002)
  8   8        (object header: class)    0x000001f8609ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

Disconnected from the target VM, address: '127.0.0.1:54047', transport: 'socket'

Process finished with exit code 0

 */


/*
C:\Users\xufeng\.jdks\corretto-11.0.17\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:55141,suspend=y,server=n -Djdk.attach.allowAttachSelf -XX:-UseCompressedOops -XX:+UseBiasedLocking -XX:+PrintCommandLineFlags -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.MemoryLayout2
-XX:G1ConcRefinementThreads=6 -XX:GCDrainStackTargetSize=64 -XX:InitialHeapSize=257905536 -XX:MaxHeapSize=4126488576 -XX:+PrintCommandLineFlags -XX:ReservedCodeCacheSize=251658240 -XX:+SegmentedCodeCache -XX:+UseBiasedLocking -XX:-UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation
Connected to the target VM, address: '127.0.0.1:55141', transport: 'socket'
org.fenixsoft.jvm.chapter3.MemoryLayout2$SimpleInt object internals:
OFF  SZ                                       TYPE DESCRIPTION               VALUE
  0   8                                            (object header: mark)     N/A
  8   8                                            (object header: class)    N/A
 16   4                                        int SimpleInt.state           N/A
 20   4                                            (alignment/padding gap)
 24   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 SimpleInt.this$0          N/A
Instance size: 32 bytes
Space losses: 4 bytes internal + 0 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     N/A
  8   8        (object header: class)    N/A
 16   4    int SSimpleInt.state          N/A
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

# Running 64-bit HotSpot VM.
# Objects are 8 bytes aligned.
# Field sizes by type: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]

org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   8        (object header: class)    0x000001c6714ac810
 16   4    int SSimpleInt.state          0
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

The identity hash code is 1381713434
org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000000525b461a01 (hash: 0x525b461a; age: 0)
  8   8        (object header: class)    0x000001c6714ac810
 16   4    int SSimpleInt.state          0
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$SimpleLong object internals:
OFF  SZ                                       TYPE DESCRIPTION               VALUE
  0   8                                            (object header: mark)     N/A
  8   8                                            (object header: class)    N/A
 16   8                                       long SimpleLong.state          N/A
 24   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 SimpleLong.this$0         N/A
Instance size: 32 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$FieldsArrangement object internals:
OFF  SZ                                       TYPE DESCRIPTION                VALUE
  0   8                                            (object header: mark)      N/A
  8   8                                            (object header: class)     N/A
 16   8                                     double FieldsArrangement.third    N/A
 24   4                                        int FieldsArrangement.fourth   N/A
 28   2                                       char FieldsArrangement.second   N/A
 30   1                                    boolean FieldsArrangement.first    N/A
 31   1                                    boolean FieldsArrangement.fifth    N/A
 32   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 FieldsArrangement.this$0   N/A
Instance size: 40 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

初始状态org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   8        (object header: class)    0x000001c6714ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

同步块中：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000001c555ac0805 (biased: 0x0000000071556b02; epoch: 0; age: 0)
  8   8        (object header: class)    0x000001c6714ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
//出现了锁竞争，偏向锁直接升级为重量级锁！！！
退出同步：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000001c6716c1402 (fat lock: 0x000001c6716c1402)
  8   8        (object header: class)    0x000001c6714ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

在新线程中：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000001c6716c1402 (fat lock: 0x000001c6716c1402)
  8   8        (object header: class)    0x000001c6714ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

Disconnected from the target VM, address: '127.0.0.1:55141', transport: 'socket'

Process finished with exit code 0

 */

/*
C:\Users\xufeng\.jdks\corretto-11.0.17\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:55243,suspend=y,server=n -Djdk.attach.allowAttachSelf -XX:-UseCompressedOops -XX:+UseBiasedLocking -XX:+PrintCommandLineFlags -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.MemoryLayout2
-XX:G1ConcRefinementThreads=6 -XX:GCDrainStackTargetSize=64 -XX:InitialHeapSize=257905536 -XX:MaxHeapSize=4126488576 -XX:+PrintCommandLineFlags -XX:ReservedCodeCacheSize=251658240 -XX:+SegmentedCodeCache -XX:+UseBiasedLocking -XX:-UseCompressedOops -XX:+UseG1GC -XX:-UseLargePagesIndividualAllocation
Connected to the target VM, address: '127.0.0.1:55243', transport: 'socket'
org.fenixsoft.jvm.chapter3.MemoryLayout2$SimpleInt object internals:
OFF  SZ                                       TYPE DESCRIPTION               VALUE
  0   8                                            (object header: mark)     N/A
  8   8                                            (object header: class)    N/A
 16   4                                        int SimpleInt.state           N/A
 20   4                                            (alignment/padding gap)
 24   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 SimpleInt.this$0          N/A
Instance size: 32 bytes
Space losses: 4 bytes internal + 0 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     N/A
  8   8        (object header: class)    N/A
 16   4    int SSimpleInt.state          N/A
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

# Running 64-bit HotSpot VM.
# Objects are 8 bytes aligned.
# Field sizes by type: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 8, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]    //禁用对象指针压缩，对象指针占8个字节

org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   8        (object header: class)    0x000001ce514ac810
 16   4    int SSimpleInt.state          0
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

The identity hash code is 1381713434
org.fenixsoft.jvm.chapter3.MemoryLayout2$SSimpleInt object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000000525b461a01 (hash: 0x525b461a; age: 0)
  8   8        (object header: class)    0x000001ce514ac810
 16   4    int SSimpleInt.state          0
 20   4        (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$SimpleLong object internals:
OFF  SZ                                       TYPE DESCRIPTION               VALUE
  0   8                                            (object header: mark)     N/A
  8   8                                            (object header: class)    N/A
 16   8                                       long SimpleLong.state          N/A
 24   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 SimpleLong.this$0         N/A
Instance size: 32 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

org.fenixsoft.jvm.chapter3.MemoryLayout2$FieldsArrangement object internals:
OFF  SZ                                       TYPE DESCRIPTION                VALUE
  0   8                                            (object header: mark)      N/A
  8   8                                            (object header: class)     N/A
 16   8                                     double FieldsArrangement.third    N/A
 24   4                                        int FieldsArrangement.fourth   N/A
 28   2                                       char FieldsArrangement.second   N/A
 30   1                                    boolean FieldsArrangement.first    N/A
 31   1                                    boolean FieldsArrangement.fifth    N/A
 32   8   org.fenixsoft.jvm.chapter3.MemoryLayout2 FieldsArrangement.this$0   N/A
Instance size: 40 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

初始状态org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   8        (object header: class)    0x000001ce514ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

同步块中：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x000001cd372ab005 (biased: 0x00000000734dcaac; epoch: 0; age: 0)
  8   8        (object header: class)    0x000001ce514ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
//有二个线程都同步当前对象，但是没有竞争，锁升级为轻量级锁
在新线程中：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000003dd60ff120 (thin lock: 0x0000003dd60ff120)
  8   8        (object header: class)    0x000001ce514ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

//当出现多线程都同步当前对象时，退出同步后，锁不可再偏向！
退出同步：org.fenixsoft.jvm.chapter3.MemoryLayout2$Lock object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
  8   8        (object header: class)    0x000001ce514ac3c8
Instance size: 16 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total

Disconnected from the target VM, address: '127.0.0.1:55243', transport: 'socket'

Process finished with exit code 0

 */