package org.fenixsoft.jvm.chapter8;

public class GCDemo {
    public static void main(String[] args){
        for(int i = 0;i< 1000;i++) {
            {
//            {
                byte[] placeholder = new byte[64 * 1024 * 1024];
//            }
                System.out.println("index:" + i);
                byte[] placeholder2 = new byte[64 * 1024 * 1024];
                placeholder = null;
            }
            int a = 0;
            byte[] placeholder3 = new byte[64 * 1024 * 1024];
//        int a = 0;
//            System.gc();
        }
    }
}
