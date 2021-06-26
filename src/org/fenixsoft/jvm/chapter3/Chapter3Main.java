package org.fenixsoft.jvm.chapter3;

public class Chapter3Main {

    //vm args: -verbose:gc -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
    //vm args: -verbose:gc -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGC -XX:SurvivorRatio=8  //打印粗略信息
    public static void main(String[] args){
        TestSerialGCAllocation.testAllocation();
    }
}
