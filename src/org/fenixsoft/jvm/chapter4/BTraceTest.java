package org.fenixsoft.jvm.chapter4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zzm
 * 如果在visualVM中通过插件安装Btrace使用，那必须配置用OracleJDK来启动VisualVM！否则Btrace插件会报错，用不了！
 */
public class BTraceTest {

    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) throws IOException {
        BTraceTest test = new BTraceTest();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 10; i++) {
            reader.readLine();
            int a = (int) Math.round(Math.random() * 1000);
            int b = (int) Math.round(Math.random() * 1000);
            System.out.println(test.add(a, b));
        }
    }
}


/* BTrace Script Template
import com.sun.btrace.annotations.*;
        import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class TracingScript {
    @OnMethod(
            clazz="org.fenixsoft.jvm.chapter4.BTraceTest",
            method="add",
            location=@Location(Kind.RETURN)
    )

    public static void func(@Self org.fenixsoft.jvm.chapter4.BTraceTest instance,int a,int b,@Return int result) {
        println("调用堆栈:");
        jstack();
        println(strcat("方法参数A:",str(a)));
        println(strcat("方法参数B:",str(b)));
        println(strcat("方法结果:",str(result)));
    }
}
*/

/*
把jdk/lib目录下的tools.jar复制到jar/lib目录下即可：
btrace WARNING: BTrace system classpath resource [C:\Users\xufeng\.jdks\corretto-1.8.0_342\jre/lib/tools.jar] does not exist.

 */