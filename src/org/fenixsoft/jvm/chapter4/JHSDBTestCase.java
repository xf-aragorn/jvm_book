package org.fenixsoft.jvm.chapter4;

/**
 * staticObj、instanceObj、localObj存放在哪里？
 *  启动参数： -Xmx10m -XX:+UseSerialGC -XX:-UseCompressedOops
 */
public class JHSDBTestCase {

    static class Test {
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done");    // 这里设一个断点
        }
    }

    private static class ObjectHolder {}

    public static void main(String[] args) {
        Test test = new JHSDBTestCase.Test();
        test.foo();
    }
}

/*
在软件的“windows”菜单中，打开console，命令help会输出所有可用的命令
 */