package org.fenixsoft.jvm.chapter3;

/* GCPerformance.java
 * Copyright (c) HerongYang.com. All Rights Reserved.
 * site: https://www.herongyang.com/Java-GC/Performance-Test-Program-GCPerformance.html
 */
class GCPerformance {
    static MyList objList = null;
    static int objSize = 1024; // in KB, default = 1 MB
    static int baseSize = 32;  // # of objects in the base
    static int chunkSize = 32; // # of objects in chunk
    static int wait = 1000;    // in milliseconds: 1 second
    static int warmup = 256;   // warmup loops: 256*32 = 8GB
    public static void main(String[] arg) {
        if (arg.length>0) objSize = Integer.parseInt(arg[0]);
        if (arg.length>1) baseSize = Integer.parseInt(arg[1]);
        if (arg.length>2) chunkSize = Integer.parseInt(arg[2]);
        if (arg.length>3) wait = Integer.parseInt(arg[3]);
        if (arg.length>4) warmup = Integer.parseInt(arg[4]);
        System.out.println("Parameters: "+"Size="+objSize+"KB"
                +", Base="+baseSize +", Chunk="+chunkSize
                +", Wait="+wait+"ms" +", Warmup="+warmup);
        objList = new MyList();
        myTest();
    }
    public static void myTest() {
        for (int m=0; m<baseSize; m++) {
            objList.add(new MyObject());
        }
        for (int k=0; k<warmup; k++) {
            for (int m=0; m<chunkSize; m++) {
                objList.add(new MyObject());
            }
            for (int m=0; m<chunkSize; m++) {
                objList.removeTail();
            }
        }

        Runtime rt = Runtime.getRuntime();
        System.out.println("Real:Exec  Lat.     Throughput      "
                +"Total:Free  Proc.");
        System.out.println("Time:Time  ms/o  Ave:Min:Max:Chunk  "
                +" Mem.:Mem.   Obj.");
        long dt0 = System.currentTimeMillis();
        long dt1 = System.currentTimeMillis();
        long minPerf = 999999;
        long maxPerf = 0;
        long count = 0;
        while (true) {
            for (int m=0; m<chunkSize; m++) {
                objList.add(new MyObject());
            }
            for (int m=0; m<chunkSize; m++) {
                objList.removeTail();
            }
            count++;

            mySleep(wait);
            long tm = rt.totalMemory()/1024;
            long fm = rt.freeMemory()/1024;
            long dt2 = System.currentTimeMillis();
            long dt = dt2 - dt0;
            long de = dt - (count*wait);
            long perf = (1000*chunkSize)/(dt2-dt1-wait); // per second
            if (perf<minPerf) minPerf = perf;
            if (perf>maxPerf) maxPerf = perf;

            System.out.println("Real time in millis:"+(dt)      // Real time in millis
                    +";Execution time in millis:"+(de)                 // Execution time in millis
                    +" ;Latency (ms/1000 objects): "+(1000000/minPerf)   // Latency (ms/1000 objects)
                    +" ;Average throughput: "+((1000*count*chunkSize)/de) // Average throughput
                    +";Best throughput:"+minPerf              // Best throughput
                    +";Worst throughput:"+maxPerf              // Worst throughput
                    +";Chunk throughput:"+perf                 // Chunk throughput
                    +";memory in KB：  Total："+tm+",free:"+fm           // Total and free memory in KB
                    +"; Objects processed: "+(count*chunkSize)); // Objects processed
            dt1 = dt2;
        }
    }
    static void mySleep(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            System.out.println("Interreupted...");
        }
    }
    static class MyObject {
        private long[] obj = null;
        public MyObject next = null;
        public MyObject prev = null;
        public MyObject() {
            obj = new long[objSize*128]; // 128*8=1024 bytes
            for (int i=0; i<objSize*128; i++) {
                obj[i] = i/2+i/3+i/4+i/5; // some work load
            }
        }
    }
    static class MyList {
        MyObject head = null;
        MyObject tail = null;
        void add(MyObject o) {
            if (head==null) {
                head = o;
                tail = o;
            } else {
                o.prev = head;
                head.next = o;
                head = o;
            }
        }
        void removeTail() {
            if (tail!=null) {
                if (tail.next==null) {
                    tail = null;
                    head = null;
                } else {
                    tail = tail.next;
                    tail.prev = null;
                }
            }
        }
    }
}
