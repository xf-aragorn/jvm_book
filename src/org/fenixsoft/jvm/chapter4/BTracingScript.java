package org.fenixsoft.jvm.chapter4;

//import com.sun.btrace.annotations.*;
//import static com.sun.btrace.BTraceUtils.*;
import org.openjdk.btrace.core.annotations.*;
import static org.openjdk.btrace.core.BTraceUtils.*;

/**
 * Btrace应用示例： https://github.com/btraceio/btrace/tree/master/btrace-dist/src/main/resources/samples
 *
 * btrace -javaagent:btrace-agent-2.2.2.jar=debug，trusted，script:?
 */
@BTrace
public class BTracingScript {
    @OnMethod(
            clazz="org.fenixsoft.jvm.chapter4.BTraceTest",
            method="add",
            location=@Location(Kind.RETURN)
    )

    public static void func(@Self org.fenixsoft.jvm.chapter4.BTraceTest instance, int a, int b, @Return int result) {
        println("调用堆栈:");
        jstack();
        println(strcat("方法参数A:",str(a)));
        println(strcat("方法参数B:",str(b)));
        println(strcat("方法结果:",str(result)));
    }

    @OnExit
    public static void onexit(int code) {
        println("BTrace program exits!");
    }

    // We just put @OnTimer probe and exit BTrace
    // program when the count reaches 100.
    private static volatile int i;

    @OnTimer(1000)
    public static void ontime() {
        println("hello");
            i++;
        if (i == 100) {
            // note that this exits the BTrace client
            // and not the traced program (which would
            // be a destructive action!).
            Sys.exit(0);
        }
    }
    @OnMethod(
            clazz = "+java.lang.ClassLoader",
            method = "defineClass",
            location = @Location(Kind.RETURN)
    )
    public static void defineclass(@Return Class<?> cl) {
        println("loaded " + Reflective.name(cl));
        Threads.jstack();
        println("==========================");
    }
}