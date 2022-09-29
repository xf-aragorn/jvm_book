package org.fenixsoft.jvm.chapter7;

/**
 * 非主动使用类字段演示
 **/
public class NotInitialization_1 {
    //读取或设置一个类型的静态字段的时候会触发该类的初始化，value字段是SuperClass的，所以SuperClass初始化了，但是SubClass不会初始化！
    public static void main(String[] args) {
        System.out.println(SubClass.value);
    }

}
