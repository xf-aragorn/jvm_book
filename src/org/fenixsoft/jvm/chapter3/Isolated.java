package org.fenixsoft.jvm.chapter3;

import jdk.internal.vm.annotation.Contended;
import org.openjdk.jol.info.ClassLayout;

public class Isolated {
    @Contended
    private int v1;

    @Contended
    private long v2;

    public static void main(String[] args){
        Isolated obj = new Isolated();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
    }
}

/*
C:\Users\xufeng\.jdks\corretto-11.0.17\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:52564,suspend=y,server=n -XX:-RestrictContended -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.Isolated
Connected to the target VM, address: '127.0.0.1:52564', transport: 'socket'
Error while accessing @Contended value: class org.openjdk.jol.vm.ContendedSupport cannot access interface jdk.internal.vm.annotation.Contended (in module java.base) because module java.base does not export jdk.internal.vm.annotation to unnamed module @61a485d2

# WARNING: Unable to get Instrumentation. Dynamic Attach failed. You may add this JAR as -javaagent manually, or supply -Djdk.attach.allowAttachSelf
org.fenixsoft.jvm.chapter3.Isolated object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   4        (object header: class)    0x00067840
 12 128        (alignment/padding gap)
140   4    int Isolated.v1               0
144 128        (alignment/padding gap)
272   8   long Isolated.v2               0
Instance size: 280 bytes
Space losses: 256 bytes internal + 0 bytes external = 256 bytes total

 */

/*
C:\Users\xufeng\.jdks\corretto-11.0.17\bin\java.exe -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:52640,suspend=y,server=n -XX:-RestrictContended -XX:ContendedPaddingWidth=64 -javaagent:C:\Users\xufeng\AppData\Local\JetBrains\IntelliJIdea2022.2\captureAgent\debugger-agent.jar=file:/C:/Users/xufeng/AppData/Local/Temp/capture2.props -Dfile.encoding=UTF-8 -classpath "E:\src\jvm-book-3rd\target\classes;E:\src\jvm-book-3rd\lib\asm-3.3.1.jar;E:\src\jvm-book-3rd\lib\cglib-2.2.2.jar;E:\src\jvm-book-3rd\src\org\fenixsoft\jvm\chapter5\EclipseStartTime_1.0.0.201011281102.jar;E:\java-maven-repository\repository\org\projectlombok\lombok\1.18.8\lombok-1.18.8.jar;E:\java-maven-repository\repository\io\btrace\btrace-client\2.2.2\btrace-client-2.2.2.jar;E:\java-maven-repository\repository\org\openjdk\jol\jol-core\0.16\jol-core-0.16.jar;D:\Program Files\JetBrains\IntelliJ IDEA\lib\idea_rt.jar" org.fenixsoft.jvm.chapter3.Isolated
Connected to the target VM, address: '127.0.0.1:52640', transport: 'socket'
Error while accessing @Contended value: class org.openjdk.jol.vm.ContendedSupport cannot access interface jdk.internal.vm.annotation.Contended (in module java.base) because module java.base does not export jdk.internal.vm.annotation to unnamed module @61a485d2

# WARNING: Unable to get Instrumentation. Dynamic Attach failed. You may add this JAR as -javaagent manually, or supply -Djdk.attach.allowAttachSelf
org.fenixsoft.jvm.chapter3.Isolated object internals:
OFF  SZ   TYPE DESCRIPTION               VALUE
  0   8        (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   4        (object header: class)    0x00067840
 12  64        (alignment/padding gap)
 76   4    int Isolated.v1               0
 80  64        (alignment/padding gap)
144   8   long Isolated.v2               0
Instance size: 152 bytes
Space losses: 128 bytes internal + 0 bytes external = 128 bytes total

 */