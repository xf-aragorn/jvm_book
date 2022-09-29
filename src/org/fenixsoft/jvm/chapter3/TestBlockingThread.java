package org.fenixsoft.jvm.chapter3;

public class TestBlockingThread {
    static Thread t1 = new Thread(() -> {
        while (true) {
            long start = System.currentTimeMillis();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long cost = System.currentTimeMillis() - start;
            //按照正常情况,t1线程,大致上应是每隔1000毫秒左右,会输出一句话 我们使用 cost 来记录实际等待的时间
            //如果实际时间cost大于1010毫秒 我们就使用System.err输出,也就是红色字样的输出,否则则是正常输出
            (cost > 1010L ? System.err : System.out).printf("thread: %s, costs %d ms\n", Thread.currentThread().getName(), cost);
        }
    });

    static Thread t2 = new Thread(() -> {
        while (true) {

            //下面是一个counted loop,单次循环末尾不会被加入安全点,整个for循环期执行结束之前,都不会进入安全点
            //存在这样一种情况, 如果某次for循环才刚刚开始没多久, 因为内存过多而需要进行垃圾收集
            //而我们知道,垃圾收集刚开始的时候需要先获取所有根节点,而根节点的获取依赖所有线程抵达安全点
            //线程t1很简单,只需要隔1s就会进入安全点,之后,线程t1需要等到其他线程(t2)也进入到安全点
            //而t2此时才刚刚是for循环的刚开始,所以需要消耗大量时间走完剩下的循环次数,这也就是为什么有时候t1实际cost时间多达5s的原因
            //也就是gc发生时,要获取所有根节点,而想要获取根节点,就要所有线程抵达安全点,已经抵达的线程(t1)需要等待未抵达的线程(t2)到达安全点 然后才会继续垃圾收集的剩下内容
            for (int i = 1; i <= 1000000000; i++) {
                boolean b = 1.0 / i == 0;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private static final int _50KB = 50 * 1024;

    //下面的代码在创建大量的对象, 一定会导致隔一段时间会出现垃圾收集
    static Thread t3 = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[_50KB];
        }
    });

    public static void main(String[] args) throws InterruptedException {
        t1.setName("thread1");
        t2.setName("thread2");
        t3.setName("thread3");
        t1.start();
        //记录睡眠时间
        long start = System.currentTimeMillis();
        Thread.sleep(1500L);
        t2.start();
        t3.start();
        System.out.println("睡醒了, 一共睡了 : " + (System.currentTimeMillis() - start) + " 毫秒");
    }
}

/*
E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:51343,suspend=y,server=n -Xmx40M "-Xlog:gc*" -XX:+UseShenandoahGC -Xlog:safepoint+stats -XX:+SafepointTimeout -XX:SafepointTimeoutDelay=10 -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2021.3\captureAgent\debugger-agent.jar -Dfile.encoding=UTF-8 -classpath "E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.base;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.compiler;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.datatransfer;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.desktop;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.instrument;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.logging;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.management;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.management.rmi;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.naming;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.net.http;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.prefs;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.rmi;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.scripting;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.se;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.security.jgss;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.security.sasl;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.smartcardio;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.sql;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.sql.rowset;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.transaction.xa;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.xml;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\java.xml.crypto;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.accessibility;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.attach;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.charsets;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.compiler;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.crypto.cryptoki;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.crypto.ec;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.crypto.mscapi;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.dynalink;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.editpad;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.hotspot.agent;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.httpserver;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.incubator.foreign;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.incubator.vector;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.internal.ed;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.internal.jvmstat;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.internal.le;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.internal.opt;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.internal.vm.ci;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.internal.vm.compiler;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.internal.vm.compiler.management;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jartool;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.javadoc;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jcmd;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jconsole;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jdeps;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jdi;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jdwp.agent;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jfr;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jlink;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jpackage;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jshell;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jsobject;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.jstatd;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.localedata;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.management;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.management.agent;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.management.jfr;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.naming.dns;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.naming.rmi;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.net;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.nio.mapmode;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.random;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.sctp;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.security.auth;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.security.jgss;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.unsupported;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.unsupported.desktop;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.xml.dom;E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\modules\jdk.zipfs;D:\project\out\production\jvm-book-3rd;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.TestBlockingThread
[0.004s][info][gc] Min heap equals to max heap, disabling ShenandoahUncommit
[0.022s][info][gc] Heuristics ergonomically sets -XX:+ExplicitGCInvokesConcurrent
[0.022s][info][gc] Heuristics ergonomically sets -XX:+ShenandoahImplicitGCInvokesConcurrent
[0.022s][info][gc] Using Shenandoah
[0.040s][info][gc,ergo] Pacer for Idle. Initial: 819K, Alloc Tax Rate: 1.0x
[0.041s][info][gc,init] Version: 17-internal+0-adhoc.xufeng.openjdk (fastdebug)
[0.041s][info][gc,init] CPUs: 6 total, 6 available
[0.041s][info][gc,init] Memory: 15741M
[0.041s][info][gc,init] Large Page Support: Disabled
[0.041s][info][gc,init] NUMA Support: Disabled
[0.041s][info][gc,init] Compressed Oops: Enabled (32-bit)
[0.041s][info][gc,init] Heap Min Capacity: 6656K
[0.041s][info][gc,init] Heap Initial Capacity: 40M
[0.041s][info][gc,init] Heap Max Capacity: 40M
[0.041s][info][gc,init] Pre-touch: Disabled
[0.041s][info][gc,init] Mode: Snapshot-At-The-Beginning (SATB)
[0.041s][info][gc,init] Heuristics: Adaptive
[0.041s][info][gc,init] Heap Region Count: 160
[0.041s][info][gc,init] Heap Region Size: 256K
[0.041s][info][gc,init] TLAB Size Max: 256K
[0.041s][info][gc,init] Humongous Object Threshold: 256K
[0.041s][info][gc,init] Parallel Workers: 3
[0.041s][info][gc,init] Concurrent Workers: 1
[0.041s][info][gc,metaspace] CDS archive(s) not mapped
[0.041s][info][gc,metaspace] Compressed class space mapped at: 0x0000000100000000-0x0000000140000000, reserved size: 1073741824
[0.041s][info][gc,metaspace] Narrow klass base: 0x0000000000000000, Narrow klass shift: 3, Narrow klass range: 0x140000000
[0.887s][info][safepoint,stats] VM Operation                 [ threads: total initial_running ][ time:       sync    cleanup       vmop      total ] page_trap_count
[0.887s][info][safepoint,stats] ChangeBreakpoints            [             15               2 ][          9699800     314600    2991500   13005900 ]               0
Connected to the target VM, address: '127.0.0.1:51343', transport: 'socket'
[0.915s][info][safepoint,stats] ChangeBreakpoints            [             15               0 ][            11500      30700     174600     216800 ]               0
[0.917s][info][safepoint,stats] ChangeBreakpoints            [             15               0 ][            10600      18100     160200     188900 ]               0
[1.922s][info][safepoint,stats] Cleanup                      [             17               0 ][            22600     162100      12300     197000 ]               0
thread: thread1, costs 1004 ms
睡醒了, 一共睡了 : 1505 毫秒
[2.742s][info][gc             ] Trigger: Learning 1 of 5. Free (28621K) is below initial threshold (28671K)
[2.742s][info][gc,ergo        ] Free: 28621K, Max: 256K regular, 28416K humongous, Frag: 0% external, 0% internal; Reserve: 2048K, Max: 256K
[2.742s][info][gc,start       ] GC(0) Concurrent reset
[2.742s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent reset
[2.742s][info][gc,ergo        ] GC(0) Pacer for Reset. Non-Taxable: 40960K
[2.743s][info][gc             ] GC(0) Concurrent reset 1.191ms
[2.743s][info][gc,start       ] GC(0) Pause Init Mark (unload classes)
[2.743s][info][gc,task        ] GC(0) Using 3 of 3 workers for init marking
[2.744s][info][gc,ergo        ] GC(0) Pacer for Mark. Expected Live: 4096K, Free: 28621K, Non-Taxable: 2862K, Alloc Tax Rate: 0.2x
[2.744s][info][gc             ] GC(0) Pause Init Mark (unload classes) 0.373ms
[2.744s][info][safepoint,stats] ShenandoahInitMark           [             18               1 ][            44200      93800     438800     576800 ]               1
[2.744s][info][gc,start       ] GC(0) Concurrent marking roots
[2.744s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent marking roots
[2.744s][info][gc             ] GC(0) Concurrent marking roots 0.807ms
[2.744s][info][gc,start       ] GC(0) Concurrent marking (unload classes)
[2.744s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent marking
[2.758s][info][gc             ] GC(0) Concurrent marking (unload classes) 13.417ms
[2.758s][info][gc,start       ] GC(0) Pause Final Mark (unload classes)
[2.758s][info][gc,task        ] GC(0) Using 3 of 3 workers for final marking
[2.758s][info][gc,ergo        ] GC(0) Adaptive CSet Selection. Target Free: 5802K, Actual Free: 31970K, Max CSet: 1706K, Min Garbage: 0B
[2.758s][info][gc,ergo        ] GC(0) Collectable Garbage: 8732K (99%), Immediate: 1506K (17%), CSet: 7226K (82%)
[2.758s][info][gc,ergo        ] GC(0) Pacer for Evacuation. Used CSet: 8448K, Free: 29952K, Non-Taxable: 2995K, Alloc Tax Rate: 1.1x
[2.758s][info][gc             ] GC(0) Pause Final Mark (unload classes) 0.149ms
[2.758s][info][safepoint,stats] ShenandoahFinalMarkStartEvac [             18               1 ][            14900      43000     177900     235800 ]               1
[2.758s][info][gc,start       ] GC(0) Concurrent thread roots
[2.758s][info][gc,task        ] GC(0) Using 1 of 3 workers for Concurrent thread roots
[2.759s][info][gc             ] GC(0) Concurrent thread roots 0.299ms
[2.759s][info][gc,start       ] GC(0) Concurrent weak references
[2.759s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent weak references
[2.759s][info][gc,ref         ] GC(0) Encountered references: Soft: 148, Weak: 414, Final: 0, Phantom: 48
[2.759s][info][gc,ref         ] GC(0) Discovered  references: Soft: 0, Weak: 286, Final: 0, Phantom: 48
[2.759s][info][gc,ref         ] GC(0) Enqueued    references: Soft: 0, Weak: 160, Final: 0, Phantom: 41
[2.759s][info][gc             ] GC(0) Concurrent weak references 0.276ms
[2.759s][info][gc,start       ] GC(0) Concurrent weak roots
[2.759s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent weak root
[2.760s][info][gc             ] GC(0) Concurrent weak roots 1.094ms
[2.760s][info][gc,start       ] GC(0) Concurrent cleanup
[2.760s][info][gc             ] GC(0) Concurrent cleanup 10M->9M(40M) 0.118ms
[2.760s][info][gc,ergo        ] GC(0) Free: 29704K, Max: 256K regular, 28160K humongous, Frag: 6% external, 0% internal; Reserve: 1792K, Max: 256K
[2.760s][info][gc,start       ] GC(0) Concurrent class unloading
[2.760s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent class unloading
[2.762s][info][gc             ] GC(0) Concurrent class unloading 1.915ms
[2.762s][info][gc,start       ] GC(0) Concurrent strong roots
[2.762s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent strong root
[2.762s][info][gc             ] GC(0) Concurrent strong roots 0.295ms
[2.762s][info][gc,start       ] GC(0) Concurrent evacuation
[2.762s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent evacuation
[2.762s][info][safepoint,stats] CleanClassLoaderDataMetaspaces [             18               1 ][            19800      56400     244600     320800 ]               1
[2.767s][info][gc             ] GC(0) Concurrent evacuation 4.880ms
[2.767s][info][gc,start       ] GC(0) Pause Init Update Refs
[2.767s][info][gc,ergo        ] GC(0) Pacer for Update Refs. Used: 10487K, Free: 29704K, Non-Taxable: 2970K, Alloc Tax Rate: 1.1x
[2.767s][info][gc             ] GC(0) Pause Init Update Refs 0.047ms
[2.767s][info][safepoint,stats] ShenandoahInitUpdateRefs     [             18               1 ][            50600      58100      87800     196500 ]               1
[2.767s][info][gc,start       ] GC(0) Concurrent update references
[2.768s][info][gc,task        ] GC(0) Using 1 of 3 workers for concurrent reference update
[2.777s][info][gc             ] GC(0) Concurrent update references 9.219ms
[2.777s][info][gc,start       ] GC(0) Concurrent update thread roots
[2.777s][info][gc             ] GC(0) Concurrent update thread roots 0.311ms
[2.777s][info][gc,start       ] GC(0) Pause Final Update Refs
[2.777s][info][gc,task        ] GC(0) Using 3 of 3 workers for final reference update
[2.777s][info][gc             ] GC(0) Pause Final Update Refs 0.062ms
[2.777s][info][safepoint,stats] ShenandoahFinalUpdateRefs    [             18               1 ][            25400      27200      91200     143800 ]               1
[2.777s][info][gc,start       ] GC(0) Concurrent cleanup
[2.778s][info][gc             ] GC(0) Concurrent cleanup 10M->1M(40M) 1.004ms
[2.778s][info][gc,ergo        ] Free: 36872K, Max: 256K regular, 26880K humongous, Frag: 28% external, 0% internal; Reserve: 2048K, Max: 256K
[2.778s][info][gc,stats       ]
[2.778s][info][gc,stats       ] All times are wall-clock times, except per-root-class counters, that are sum over
[2.778s][info][gc,stats       ] all workers. Dividing the <total> over the root stage time estimates parallelism.
[2.778s][info][gc,stats       ]
[2.778s][info][gc,stats       ] Concurrent Reset                   1228 us
[2.778s][info][gc,stats       ] Pause Init Mark (G)                 693 us
[2.778s][info][gc,stats       ] Pause Init Mark (N)                 389 us
[2.778s][info][gc,stats       ]   Update Region States                6 us
[2.778s][info][gc,stats       ] Concurrent Mark Roots               844 us, parallelism: 0.72x
[2.778s][info][gc,stats       ]   CMR: <total>                      609 us
[2.778s][info][gc,stats       ]   CMR: Thread Roots                 314 us, workers (us): 314, ---, ---,
[2.778s][info][gc,stats       ]   CMR: VM Strong Roots               31 us, workers (us):  31, ---, ---,
[2.779s][info][gc,stats       ]   CMR: CLDG Roots                   264 us, workers (us): 264, ---, ---,
[2.779s][info][gc,stats       ] Concurrent Marking                13432 us
[2.779s][info][gc,stats       ] Pause Final Mark (G)                286 us
[2.779s][info][gc,stats       ] Pause Final Mark (N)                155 us
[2.779s][info][gc,stats       ]   Finish Mark                        59 us
[2.779s][info][gc,stats       ]   Update Region States                3 us
[2.779s][info][gc,stats       ]   Choose Collection Set              35 us
[2.779s][info][gc,stats       ]   Rebuild Free Set                    4 us
[2.779s][info][gc,stats       ] Concurrent Thread Roots             312 us, parallelism: 0.74x
[2.779s][info][gc,stats       ]   CTR: <total>                      231 us
[2.779s][info][gc,stats       ]   CTR: Thread Roots                 231 us, workers (us): 231, ---, ---,
[2.779s][info][gc,stats       ] Concurrent Weak References          288 us, parallelism: 0.51x
[2.779s][info][gc,stats       ]   CWRF: <total>                     145 us
[2.779s][info][gc,stats       ]   CWRF: Weak References             145 us, workers (us): 145, ---, ---,
[2.779s][info][gc,stats       ] Concurrent Weak Roots              1107 us
[2.779s][info][gc,stats       ]   Roots                            1000 us, parallelism: 0.94x
[2.779s][info][gc,stats       ]     CWR: <total>                    936 us
[2.779s][info][gc,stats       ]     CWR: Code Cache Roots           119 us, workers (us): 119, ---, ---,
[2.779s][info][gc,stats       ]     CWR: VM Weak Roots              806 us, workers (us): 806, ---, ---,
[2.779s][info][gc,stats       ]     CWR: CLDG Roots                  11 us, workers (us):  11, ---, ---,
[2.779s][info][gc,stats       ]   Rendezvous                         71 us
[2.779s][info][gc,stats       ] Concurrent Cleanup                  126 us
[2.779s][info][gc,stats       ] Concurrent Class Unloading         1927 us
[2.779s][info][gc,stats       ]   Unlink Stale                     1735 us
[2.779s][info][gc,stats       ]     System Dictionary                17 us
[2.779s][info][gc,stats       ]     Weak Class Links                  0 us
[2.779s][info][gc,stats       ]     Code Roots                     1717 us
[2.779s][info][gc,stats       ]   Rendezvous                         98 us
[2.779s][info][gc,stats       ]   Purge Unlinked                     72 us
[2.779s][info][gc,stats       ]     Code Roots                       63 us
[2.779s][info][gc,stats       ]     CLDG                              8 us
[2.779s][info][gc,stats       ]     Exception Caches                  0 us
[2.779s][info][gc,stats       ] Concurrent Strong Roots             324 us, parallelism: 0.74x
[2.779s][info][gc,stats       ]   CSR: <total>                      240 us
[2.779s][info][gc,stats       ]   CSR: VM Strong Roots               23 us, workers (us):  23, ---, ---,
[2.779s][info][gc,stats       ]   CSR: CLDG Roots                   217 us, workers (us): 217, ---, ---,
[2.779s][info][gc,stats       ] Concurrent Evacuation              4929 us
[2.779s][info][gc,stats       ] Pause Init Update Refs (G)          252 us
[2.779s][info][gc,stats       ] Pause Init Update Refs (N)           52 us
[2.779s][info][gc,stats       ]   Manage GCLABs                      18 us
[2.779s][info][gc,stats       ] Concurrent Update Refs             9260 us
[2.779s][info][gc,stats       ] Concurrent Update Thread Roots      339 us
[2.779s][info][gc,stats       ] Pause Final Update Refs (G)         207 us
[2.779s][info][gc,stats       ] Pause Final Update Refs (N)          68 us
[2.779s][info][gc,stats       ]   Update Region States                3 us
[2.779s][info][gc,stats       ]   Trash Collection Set                3 us
[2.779s][info][gc,stats       ]   Rebuild Free Set                    4 us
[2.779s][info][gc,stats       ] Concurrent Cleanup                 1044 us
[2.779s][info][gc,stats       ]
[2.779s][info][gc,stats       ] Allocation pacing accrued:
[2.779s][info][gc,stats       ]       0 of  2738 ms (  0.0%): <total>
[2.779s][info][gc,stats       ]       0 of  2738 ms (  0.0%): <average total>
[2.779s][info][gc,stats       ]
[2.779s][info][gc,metaspace   ] Metaspace: 7960K(8128K)->7960K(8128K) NonClass: 7248K(7360K)->7248K(7360K) Class: 712K(768K)->712K(768K)
[2.779s][info][gc,ergo        ] Pacer for Idle. Initial: 819K, Alloc Tax Rate: 1.0x
thread: thread1, costs 1000 ms
[3.772s][info][gc             ] Trigger: Learning 2 of 5. Free (28630K) is below initial threshold (28671K)
[3.772s][info][gc,ergo        ] Free: 28630K, Max: 256K regular, 26880K humongous, Frag: 6% external, 1% internal; Reserve: 2048K, Max: 256K
[3.772s][info][gc,start       ] GC(1) Concurrent reset
[3.772s][info][gc,task        ] GC(1) Using 1 of 3 workers for concurrent reset
[3.772s][info][gc,ergo        ] GC(1) Pacer for Reset. Non-Taxable: 40960K
[3.772s][info][gc             ] GC(1) Concurrent reset 0.262ms
[3.772s][info][gc,start       ] GC(1) Pause Init Mark (unload classes)
[3.772s][info][gc,task        ] GC(1) Using 3 of 3 workers for init marking
[3.772s][info][gc,ergo        ] GC(1) Pacer for Mark. Expected Live: 1457K, Free: 28630K, Non-Taxable: 2863K, Alloc Tax Rate: 0.1x
[3.772s][info][gc             ] GC(1) Pause Init Mark (unload classes) 0.202ms
[3.772s][info][safepoint,stats] ShenandoahInitMark           [             18               1 ][            58600      39200     243800     341600 ]               1
[3.772s][info][gc,start       ] GC(1) Concurrent marking roots
[3.772s][info][gc,task        ] GC(1) Using 1 of 3 workers for concurrent marking roots
[3.773s][info][gc             ] GC(1) Concurrent marking roots 0.686ms
[3.773s][info][gc,start       ] GC(1) Concurrent marking (unload classes)
[3.773s][info][gc,task        ] GC(1) Using 1 of 3 workers for concurrent marking
[3.787s][info][gc             ] GC(1) Concurrent marking (unload classes) 14.161ms
[3.787s][info][gc,start       ] GC(1) Pause Final Mark (unload classes)
[3.787s][info][gc,task        ] GC(1) Using 3 of 3 workers for final marking
[3.787s][info][gc,ergo        ] GC(1) Collectable Garbage: 8511K (98%), Immediate: 8511K (98%), CSet: 0B (0%)
[3.788s][info][gc             ] GC(1) Pause Final Mark (unload classes) 0.162ms
[3.788s][info][safepoint,stats] ShenandoahFinalMarkStartEvac [             18               1 ][            15000      32200     217500     264700 ]               1
[3.788s][info][gc,start       ] GC(1) Concurrent weak references
[3.788s][info][gc,task        ] GC(1) Using 1 of 3 workers for concurrent weak references
[3.788s][info][gc,ref         ] GC(1) Encountered references: Soft: 148, Weak: 254, Final: 0, Phantom: 7
[3.788s][info][gc,ref         ] GC(1) Discovered  references: Soft: 0, Weak: 126, Final: 0, Phantom: 7
[3.788s][info][gc,ref         ] GC(1) Enqueued    references: Soft: 0, Weak: 0, Final: 0, Phantom: 0
[3.788s][info][gc             ] GC(1) Concurrent weak references 0.177ms
[3.788s][info][gc,start       ] GC(1) Concurrent weak roots
[3.788s][info][gc,task        ] GC(1) Using 1 of 3 workers for concurrent weak root
[3.788s][info][gc             ] GC(1) Concurrent weak roots 0.425ms
[3.788s][info][gc,start       ] GC(1) Concurrent cleanup
[3.789s][info][gc             ] GC(1) Concurrent cleanup 10M->1M(40M) 1.066ms
[3.789s][info][gc,ergo        ] GC(1) Free: 37120K, Max: 256K regular, 28928K humongous, Frag: 23% external, 0% internal; Reserve: 2048K, Max: 256K
[3.789s][info][gc,start       ] GC(1) Concurrent class unloading
[3.789s][info][gc,task        ] GC(1) Using 1 of 3 workers for concurrent class unloading
[3.792s][info][gc             ] GC(1) Concurrent class unloading 2.651ms
[3.792s][info][gc,start       ] GC(1) Pause Final Roots
[3.792s][info][gc             ] GC(1) Pause Final Roots 0.025ms
[3.792s][info][safepoint,stats] ShenandoahFinalRoots         [             18               1 ][            17800      24500      48800      91100 ]               1
[3.792s][info][gc,ergo        ] Free: 37120K, Max: 256K regular, 28928K humongous, Frag: 23% external, 0% internal; Reserve: 2048K, Max: 256K
[3.792s][info][gc,stats       ]
[3.792s][info][gc,stats       ] All times are wall-clock times, except per-root-class counters, that are sum over
[3.792s][info][gc,stats       ] all workers. Dividing the <total> over the root stage time estimates parallelism.
[3.792s][info][gc,stats       ]
[3.792s][info][gc,stats       ] Concurrent Reset                    277 us
[3.792s][info][gc,stats       ] Pause Init Mark (G)                 419 us
[3.792s][info][gc,stats       ] Pause Init Mark (N)                 211 us
[3.792s][info][gc,stats       ]   Update Region States                3 us
[3.792s][info][gc,stats       ] Concurrent Mark Roots               699 us, parallelism: 0.89x
[3.792s][info][gc,stats       ]   CMR: <total>                      619 us
[3.792s][info][gc,stats       ]   CMR: Thread Roots                 244 us, workers (us): 244, ---, ---,
[3.792s][info][gc,stats       ]   CMR: VM Strong Roots               39 us, workers (us):  39, ---, ---,
[3.792s][info][gc,stats       ]   CMR: CLDG Roots                   336 us, workers (us): 336, ---, ---,
[3.792s][info][gc,stats       ] Concurrent Marking                14214 us
[3.792s][info][gc,stats       ] Pause Final Mark (G)                333 us
[3.792s][info][gc,stats       ] Pause Final Mark (N)                169 us
[3.792s][info][gc,stats       ]   Finish Mark                        69 us
[3.792s][info][gc,stats       ]   Update Region States                3 us
[3.792s][info][gc,stats       ]   Choose Collection Set              44 us
[3.792s][info][gc,stats       ]   Rebuild Free Set                    5 us
[3.792s][info][gc,stats       ] Concurrent Weak References          188 us, parallelism: 0.12x
[3.792s][info][gc,stats       ]   CWRF: <total>                      22 us
[3.792s][info][gc,stats       ]   CWRF: Weak References              22 us, workers (us):  22, ---, ---,
[3.792s][info][gc,stats       ] Concurrent Weak Roots               459 us
[3.792s][info][gc,stats       ]   Roots                             338 us, parallelism: 0.76x
[3.792s][info][gc,stats       ]     CWR: <total>                    257 us
[3.792s][info][gc,stats       ]     CWR: Code Cache Roots           113 us, workers (us): 113, ---, ---,
[3.792s][info][gc,stats       ]     CWR: VM Weak Roots              136 us, workers (us): 136, ---, ---,
[3.792s][info][gc,stats       ]     CWR: CLDG Roots                   8 us, workers (us):   8, ---, ---,
[3.792s][info][gc,stats       ]   Rendezvous                         61 us
[3.792s][info][gc,stats       ] Concurrent Cleanup                 1098 us
[3.792s][info][gc,stats       ] Concurrent Class Unloading         2694 us
[3.792s][info][gc,stats       ]   Unlink Stale                     2451 us
[3.792s][info][gc,stats       ]     System Dictionary                15 us
[3.792s][info][gc,stats       ]     Weak Class Links                  0 us
[3.792s][info][gc,stats       ]     Code Roots                     2433 us
[3.792s][info][gc,stats       ]   Rendezvous                         95 us
[3.792s][info][gc,stats       ]   Purge Unlinked                     85 us
[3.792s][info][gc,stats       ]     Code Roots                       77 us
[3.792s][info][gc,stats       ]     CLDG                              7 us
[3.792s][info][gc,stats       ]     Exception Caches                  0 us
[3.792s][info][gc,stats       ] Pause Final Roots (G)               140 us
[3.792s][info][gc,stats       ] Pause Final Roots (N)                31 us
[3.792s][info][gc,stats       ]
[3.792s][info][gc,stats       ] Allocation pacing accrued:
[3.792s][info][gc,stats       ]       0 of  1014 ms (  0.0%): <total>
[3.792s][info][gc,stats       ]       0 of  1014 ms (  0.0%): <average total>
[3.792s][info][gc,stats       ]
[3.792s][info][gc,metaspace   ] Metaspace: 7960K(8128K)->7960K(8128K) NonClass: 7248K(7360K)->7248K(7360K) Class: 712K(768K)->712K(768K)
[3.792s][info][gc,ergo        ] Pacer for Idle. Initial: 819K, Alloc Tax Rate: 1.0x
thread: thread1, costs 1003 ms
[4.849s][info][gc             ] Trigger: Learning 3 of 5. Free (28621K) is below initial threshold (28671K)
[4.849s][info][gc,ergo        ] Free: 28621K, Max: 256K regular, 28416K humongous, Frag: 0% external, 0% internal; Reserve: 2048K, Max: 256K
[4.849s][info][gc,start       ] GC(2) Concurrent reset
[4.849s][info][gc,task        ] GC(2) Using 1 of 3 workers for concurrent reset
[4.849s][info][gc,ergo        ] GC(2) Pacer for Reset. Non-Taxable: 40960K
[4.850s][info][gc             ] GC(2) Concurrent reset 0.308ms
[4.850s][info][gc,start       ] GC(2) Pause Init Mark (unload classes)
[4.850s][info][gc,task        ] GC(2) Using 3 of 3 workers for init marking
[4.850s][info][gc,ergo        ] GC(2) Pacer for Mark. Expected Live: 1455K, Free: 28621K, Non-Taxable: 2862K, Alloc Tax Rate: 0.1x
[4.850s][info][gc             ] GC(2) Pause Init Mark (unload classes) 0.193ms
[4.850s][info][safepoint,stats] ShenandoahInitMark           [             18               1 ][            49700      43000     226200     318900 ]               1
[4.850s][info][gc,start       ] GC(2) Concurrent marking roots
[4.850s][info][gc,task        ] GC(2) Using 1 of 3 workers for concurrent marking roots
[4.851s][info][gc             ] GC(2) Concurrent marking roots 0.938ms
[4.851s][info][gc,start       ] GC(2) Concurrent marking (unload classes)
[4.851s][info][gc,task        ] GC(2) Using 1 of 3 workers for concurrent marking
[4.864s][info][gc             ] GC(2) Concurrent marking (unload classes) 13.338ms
[4.864s][info][gc,start       ] GC(2) Pause Final Mark (unload classes)
[4.864s][info][gc,task        ] GC(2) Using 3 of 3 workers for final marking
[4.865s][info][gc,ergo        ] GC(2) Collectable Garbage: 8532K (98%), Immediate: 8532K (98%), CSet: 0B (0%)
[4.865s][info][gc             ] GC(2) Pause Final Mark (unload classes) 0.130ms
[4.865s][info][safepoint,stats] ShenandoahFinalMarkStartEvac [             18               1 ][            15700      39700     162800     218200 ]               1
[4.865s][info][gc,start       ] GC(2) Concurrent weak references
[4.865s][info][gc,task        ] GC(2) Using 1 of 3 workers for concurrent weak references
[4.865s][info][gc,ref         ] GC(2) Encountered references: Soft: 148, Weak: 254, Final: 0, Phantom: 7
[4.865s][info][gc,ref         ] GC(2) Discovered  references: Soft: 0, Weak: 126, Final: 0, Phantom: 7
[4.865s][info][gc,ref         ] GC(2) Enqueued    references: Soft: 0, Weak: 0, Final: 0, Phantom: 0
[4.865s][info][gc             ] GC(2) Concurrent weak references 0.105ms
[4.865s][info][gc,start       ] GC(2) Concurrent weak roots
[4.865s][info][gc,task        ] GC(2) Using 1 of 3 workers for concurrent weak root
[4.865s][info][gc             ] GC(2) Concurrent weak roots 0.367ms
[4.865s][info][gc,start       ] GC(2) Concurrent cleanup
[4.866s][info][gc             ] GC(2) Concurrent cleanup 10M->1M(40M) 0.937ms
[4.866s][info][gc,ergo        ] GC(2) Free: 37120K, Max: 256K regular, 28416K humongous, Frag: 24% external, 0% internal; Reserve: 2048K, Max: 256K
[4.866s][info][gc,start       ] GC(2) Concurrent class unloading
[4.866s][info][gc,task        ] GC(2) Using 1 of 3 workers for concurrent class unloading
[4.869s][info][gc             ] GC(2) Concurrent class unloading 2.441ms
[4.869s][info][gc,start       ] GC(2) Pause Final Roots
[4.869s][info][gc             ] GC(2) Pause Final Roots 0.021ms
[4.869s][info][safepoint,stats] ShenandoahFinalRoots         [             18               1 ][            20000      42300      43800     106100 ]               1
[4.869s][info][gc,ergo        ] Free: 37120K, Max: 256K regular, 28416K humongous, Frag: 24% external, 0% internal; Reserve: 2048K, Max: 256K
[4.869s][info][gc,stats       ]
[4.869s][info][gc,stats       ] All times are wall-clock times, except per-root-class counters, that are sum over
[4.869s][info][gc,stats       ] all workers. Dividing the <total> over the root stage time estimates parallelism.
[4.869s][info][gc,stats       ]
[4.869s][info][gc,stats       ] Concurrent Reset                    333 us
[4.869s][info][gc,stats       ] Pause Init Mark (G)                 439 us
[4.869s][info][gc,stats       ] Pause Init Mark (N)                 201 us
[4.869s][info][gc,stats       ]   Update Region States                2 us
[4.869s][info][gc,stats       ] Concurrent Mark Roots               952 us, parallelism: 0.85x
[4.869s][info][gc,stats       ]   CMR: <total>                      808 us
[4.869s][info][gc,stats       ]   CMR: Thread Roots                 302 us, workers (us): 302, ---, ---,
[4.869s][info][gc,stats       ]   CMR: VM Strong Roots               43 us, workers (us):  43, ---, ---,
[4.869s][info][gc,stats       ]   CMR: CLDG Roots                   463 us, workers (us): 463, ---, ---,
[4.869s][info][gc,stats       ] Concurrent Marking                13367 us
[4.869s][info][gc,stats       ] Pause Final Mark (G)                265 us
[4.869s][info][gc,stats       ] Pause Final Mark (N)                135 us
[4.869s][info][gc,stats       ]   Finish Mark                        57 us
[4.869s][info][gc,stats       ]   Update Region States                4 us
[4.869s][info][gc,stats       ]   Choose Collection Set              36 us
[4.869s][info][gc,stats       ]   Rebuild Free Set                    4 us
[4.869s][info][gc,stats       ] Concurrent Weak References          110 us, parallelism: 0.21x
[4.869s][info][gc,stats       ]   CWRF: <total>                      23 us
[4.869s][info][gc,stats       ]   CWRF: Weak References              23 us, workers (us):  23, ---, ---,
[4.869s][info][gc,stats       ] Concurrent Weak Roots               379 us
[4.869s][info][gc,stats       ]   Roots                             307 us, parallelism: 0.87x
[4.869s][info][gc,stats       ]     CWR: <total>                    266 us
[4.869s][info][gc,stats       ]     CWR: Code Cache Roots           112 us, workers (us): 112, ---, ---,
[4.869s][info][gc,stats       ]     CWR: VM Weak Roots              142 us, workers (us): 142, ---, ---,
[4.869s][info][gc,stats       ]     CWR: CLDG Roots                  11 us, workers (us):  11, ---, ---,
[4.869s][info][gc,stats       ]   Rendezvous                         51 us
[4.869s][info][gc,stats       ] Concurrent Cleanup                  971 us
[4.869s][info][gc,stats       ] Concurrent Class Unloading         2459 us
[4.869s][info][gc,stats       ]   Unlink Stale                     2315 us
[4.869s][info][gc,stats       ]     System Dictionary                11 us
[4.869s][info][gc,stats       ]     Weak Class Links                  1 us
[4.869s][info][gc,stats       ]     Code Roots                     2301 us
[4.869s][info][gc,stats       ]   Rendezvous                         46 us
[4.869s][info][gc,stats       ]   Purge Unlinked                     62 us
[4.869s][info][gc,stats       ]     Code Roots                       57 us
[4.869s][info][gc,stats       ]     CLDG                              4 us
[4.869s][info][gc,stats       ]     Exception Caches                  0 us
[4.869s][info][gc,stats       ] Pause Final Roots (G)               169 us
[4.869s][info][gc,stats       ] Pause Final Roots (N)                27 us
[4.869s][info][gc,stats       ]
[4.869s][info][gc,stats       ] Allocation pacing accrued:
[4.869s][info][gc,stats       ]       0 of  1076 ms (  0.0%): <total>
[4.869s][info][gc,stats       ]       0 of  1076 ms (  0.0%): <average total>
[4.869s][info][gc,stats       ]
[4.869s][info][gc,metaspace   ] Metaspace: 7960K(8128K)->7960K(8128K) NonClass: 7248K(7360K)->7248K(7360K) Class: 712K(768K)->712K(768K)
[4.869s][info][gc,ergo        ] Pacer for Idle. Initial: 819K, Alloc Tax Rate: 1.0x
thread: thread1, costs 1001 ms
[5.798s][info][safepoint,stats] ChangeBreakpoints            [             18               1 ][            69600      58400      29500     157500 ]               1
[5.798s][info][safepoint,stats] ChangeBreakpoints            [             18               1 ][           140600      26300      29100     196000 ]               1
[5.798s][info][safepoint,stats] ChangeBreakpoints            [             18               1 ][           200300      22500      35100     257900 ]               1
Disconnected from the target VM, address: '127.0.0.1:51343', transport: 'socket'
[5.831s][info][gc             ] Cancelling GC: Stopping VM
[5.839s][info][gc,heap,exit   ] Heap
[5.839s][info][gc,heap,exit   ] Shenandoah Heap
[5.839s][info][gc,heap,exit   ]  40960K max, 40960K soft max, 40960K committed, 10130K used
[5.839s][info][gc,heap,exit   ]  160 x 256K regions
[5.839s][info][gc,heap,exit   ] Status: cancelled
[5.839s][info][gc,heap,exit   ] Reserved region:
[5.839s][info][gc,heap,exit   ]  - [0x00000000fd800000, 0x0000000100000000)
[5.839s][info][gc,heap,exit   ] Collection set:
[5.839s][info][gc,heap,exit   ]  - map (vanilla): 0x0000000000013f60
[5.839s][info][gc,heap,exit   ]  - map (biased):  0x0000000000010000
[5.839s][info][gc,heap,exit   ]
[5.839s][info][gc,heap,exit   ]  Metaspace       used 7962K, committed 8128K, reserved 1056768K
[5.839s][info][gc,heap,exit   ]   class space    used 713K, committed 768K, reserved 1048576K
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ] GC STATISTICS:
[5.842s][info][gc,stats       ]   "(G)" (gross) pauses include VM time: time to notify and block threads, do the pre-
[5.842s][info][gc,stats       ]         and post-safepoint housekeeping. Use -Xlog:safepoint+stats to dissect.
[5.842s][info][gc,stats       ]   "(N)" (net) pauses are the times spent in the actual GC code.
[5.842s][info][gc,stats       ]   "a" is average time for each phase, look at levels to see if average makes sense.
[5.842s][info][gc,stats       ]   "lvls" are quantiles: 0% (minimum), 25%, 50% (median), 75%, 100% (maximum).
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ]   All times are wall-clock times, except per-root-class counters, that are sum over
[5.842s][info][gc,stats       ]   all workers. Dividing the <total> over the root stage time estimates parallelism.
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ]   Pacing delays are measured from entering the pacing code till exiting it. Therefore,
[5.842s][info][gc,stats       ]   observed pacing delays may be higher than the threshold when paced thread spent more
[5.842s][info][gc,stats       ]   time in the pacing code. It usually happens when thread is de-scheduled while paced,
[5.842s][info][gc,stats       ]   OS takes longer to unblock the thread, or JVM experiences an STW pause.
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ]   Higher delay would prevent application outpacing the GC, but it will hide the GC latencies
[5.842s][info][gc,stats       ]   from the STW pause times. Pacing affects the individual threads, and so it would also be
[5.842s][info][gc,stats       ]   invisible to the usual profiling tools, but would add up to end-to-end application latency.
[5.842s][info][gc,stats       ]   Raise max pacing delay with care.
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ] Concurrent Reset               =    0.002 s (a =      612 us) (n =     3) (lvls, us =      275,      275,      275,      332,     1228)
[5.842s][info][gc,stats       ] Pause Init Mark (G)            =    0.002 s (a =      517 us) (n =     3) (lvls, us =      418,      418,      418,      439,      693)
[5.842s][info][gc,stats       ] Pause Init Mark (N)            =    0.001 s (a =      267 us) (n =     3) (lvls, us =      201,      201,      201,      211,      389)
[5.842s][info][gc,stats       ]   Update Region States         =    0.000 s (a =        4 us) (n =     3) (lvls, us =        2,        2,        2,        3,        6)
[5.842s][info][gc,stats       ] Concurrent Mark Roots          =    0.002 s (a =      831 us) (n =     3) (lvls, us =      697,      697,      697,      842,      952)
[5.842s][info][gc,stats       ]   CMR: <total>                 =    0.002 s (a =      678 us) (n =     3) (lvls, us =      607,      607,      607,      617,      808)
[5.842s][info][gc,stats       ]   CMR: Thread Roots            =    0.001 s (a =      287 us) (n =     3) (lvls, us =      242,      242,      242,      301,      314)
[5.842s][info][gc,stats       ]   CMR: VM Strong Roots         =    0.000 s (a =       38 us) (n =     3) (lvls, us =       30,       30,       30,       39,       43)
[5.842s][info][gc,stats       ]   CMR: CLDG Roots              =    0.001 s (a =      354 us) (n =     3) (lvls, us =      264,      264,      264,      334,      463)
[5.842s][info][gc,stats       ] Concurrent Marking             =    0.041 s (a =    13671 us) (n =     3) (lvls, us =    13281,    13281,    13281,    13281,    14214)
[5.842s][info][gc,stats       ] Pause Final Mark (G)           =    0.001 s (a =      294 us) (n =     3) (lvls, us =      264,      264,      264,      285,      333)
[5.842s][info][gc,stats       ] Pause Final Mark (N)           =    0.000 s (a =      153 us) (n =     3) (lvls, us =      135,      135,      135,      154,      169)
[5.842s][info][gc,stats       ]   Finish Mark                  =    0.000 s (a =       62 us) (n =     3) (lvls, us =       57,       57,       57,       59,       69)
[5.842s][info][gc,stats       ]   Update Region States         =    0.000 s (a =        3 us) (n =     3) (lvls, us =        3,        3,        3,        3,        4)
[5.842s][info][gc,stats       ]   Choose Collection Set        =    0.000 s (a =       38 us) (n =     3) (lvls, us =       35,       35,       35,       36,       44)
[5.842s][info][gc,stats       ]   Rebuild Free Set             =    0.000 s (a =        4 us) (n =     3) (lvls, us =        4,        4,        4,        4,        5)
[5.842s][info][gc,stats       ] Concurrent Thread Roots        =    0.000 s (a =      312 us) (n =     1) (lvls, us =      311,      311,      311,      311,      312)
[5.842s][info][gc,stats       ]   CTR: <total>                 =    0.000 s (a =      231 us) (n =     1) (lvls, us =      230,      230,      230,      230,      231)
[5.842s][info][gc,stats       ]   CTR: Thread Roots            =    0.000 s (a =      231 us) (n =     1) (lvls, us =      230,      230,      230,      230,      231)
[5.842s][info][gc,stats       ] Concurrent Weak References     =    0.001 s (a =      195 us) (n =     3) (lvls, us =      109,      109,      109,      188,      288)
[5.842s][info][gc,stats       ]   CWRF: <total>                =    0.000 s (a =       63 us) (n =     3) (lvls, us =       21,       21,       21,       23,      145)
[5.842s][info][gc,stats       ]   CWRF: Weak References        =    0.000 s (a =       63 us) (n =     3) (lvls, us =       21,       21,       21,       23,      145)
[5.842s][info][gc,stats       ] Concurrent Weak Roots          =    0.002 s (a =      648 us) (n =     3) (lvls, us =      377,      377,      377,      459,     1107)
[5.842s][info][gc,stats       ]   Roots                        =    0.002 s (a =      548 us) (n =     3) (lvls, us =      307,      307,      307,      338,     1000)
[5.842s][info][gc,stats       ]     CWR: <total>               =    0.001 s (a =      486 us) (n =     3) (lvls, us =      256,      256,      256,      264,      936)
[5.842s][info][gc,stats       ]     CWR: Code Cache Roots      =    0.000 s (a =      114 us) (n =     3) (lvls, us =      111,      111,      111,      111,      119)
[5.842s][info][gc,stats       ]     CWR: VM Weak Roots         =    0.001 s (a =      362 us) (n =     3) (lvls, us =      135,      135,      135,      141,      806)
[5.842s][info][gc,stats       ]     CWR: CLDG Roots            =    0.000 s (a =       10 us) (n =     3) (lvls, us =        8,        8,        8,       11,       11)
[5.842s][info][gc,stats       ]   Rendezvous                   =    0.000 s (a =       61 us) (n =     3) (lvls, us =       50,       50,       50,       61,       71)
[5.842s][info][gc,stats       ] Concurrent Cleanup             =    0.002 s (a =      731 us) (n =     3) (lvls, us =      125,      125,      125,      969,     1098)
[5.842s][info][gc,stats       ] Concurrent Class Unloading     =    0.007 s (a =     2360 us) (n =     3) (lvls, us =     1914,     1914,     1914,     2441,     2694)
[5.842s][info][gc,stats       ]   Unlink Stale                 =    0.007 s (a =     2167 us) (n =     3) (lvls, us =     1719,     1719,     1719,     2305,     2451)
[5.842s][info][gc,stats       ]     System Dictionary          =    0.000 s (a =       14 us) (n =     3) (lvls, us =       11,       11,       11,       15,       17)
[5.842s][info][gc,stats       ]     Weak Class Links           =    0.000 s (a =        0 us) (n =     3) (lvls, us =        0,        0,        0,        0,        1)
[5.842s][info][gc,stats       ]     Code Roots                 =    0.006 s (a =     2150 us) (n =     3) (lvls, us =     1699,     1699,     1699,     2285,     2433)
[5.842s][info][gc,stats       ]   Rendezvous                   =    0.000 s (a =       80 us) (n =     3) (lvls, us =       46,       46,       46,       95,       98)
[5.842s][info][gc,stats       ]   Purge Unlinked               =    0.000 s (a =       73 us) (n =     3) (lvls, us =       62,       62,       62,       72,       85)
[5.842s][info][gc,stats       ]     Code Roots                 =    0.000 s (a =       66 us) (n =     3) (lvls, us =       57,       57,       57,       63,       77)
[5.842s][info][gc,stats       ]     CLDG                       =    0.000 s (a =        6 us) (n =     3) (lvls, us =        4,        4,        4,        7,        8)
[5.842s][info][gc,stats       ]     Exception Caches           =    0.000 s (a =        0 us) (n =     3) (lvls, us =        0,        0,        0,        0,        0)
[5.842s][info][gc,stats       ] Concurrent Strong Roots        =    0.000 s (a =      324 us) (n =     1) (lvls, us =      322,      322,      322,      322,      324)
[5.842s][info][gc,stats       ]   CSR: <total>                 =    0.000 s (a =      240 us) (n =     1) (lvls, us =      238,      238,      238,      238,      240)
[5.842s][info][gc,stats       ]   CSR: VM Strong Roots         =    0.000 s (a =       23 us) (n =     1) (lvls, us =       22,       22,       22,       22,       23)
[5.842s][info][gc,stats       ]   CSR: CLDG Roots              =    0.000 s (a =      217 us) (n =     1) (lvls, us =      217,      217,      217,      217,      217)
[5.842s][info][gc,stats       ] Concurrent Evacuation          =    0.005 s (a =     4929 us) (n =     1) (lvls, us =     4922,     4922,     4922,     4922,     4929)
[5.842s][info][gc,stats       ] Pause Final Roots (G)          =    0.000 s (a =      154 us) (n =     2) (lvls, us =      139,      139,      139,      139,      169)
[5.842s][info][gc,stats       ] Pause Final Roots (N)          =    0.000 s (a =       29 us) (n =     2) (lvls, us =       27,       27,       27,       27,       31)
[5.842s][info][gc,stats       ] Pause Init Update Refs (G)     =    0.000 s (a =      252 us) (n =     1) (lvls, us =      250,      250,      250,      250,      252)
[5.842s][info][gc,stats       ] Pause Init Update Refs (N)     =    0.000 s (a =       52 us) (n =     1) (lvls, us =       52,       52,       52,       52,       52)
[5.842s][info][gc,stats       ]   Manage GCLABs                =    0.000 s (a =       18 us) (n =     1) (lvls, us =       18,       18,       18,       18,       18)
[5.842s][info][gc,stats       ] Concurrent Update Refs         =    0.009 s (a =     9260 us) (n =     1) (lvls, us =     9258,     9258,     9258,     9258,     9260)
[5.842s][info][gc,stats       ] Concurrent Update Thread Roots =    0.000 s (a =      339 us) (n =     1) (lvls, us =      338,      338,      338,      338,      339)
[5.842s][info][gc,stats       ] Pause Final Update Refs (G)    =    0.000 s (a =      207 us) (n =     1) (lvls, us =      205,      205,      205,      205,      207)
[5.842s][info][gc,stats       ] Pause Final Update Refs (N)    =    0.000 s (a =       68 us) (n =     1) (lvls, us =       68,       68,       68,       68,       68)
[5.842s][info][gc,stats       ]   Update Region States         =    0.000 s (a =        3 us) (n =     1) (lvls, us =        3,        3,        3,        3,        3)
[5.842s][info][gc,stats       ]   Trash Collection Set         =    0.000 s (a =        3 us) (n =     1) (lvls, us =        3,        3,        3,        3,        3)
[5.842s][info][gc,stats       ]   Rebuild Free Set             =    0.000 s (a =        4 us) (n =     1) (lvls, us =        4,        4,        4,        4,        4)
[5.842s][info][gc,stats       ] Concurrent Cleanup             =    0.001 s (a =     1044 us) (n =     1) (lvls, us =     1035,     1035,     1035,     1035,     1044)
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ] Under allocation pressure, concurrent cycles may cancel, and either continue cycle
[5.842s][info][gc,stats       ] under stop-the-world pause or result in stop-the-world Full GC. Increase heap size,
[5.842s][info][gc,stats       ] tune GC heuristics, set more aggressive pacing delay, or lower allocation rate
[5.842s][info][gc,stats       ] to avoid Degenerated and Full GC cycles.
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ]     3 successful concurrent GCs
[5.842s][info][gc,stats       ]       0 invoked explicitly
[5.842s][info][gc,stats       ]       0 invoked implicitly
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ]     0 Degenerated GCs
[5.842s][info][gc,stats       ]       0 caused by allocation failure
[5.842s][info][gc,stats       ]       0 upgraded to Full GC
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ]     0 Full GCs
[5.842s][info][gc,stats       ]       0 invoked explicitly
[5.842s][info][gc,stats       ]       0 invoked implicitly
[5.842s][info][gc,stats       ]       0 caused by allocation failure
[5.842s][info][gc,stats       ]       0 upgraded from Degenerated GC
[5.842s][info][gc,stats       ]
[5.842s][info][gc,stats       ]
[5.844s][info][safepoint,stats] Cleanup                              1
[5.844s][info][safepoint,stats] ChangeBreakpoints                    6
[5.844s][info][safepoint,stats] ShenandoahInitMark                   3
[5.844s][info][safepoint,stats] ShenandoahFinalMarkStartEvac         3
[5.844s][info][safepoint,stats] ShenandoahInitUpdateRefs             1
[5.844s][info][safepoint,stats] ShenandoahFinalUpdateRefs            1
[5.844s][info][safepoint,stats] ShenandoahFinalRoots                 2
[5.844s][info][safepoint,stats] Exit                                 1
[5.844s][info][safepoint,stats] CleanClassLoaderDataMetaspaces         1
[5.844s][info][safepoint,stats] Maximum sync time  9699800 ns
[5.844s][info][safepoint,stats] Maximum vm operation time (except for Exit VM operation)  3306100 ns

Process finished with exit code 130

 */