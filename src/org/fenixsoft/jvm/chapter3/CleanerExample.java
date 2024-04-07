package org.fenixsoft.jvm.chapter3;

import java.lang.ref.Cleaner;

public class CleanerExample {

    // This Cleaner is shared by all CleanerExample instances
    private static final Cleaner CLEANER = Cleaner.create();
    private final State state;

    public CleanerExample(String id) {
        state = new State(id);
        //当this对象被回收时（成为虚引用），执行state通知
        CLEANER.register(this, state);
    }

    // Cleaning action class for CleanerExample
    private static class State implements Runnable {
        final private String id;

        private State(String id) {
            this.id = id;
            System.out.println("Created cleaning action for " + this.id);
        }

        @Override
        public void run() {
            System.out.println("Cleaner garbage collected " + this.id);
        }
    }

    public static void main(String[] args) {
        CleanerExample myObject1 = new CleanerExample("myObject1");

        // Make myObject1 unreachable
        myObject1 = null;

        System.out.println("-- Give the GC a chance to schedule the Cleaner --");
        for (int i = 0; i < 100; i++) {

            // Calling System.gc() in a loop is usually sufficient to trigger
            // cleanup in a small program like this.
            System.gc();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {}
        }
        System.out.println("-- Finished --");
    }
}
