package org.fenixsoft.jvm.chapter3;

import jdk.internal.vm.annotation.Contended;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class MemoryLayout {
    public class Isolated {

        @Contended
        private int v1;

        @Contended
        private long v2;
    }
    static volatile Object consumer;
    public static void main(String[] args) {

        Object instance = new Object();
        long lastAddr = VM.current().addressOf(instance);
        ClassLayout layout = ClassLayout.parseInstance(instance);


        for (int i = 0; i < 10_000; i++) {

            long currentAddr = VM.current().addressOf(instance);
            if (currentAddr != lastAddr) {
                System.out.println(layout.toPrintable());
            }

            for (int j = 0; j < 10_000; j++) {
                consumer = new Object();
            }

            lastAddr = currentAddr;
        }
    }
}
