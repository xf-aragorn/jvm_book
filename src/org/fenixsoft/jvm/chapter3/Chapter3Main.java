package org.fenixsoft.jvm.chapter3;

public class Chapter3Main {

    //vm args: -verbose:gc -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
    public static void main(String[] args){
        TestSerialGCAllocation.testAllocation();
    }
}
