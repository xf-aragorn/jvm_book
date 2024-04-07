package org.fenixsoft.jvm.chapter12;

import jdk.internal.vm.annotation.Contended;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Contended
public class VolatileTest2{
    @Contended
    private volatile int myField;
    private int field2;
    private static final VarHandle MY_FIELD_HANDLE;

    static {
        try {
            MY_FIELD_HANDLE = MethodHandles.lookup().findVarHandle(VolatileTest2.class, "myField", int.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSomething(int index) {
        // read myField using VarHandle
        int value = (int) MY_FIELD_HANDLE.getAcquire(this);

        // modify myField using VarHandle
        MY_FIELD_HANDLE.setRelease(this, value + 1);
//        System.out.println("now value: "+myField + ", index: "+index);
    }
    public void doSomething2(int index) {
        field2++;
//        System.out.println("now value: "+field2 + ", index: "+index);
    }
    public static void  main(String[] args){
        VolatileTest2 instance =  new VolatileTest2();
        instance.doSomething(0);
        System.out.println("结果1："+instance.myField);
        instance.doSomething(1);
        System.out.println("结果2："+instance.myField);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(200, 200,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
//        List<Future<?>> list = IntStream.range(0,10000).mapToObj((i)-> executor.submit(()->instance.doSomething(i))).collect(Collectors.toList());
        IntStream.range(0,100000).parallel().mapToObj((i)-> executor.submit(()->instance.doSomething(i))).forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        IntStream.range(0,100000).parallel().mapToObj((i)-> executor.submit(()->instance.doSomething2(i))).forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
//        try {
//            executor.wait(100000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("结果3："+instance.myField);
        System.out.println("结果4 field2："+instance.field2);
        assert instance.myField == 10002;
        assert instance.field2 == 10000;
        executor.shutdownNow();
    }
}