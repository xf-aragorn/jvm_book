package org.fenixsoft.jvm.chapter4;

import java.util.Arrays;

public class Bar {
    int a = 1;
    static int b = 2;
    double d = 0.3;
    String s1 = "中文字符串";
    final Long along = 1L;
    public int sum(int c) {
        return a + b + c;
    }
    Object obj = new Object();

    public void sync(){
        synchronized (obj){
            System.out.println("monitor...");
        }
    }

    public  synchronized void sync2(){
       System.out.println("monitor...");
    }
    public static void invokedynamictest(int c) {
        Runnable r = () -> System.out.println("thisis invokedynamic");
        r.run();
    }
    public static void main(String[] args) {
        new Bar().sum(3);
        invokedynamictest(111);
//        sync();
    }
}


/*
Windows PowerShell
Windows PowerShell
Windows PowerShell
版权所有 (C) Microsoft Corporation。保留所有权利。

尝试新的跨平台 PowerShell https://aka.ms/pscore6


*/



/*

E:\src\jdk\openjdk\build\windows-x86_64-server-fastdebug\jdk\bin\java.exe -XX:+PrintAssembly -Xcomp "-XX:CompileCommand=dontinline,*Bar.sum" "-XX:CompileCommand=compileonly,*Bar.sum"  org.fenixsoft.jvm.chapter4.Bar
OpenJDK 64-Bit Server VM warning: PrintAssembly is enabled; turning on DebugNonSafepoints to gain additional output
CompileCommand: dontinline *Bar.sum bool dontinline = true
CompileCommand: compileonly *Bar.sum bool compileonly = true

Compiled method (n/a)     209    1     n 0       java.lang.invoke.MethodHandle::linkToStatic(LLLLLLL)L (native)
 total in heap  [0x000001a775de0010,0x000001a775de02d0] = 704
 relocation     [0x000001a775de0188,0x000001a775de01d0] = 72
 main code      [0x000001a775de01e0,0x000001a775de02d0] = 240

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f7a47b8} 'linkToStatic' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/Object'
  # parm3:    rdi:rdi   = 'java/lang/Object'
  # parm4:    rsi:rsi   = 'java/lang/Object'
  # parm5:    rcx:rcx   = 'java/lang/Object'
  # parm6:    [sp+0x0]   = 'java/lang/invoke/MemberName'  (sp of caller)
  0x000001a775de01e0:   mov    0x8(%rsp),%rbx
 ;; verify_klass {
  0x000001a775de01e5:   test   %rbx,%rbx
  0x000001a775de01e8:   je     0x000001a775de022e
  0x000001a775de01ee:   push   %rdi
  0x000001a775de01ef:   push   %r10
  0x000001a775de01f1:   mov    0x8(%rbx),%edi
  0x000001a775de01f4:   movabs $0x800000000,%r10
  0x000001a775de01fe:   add    %r10,%rdi
  0x000001a775de0201:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de020b:   cmp    (%r10),%rdi
  0x000001a775de020e:   je     0x000001a775de024a
  0x000001a775de0214:   mov    0x40(%rdi),%rdi
  0x000001a775de0218:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de0222:   cmp    (%r10),%rdi
  0x000001a775de0225:   je     0x000001a775de024a
  0x000001a775de022b:   pop    %r10
  0x000001a775de022d:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de022e:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de0238:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de023c:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0246:   call   *%r10
  0x000001a775de0249:   hlt
 ;; L_ok:
  0x000001a775de024a:   pop    %r10
  0x000001a775de024c:   pop    %rdi
 ;; } verify_klass
 ;; verify_ref_kind {
  0x000001a775de024d:   mov    0xc(%rbx),%eax
  0x000001a775de0250:   shr    $0x18,%eax
  0x000001a775de0253:   and    $0xf,%eax
  0x000001a775de0256:   cmp    $0x6,%eax
  0x000001a775de0259:   je     0x000001a775de027b
 ;; verify_ref_kind expected 6
  0x000001a775de025f:   movabs $0x1a77fee10d0,%rcx          ;   {external_word}
  0x000001a775de0269:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de026d:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0277:   call   *%r10
  0x000001a775de027a:   hlt
 ;; } verify_ref_kind
  0x000001a775de027b:   mov    0x24(%rbx),%ebx
  0x000001a775de027e:   push   %r10
  0x000001a775de0280:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de028a:   cmp    (%r10),%r12
  0x000001a775de028d:   je     0x000001a775de02af
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de0293:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de029d:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de02a1:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de02ab:   call   *%r10
  0x000001a775de02ae:   hlt
  0x000001a775de02af:   pop    %r10
  0x000001a775de02b1:   shl    $0x3,%rbx
  0x000001a775de02b5:   mov    0x10(%rbx),%rbx
  0x000001a775de02b9:   test   %rbx,%rbx
  0x000001a775de02bc:   je     0x000001a775de02c5
  0x000001a775de02c2:   jmp    *0x50(%rbx)
  0x000001a775de02c5:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de02ca:   hlt
  0x000001a775de02cb:   hlt
  0x000001a775de02cc:   hlt
  0x000001a775de02cd:   hlt
  0x000001a775de02ce:   hlt
  0x000001a775de02cf:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)     417    2     n 0       java.lang.invoke.MethodHandle::linkToStatic(LLL)L (native)
 total in heap  [0x000001a775de0310,0x000001a775de05c8] = 696
 relocation     [0x000001a775de0488,0x000001a775de04d0] = 72
 main code      [0x000001a775de04e0,0x000001a775de05c8] = 232

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f7f90a8} 'linkToStatic' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de04e0:   test   %r9,%r9
  0x000001a775de04e3:   je     0x000001a775de052a
  0x000001a775de04e9:   push   %rdi
  0x000001a775de04ea:   push   %r10
  0x000001a775de04ec:   mov    0x8(%r9),%edi
  0x000001a775de04f0:   movabs $0x800000000,%r10
  0x000001a775de04fa:   add    %r10,%rdi
  0x000001a775de04fd:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de0507:   cmp    (%r10),%rdi
  0x000001a775de050a:   je     0x000001a775de0546
  0x000001a775de0510:   mov    0x40(%rdi),%rdi
  0x000001a775de0514:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de051e:   cmp    (%r10),%rdi
  0x000001a775de0521:   je     0x000001a775de0546
  0x000001a775de0527:   pop    %r10
  0x000001a775de0529:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de052a:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de0534:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0538:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0542:   call   *%r10
  0x000001a775de0545:   hlt
 ;; L_ok:
  0x000001a775de0546:   pop    %r10
  0x000001a775de0548:   pop    %rdi
 ;; } verify_klass
 ;; verify_ref_kind {
  0x000001a775de0549:   mov    0xc(%r9),%eax
  0x000001a775de054d:   shr    $0x18,%eax
  0x000001a775de0550:   and    $0xf,%eax
  0x000001a775de0553:   cmp    $0x6,%eax
  0x000001a775de0556:   je     0x000001a775de0578
 ;; verify_ref_kind expected 6
  0x000001a775de055c:   movabs $0x1a77fedfff0,%rcx          ;   {external_word}
  0x000001a775de0566:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de056a:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0574:   call   *%r10
  0x000001a775de0577:   hlt
 ;; } verify_ref_kind
  0x000001a775de0578:   mov    0x24(%r9),%ebx
  0x000001a775de057c:   push   %r10
  0x000001a775de057e:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de0588:   cmp    (%r10),%r12
  0x000001a775de058b:   je     0x000001a775de05ad
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de0591:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de059b:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de059f:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de05a9:   call   *%r10
  0x000001a775de05ac:   hlt
  0x000001a775de05ad:   pop    %r10
  0x000001a775de05af:   shl    $0x3,%rbx
  0x000001a775de05b3:   mov    0x10(%rbx),%rbx
  0x000001a775de05b7:   test   %rbx,%rbx
  0x000001a775de05ba:   je     0x000001a775de05c3
  0x000001a775de05c0:   jmp    *0x50(%rbx)
  0x000001a775de05c3:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
--------------------------------------------------------------------------------

Compiled method (n/a)     481    3     n 0       java.lang.invoke.MethodHandle::invokeBasic(LLLLLL)L (native)
 total in heap  [0x000001a775de0610,0x000001a775de08a8] = 664
 relocation     [0x000001a775de0788,0x000001a775de07d0] = 72
 main code      [0x000001a775de07e0,0x000001a775de08a8] = 200

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f9027f8} 'invokeBasic' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # this:     rdx:rdx   = 'java/lang/invoke/MethodHandle'
  # parm0:    r8:r8     = 'java/lang/Object'
  # parm1:    r9:r9     = 'java/lang/Object'
  # parm2:    rdi:rdi   = 'java/lang/Object'
  # parm3:    rsi:rsi   = 'java/lang/Object'
  # parm4:    rcx:rcx   = 'java/lang/Object'
  # parm5:    [sp+0x0]   = 'java/lang/Object'  (sp of caller)
 ;; jump_to_lambda_form {
  0x000001a775de07e0:   mov    0x14(%rdx),%ebx
  0x000001a775de07e3:   push   %r10
  0x000001a775de07e5:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de07ef:   cmp    (%r10),%r12
  0x000001a775de07f2:   je     0x000001a775de0814
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de07f8:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de0802:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0806:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0810:   call   *%r10
  0x000001a775de0813:   hlt
  0x000001a775de0814:   pop    %r10
  0x000001a775de0816:   shl    $0x3,%rbx
  0x000001a775de081a:   mov    0x28(%rbx),%ebx
  0x000001a775de081d:   push   %r10
  0x000001a775de081f:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de0829:   cmp    (%r10),%r12
  0x000001a775de082c:   je     0x000001a775de084e
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de0832:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de083c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0840:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de084a:   call   *%r10
  0x000001a775de084d:   hlt
  0x000001a775de084e:   pop    %r10
  0x000001a775de0850:   shl    $0x3,%rbx
  0x000001a775de0854:   mov    0x24(%rbx),%ebx
  0x000001a775de0857:   push   %r10
  0x000001a775de0859:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de0863:   cmp    (%r10),%r12
  0x000001a775de0866:   je     0x000001a775de0888
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de086c:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de0876:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de087a:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0884:   call   *%r10
  0x000001a775de0887:   hlt
  0x000001a775de0888:   pop    %r10
  0x000001a775de088a:   shl    $0x3,%rbx
  0x000001a775de088e:   mov    0x10(%rbx),%rbx
  0x000001a775de0892:   test   %rbx,%rbx
  0x000001a775de0895:   je     0x000001a775de089e
  0x000001a775de089b:   jmp    *0x50(%rbx)
  0x000001a775de089e:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
 ;; } jump_to_lambda_form
  0x000001a775de08a3:   hlt
  0x000001a775de08a4:   hlt
  0x000001a775de08a5:   hlt
  0x000001a775de08a6:   hlt
  0x000001a775de08a7:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)     538    4     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LLLLLLLL)L (native)
 total in heap  [0x000001a775de0910,0x000001a775de0d30] = 1056
 relocation     [0x000001a775de0a88,0x000001a775de0b18] = 144
 main code      [0x000001a775de0b20,0x000001a775de0d30] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f902c78} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/Object'
  # parm3:    rdi:rdi   = 'java/lang/Object'
  # parm4:    rsi:rsi   = 'java/lang/Object'
  # parm5:    rcx:rcx   = 'java/lang/Object'
  # parm6:    [sp+0x0]   = 'java/lang/Object'  (sp of caller)
  # parm7:    [sp+0x8]   = 'java/lang/invoke/MemberName'
  0x000001a775de0b20:   mov    0x10(%rsp),%rbx
 ;; verify_klass {
  0x000001a775de0b25:   test   %rbx,%rbx
  0x000001a775de0b28:   je     0x000001a775de0b6e
  0x000001a775de0b2e:   push   %rdi
  0x000001a775de0b2f:   push   %r10
  0x000001a775de0b31:   mov    0x8(%rbx),%edi
  0x000001a775de0b34:   movabs $0x800000000,%r10
  0x000001a775de0b3e:   add    %r10,%rdi
  0x000001a775de0b41:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de0b4b:   cmp    (%r10),%rdi
  0x000001a775de0b4e:   je     0x000001a775de0b8a
  0x000001a775de0b54:   mov    0x40(%rdi),%rdi
  0x000001a775de0b58:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de0b62:   cmp    (%r10),%rdi
  0x000001a775de0b65:   je     0x000001a775de0b8a
  0x000001a775de0b6b:   pop    %r10
  0x000001a775de0b6d:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de0b6e:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de0b78:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0b7c:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0b86:   call   *%r10
  0x000001a775de0b89:   hlt
 ;; L_ok:
  0x000001a775de0b8a:   pop    %r10
  0x000001a775de0b8c:   pop    %rdi
 ;; } verify_klass
  0x000001a775de0b8d:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de0b90:   mov    0x8(%rdx),%r10d
  0x000001a775de0b94:   movabs $0x800000000,%r11
  0x000001a775de0b9e:   add    %r11,%r10
  0x000001a775de0ba1:   mov    0x18(%rbx),%r11d
  0x000001a775de0ba5:   push   %r10
  0x000001a775de0ba7:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de0bb1:   cmp    (%r10),%r12
  0x000001a775de0bb4:   je     0x000001a775de0bd6
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de0bba:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de0bc4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0bc8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0bd2:   call   *%r10
  0x000001a775de0bd5:   hlt
  0x000001a775de0bd6:   pop    %r10
  0x000001a775de0bd8:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de0bdc:   test   %r11,%r11
  0x000001a775de0bdf:   je     0x000001a775de0c26
  0x000001a775de0be5:   push   %rdi
  0x000001a775de0be6:   push   %r10
  0x000001a775de0be8:   mov    0x8(%r11),%edi
  0x000001a775de0bec:   movabs $0x800000000,%r10
  0x000001a775de0bf6:   add    %r10,%rdi
  0x000001a775de0bf9:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de0c03:   cmp    (%r10),%rdi
  0x000001a775de0c06:   je     0x000001a775de0c42
  0x000001a775de0c0c:   mov    0x40(%rdi),%rdi
  0x000001a775de0c10:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de0c1a:   cmp    (%r10),%rdi
  0x000001a775de0c1d:   je     0x000001a775de0c42
  0x000001a775de0c23:   pop    %r10
  0x000001a775de0c25:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de0c26:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de0c30:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0c34:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0c3e:   call   *%r10
  0x000001a775de0c41:   hlt
 ;; L_ok:
  0x000001a775de0c42:   pop    %r10
  0x000001a775de0c44:   pop    %rdi
 ;; } verify_klass
  0x000001a775de0c45:   mov    0x10(%r11),%r11
  0x000001a775de0c49:   cmp    %r11,%r10
  0x000001a775de0c4c:   je     0x000001a775de0cb3
  0x000001a775de0c52:   mov    0x1c(%r11),%eax
  0x000001a775de0c56:   cmp    (%r10,%rax,1),%r11
  0x000001a775de0c5a:   je     0x000001a775de0cb3
  0x000001a775de0c60:   cmp    $0x28,%eax
  0x000001a775de0c63:   jne    0x000001a775de0c97
  0x000001a775de0c69:   mov    %r11,%rax
  0x000001a775de0c6c:   push   %rcx
  0x000001a775de0c6d:   push   %rdi
  0x000001a775de0c6e:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de0c78:   incl   (%rcx)
  0x000001a775de0c7a:   mov    0x30(%r10),%rdi
  0x000001a775de0c7e:   mov    (%rdi),%ecx
  0x000001a775de0c80:   add    $0x8,%rdi
  0x000001a775de0c84:   test   %rax,%rax
  0x000001a775de0c87:   repnz scas %es:(%rdi),%rax
  0x000001a775de0c8a:   pop    %rdi
  0x000001a775de0c8b:   pop    %rcx
  0x000001a775de0c8c:   jne    0x000001a775de0c97
  0x000001a775de0c8e:   mov    %r11,0x28(%r10)
  0x000001a775de0c92:   jmp    0x000001a775de0cb3
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de0c97:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de0ca1:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0ca5:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0caf:   call   *%r10
  0x000001a775de0cb2:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de0cb3:   mov    0xc(%rbx),%eax
  0x000001a775de0cb6:   shr    $0x18,%eax
  0x000001a775de0cb9:   and    $0xf,%eax
  0x000001a775de0cbc:   cmp    $0x7,%eax
  0x000001a775de0cbf:   je     0x000001a775de0ce1
 ;; verify_ref_kind expected 7
  0x000001a775de0cc5:   movabs $0x1a77fee04f0,%rcx          ;   {external_word}
  0x000001a775de0ccf:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0cd3:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0cdd:   call   *%r10
  0x000001a775de0ce0:   hlt
 ;; } verify_ref_kind
  0x000001a775de0ce1:   mov    0x24(%rbx),%ebx
  0x000001a775de0ce4:   push   %r10
  0x000001a775de0ce6:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de0cf0:   cmp    (%r10),%r12
  0x000001a775de0cf3:   je     0x000001a775de0d15
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de0cf9:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de0d03:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0d07:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de0d11:   call   *%r10
  0x000001a775de0d14:   hlt
  0x000001a775de0d15:   pop    %r10
  0x000001a775de0d17:   shl    $0x3,%rbx
  0x000001a775de0d1b:   mov    0x10(%rbx),%rbx
  0x000001a775de0d1f:   test   %rbx,%rbx
  0x000001a775de0d22:   je     0x000001a775de0d2b
  0x000001a775de0d28:   jmp    *0x50(%rbx)
  0x000001a775de0d2b:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
--------------------------------------------------------------------------------

Compiled method (n/a)     671    5     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LLLL)V (native)
 total in heap  [0x000001a775de0d90,0x000001a775de11b0] = 1056
 relocation     [0x000001a775de0f08,0x000001a775de0f98] = 144
 main code      [0x000001a775de0fa0,0x000001a775de11b0] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f927cf8} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)V' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/Object'
  # parm3:    rdi:rdi   = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de0fa0:   test   %rdi,%rdi
  0x000001a775de0fa3:   je     0x000001a775de0fe9
  0x000001a775de0fa9:   push   %rdi
  0x000001a775de0faa:   push   %r10
  0x000001a775de0fac:   mov    0x8(%rdi),%edi
  0x000001a775de0faf:   movabs $0x800000000,%r10
  0x000001a775de0fb9:   add    %r10,%rdi
  0x000001a775de0fbc:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de0fc6:   cmp    (%r10),%rdi
  0x000001a775de0fc9:   je     0x000001a775de1005
  0x000001a775de0fcf:   mov    0x40(%rdi),%rdi
  0x000001a775de0fd3:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de0fdd:   cmp    (%r10),%rdi
  0x000001a775de0fe0:   je     0x000001a775de1005
  0x000001a775de0fe6:   pop    %r10
  0x000001a775de0fe8:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de0fe9:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de0ff3:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de0ff7:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1001:   call   *%r10
  0x000001a775de1004:   hlt
 ;; L_ok:
  0x000001a775de1005:   pop    %r10
  0x000001a775de1007:   pop    %rdi
 ;; } verify_klass
  0x000001a775de1008:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de100b:   mov    0x8(%rdx),%r10d
  0x000001a775de100f:   movabs $0x800000000,%r11
  0x000001a775de1019:   add    %r11,%r10
  0x000001a775de101c:   mov    0x18(%rdi),%r11d
  0x000001a775de1020:   push   %r10
  0x000001a775de1022:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de102c:   cmp    (%r10),%r12
  0x000001a775de102f:   je     0x000001a775de1051
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de1035:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de103f:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1043:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de104d:   call   *%r10
  0x000001a775de1050:   hlt
  0x000001a775de1051:   pop    %r10
  0x000001a775de1053:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de1057:   test   %r11,%r11
  0x000001a775de105a:   je     0x000001a775de10a1
  0x000001a775de1060:   push   %rdi
  0x000001a775de1061:   push   %r10
  0x000001a775de1063:   mov    0x8(%r11),%edi
  0x000001a775de1067:   movabs $0x800000000,%r10
  0x000001a775de1071:   add    %r10,%rdi
  0x000001a775de1074:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de107e:   cmp    (%r10),%rdi
  0x000001a775de1081:   je     0x000001a775de10bd
  0x000001a775de1087:   mov    0x40(%rdi),%rdi
  0x000001a775de108b:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de1095:   cmp    (%r10),%rdi
  0x000001a775de1098:   je     0x000001a775de10bd
  0x000001a775de109e:   pop    %r10
  0x000001a775de10a0:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de10a1:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de10ab:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de10af:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de10b9:   call   *%r10
  0x000001a775de10bc:   hlt
 ;; L_ok:
  0x000001a775de10bd:   pop    %r10
  0x000001a775de10bf:   pop    %rdi
 ;; } verify_klass
  0x000001a775de10c0:   mov    0x10(%r11),%r11
  0x000001a775de10c4:   cmp    %r11,%r10
  0x000001a775de10c7:   je     0x000001a775de112e
  0x000001a775de10cd:   mov    0x1c(%r11),%eax
  0x000001a775de10d1:   cmp    (%r10,%rax,1),%r11
  0x000001a775de10d5:   je     0x000001a775de112e
  0x000001a775de10db:   cmp    $0x28,%eax
  0x000001a775de10de:   jne    0x000001a775de1112
  0x000001a775de10e4:   mov    %r11,%rax
  0x000001a775de10e7:   push   %rcx
  0x000001a775de10e8:   push   %rdi
  0x000001a775de10e9:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de10f3:   incl   (%rcx)
  0x000001a775de10f5:   mov    0x30(%r10),%rdi
  0x000001a775de10f9:   mov    (%rdi),%ecx
  0x000001a775de10fb:   add    $0x8,%rdi
  0x000001a775de10ff:   test   %rax,%rax
  0x000001a775de1102:   repnz scas %es:(%rdi),%rax
  0x000001a775de1105:   pop    %rdi
  0x000001a775de1106:   pop    %rcx
  0x000001a775de1107:   jne    0x000001a775de1112
  0x000001a775de1109:   mov    %r11,0x28(%r10)
  0x000001a775de110d:   jmp    0x000001a775de112e
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de1112:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de111c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1120:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de112a:   call   *%r10
  0x000001a775de112d:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de112e:   mov    0xc(%rdi),%eax
  0x000001a775de1131:   shr    $0x18,%eax
  0x000001a775de1134:   and    $0xf,%eax
  0x000001a775de1137:   cmp    $0x7,%eax
  0x000001a775de113a:   je     0x000001a775de115c
 ;; verify_ref_kind expected 7
  0x000001a775de1140:   movabs $0x1a70c1c8850,%rcx          ;   {external_word}
  0x000001a775de114a:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de114e:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1158:   call   *%r10
  0x000001a775de115b:   hlt
 ;; } verify_ref_kind
  0x000001a775de115c:   mov    0x24(%rdi),%ebx
  0x000001a775de115f:   push   %r10
  0x000001a775de1161:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de116b:   cmp    (%r10),%r12
  0x000001a775de116e:   je     0x000001a775de1190
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de1174:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de117e:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1182:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de118c:   call   *%r10
  0x000001a775de118f:   hlt
  0x000001a775de1190:   pop    %r10
  0x000001a775de1192:   shl    $0x3,%rbx
  0x000001a775de1196:   mov    0x10(%rbx),%rbx
  0x000001a775de119a:   test   %rbx,%rbx
  0x000001a775de119d:   je     0x000001a775de11a6
  0x000001a775de11a3:   jmp    *0x50(%rbx)
  0x000001a775de11a6:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de11ab:   hlt
  0x000001a775de11ac:   hlt
  0x000001a775de11ad:   hlt
  0x000001a775de11ae:   hlt
  0x000001a775de11af:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)     788    6     n 0       java.lang.invoke.MethodHandle::invokeBasic(LL)L (native)
 total in heap  [0x000001a775de1210,0x000001a775de14a8] = 664
 relocation     [0x000001a775de1388,0x000001a775de13d0] = 72
 main code      [0x000001a775de13e0,0x000001a775de14a8] = 200

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f929d70} 'invokeBasic' '(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # this:     rdx:rdx   = 'java/lang/invoke/MethodHandle'
  # parm0:    r8:r8     = 'java/lang/Object'
  # parm1:    r9:r9     = 'java/lang/Object'
  #           [sp+0x0]  (sp of caller)
 ;; jump_to_lambda_form {
  0x000001a775de13e0:   mov    0x14(%rdx),%ebx
  0x000001a775de13e3:   push   %r10
  0x000001a775de13e5:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de13ef:   cmp    (%r10),%r12
  0x000001a775de13f2:   je     0x000001a775de1414
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de13f8:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de1402:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1406:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1410:   call   *%r10
  0x000001a775de1413:   hlt
  0x000001a775de1414:   pop    %r10
  0x000001a775de1416:   shl    $0x3,%rbx
  0x000001a775de141a:   mov    0x28(%rbx),%ebx
  0x000001a775de141d:   push   %r10
  0x000001a775de141f:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de1429:   cmp    (%r10),%r12
  0x000001a775de142c:   je     0x000001a775de144e
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de1432:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de143c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1440:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de144a:   call   *%r10
  0x000001a775de144d:   hlt
  0x000001a775de144e:   pop    %r10
  0x000001a775de1450:   shl    $0x3,%rbx
  0x000001a775de1454:   mov    0x24(%rbx),%ebx
  0x000001a775de1457:   push   %r10
  0x000001a775de1459:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de1463:   cmp    (%r10),%r12
  0x000001a775de1466:   je     0x000001a775de1488
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de146c:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de1476:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de147a:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1484:   call   *%r10
  0x000001a775de1487:   hlt
  0x000001a775de1488:   pop    %r10
  0x000001a775de148a:   shl    $0x3,%rbx
  0x000001a775de148e:   mov    0x10(%rbx),%rbx
  0x000001a775de1492:   test   %rbx,%rbx
  0x000001a775de1495:   je     0x000001a775de149e
  0x000001a775de149b:   jmp    *0x50(%rbx)
  0x000001a775de149e:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
 ;; } jump_to_lambda_form
  0x000001a775de14a3:   hlt
  0x000001a775de14a4:   hlt
  0x000001a775de14a5:   hlt
  0x000001a775de14a6:   hlt
  0x000001a775de14a7:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)     844    7     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LLLL)L (native)
 total in heap  [0x000001a775de1510,0x000001a775de1930] = 1056
 relocation     [0x000001a775de1688,0x000001a775de1718] = 144
 main code      [0x000001a775de1720,0x000001a775de1930] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f92a0d8} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/Object'
  # parm3:    rdi:rdi   = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de1720:   test   %rdi,%rdi
  0x000001a775de1723:   je     0x000001a775de1769
  0x000001a775de1729:   push   %rdi
  0x000001a775de172a:   push   %r10
  0x000001a775de172c:   mov    0x8(%rdi),%edi
  0x000001a775de172f:   movabs $0x800000000,%r10
  0x000001a775de1739:   add    %r10,%rdi
  0x000001a775de173c:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de1746:   cmp    (%r10),%rdi
  0x000001a775de1749:   je     0x000001a775de1785
  0x000001a775de174f:   mov    0x40(%rdi),%rdi
  0x000001a775de1753:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de175d:   cmp    (%r10),%rdi
  0x000001a775de1760:   je     0x000001a775de1785
  0x000001a775de1766:   pop    %r10
  0x000001a775de1768:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de1769:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de1773:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1777:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1781:   call   *%r10
  0x000001a775de1784:   hlt
 ;; L_ok:
  0x000001a775de1785:   pop    %r10
  0x000001a775de1787:   pop    %rdi
 ;; } verify_klass
  0x000001a775de1788:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de178b:   mov    0x8(%rdx),%r10d
  0x000001a775de178f:   movabs $0x800000000,%r11
  0x000001a775de1799:   add    %r11,%r10
  0x000001a775de179c:   mov    0x18(%rdi),%r11d
  0x000001a775de17a0:   push   %r10
  0x000001a775de17a2:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de17ac:   cmp    (%r10),%r12
  0x000001a775de17af:   je     0x000001a775de17d1
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de17b5:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de17bf:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de17c3:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de17cd:   call   *%r10
  0x000001a775de17d0:   hlt
  0x000001a775de17d1:   pop    %r10
  0x000001a775de17d3:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de17d7:   test   %r11,%r11
  0x000001a775de17da:   je     0x000001a775de1821
  0x000001a775de17e0:   push   %rdi
  0x000001a775de17e1:   push   %r10
  0x000001a775de17e3:   mov    0x8(%r11),%edi
  0x000001a775de17e7:   movabs $0x800000000,%r10
  0x000001a775de17f1:   add    %r10,%rdi
  0x000001a775de17f4:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de17fe:   cmp    (%r10),%rdi
  0x000001a775de1801:   je     0x000001a775de183d
  0x000001a775de1807:   mov    0x40(%rdi),%rdi
  0x000001a775de180b:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de1815:   cmp    (%r10),%rdi
  0x000001a775de1818:   je     0x000001a775de183d
  0x000001a775de181e:   pop    %r10
  0x000001a775de1820:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de1821:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de182b:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de182f:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1839:   call   *%r10
  0x000001a775de183c:   hlt
 ;; L_ok:
  0x000001a775de183d:   pop    %r10
  0x000001a775de183f:   pop    %rdi
 ;; } verify_klass
  0x000001a775de1840:   mov    0x10(%r11),%r11
  0x000001a775de1844:   cmp    %r11,%r10
  0x000001a775de1847:   je     0x000001a775de18ae
  0x000001a775de184d:   mov    0x1c(%r11),%eax
  0x000001a775de1851:   cmp    (%r10,%rax,1),%r11
  0x000001a775de1855:   je     0x000001a775de18ae
  0x000001a775de185b:   cmp    $0x28,%eax
  0x000001a775de185e:   jne    0x000001a775de1892
  0x000001a775de1864:   mov    %r11,%rax
  0x000001a775de1867:   push   %rcx
  0x000001a775de1868:   push   %rdi
  0x000001a775de1869:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de1873:   incl   (%rcx)
  0x000001a775de1875:   mov    0x30(%r10),%rdi
  0x000001a775de1879:   mov    (%rdi),%ecx
  0x000001a775de187b:   add    $0x8,%rdi
  0x000001a775de187f:   test   %rax,%rax
  0x000001a775de1882:   repnz scas %es:(%rdi),%rax
  0x000001a775de1885:   pop    %rdi
  0x000001a775de1886:   pop    %rcx
  0x000001a775de1887:   jne    0x000001a775de1892
  0x000001a775de1889:   mov    %r11,0x28(%r10)
  0x000001a775de188d:   jmp    0x000001a775de18ae
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de1892:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de189c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de18a0:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de18aa:   call   *%r10
  0x000001a775de18ad:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de18ae:   mov    0xc(%rdi),%eax
  0x000001a775de18b1:   shr    $0x18,%eax
  0x000001a775de18b4:   and    $0xf,%eax
  0x000001a775de18b7:   cmp    $0x7,%eax
  0x000001a775de18ba:   je     0x000001a775de18dc
 ;; verify_ref_kind expected 7
  0x000001a775de18c0:   movabs $0x1a70c1c8f30,%rcx          ;   {external_word}
  0x000001a775de18ca:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de18ce:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de18d8:   call   *%r10
  0x000001a775de18db:   hlt
 ;; } verify_ref_kind
  0x000001a775de18dc:   mov    0x24(%rdi),%ebx
  0x000001a775de18df:   push   %r10
  0x000001a775de18e1:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de18eb:   cmp    (%r10),%r12
  0x000001a775de18ee:   je     0x000001a775de1910
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de18f4:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de18fe:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1902:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de190c:   call   *%r10
  0x000001a775de190f:   hlt
  0x000001a775de1910:   pop    %r10
  0x000001a775de1912:   shl    $0x3,%rbx
  0x000001a775de1916:   mov    0x10(%rbx),%rbx
  0x000001a775de191a:   test   %rbx,%rbx
  0x000001a775de191d:   je     0x000001a775de1926
  0x000001a775de1923:   jmp    *0x50(%rbx)
  0x000001a775de1926:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de192b:   hlt
  0x000001a775de192c:   hlt
  0x000001a775de192d:   hlt
  0x000001a775de192e:   hlt
  0x000001a775de192f:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)     981    8     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LLL)L (native)
 total in heap  [0x000001a775de1990,0x000001a775de1db0] = 1056
 relocation     [0x000001a775de1b08,0x000001a775de1b98] = 144
 main code      [0x000001a775de1ba0,0x000001a775de1db0] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f954600} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de1ba0:   test   %r9,%r9
  0x000001a775de1ba3:   je     0x000001a775de1bea
  0x000001a775de1ba9:   push   %rdi
  0x000001a775de1baa:   push   %r10
  0x000001a775de1bac:   mov    0x8(%r9),%edi
  0x000001a775de1bb0:   movabs $0x800000000,%r10
  0x000001a775de1bba:   add    %r10,%rdi
  0x000001a775de1bbd:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de1bc7:   cmp    (%r10),%rdi
  0x000001a775de1bca:   je     0x000001a775de1c06
  0x000001a775de1bd0:   mov    0x40(%rdi),%rdi
  0x000001a775de1bd4:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de1bde:   cmp    (%r10),%rdi
  0x000001a775de1be1:   je     0x000001a775de1c06
  0x000001a775de1be7:   pop    %r10
  0x000001a775de1be9:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de1bea:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de1bf4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1bf8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1c02:   call   *%r10
  0x000001a775de1c05:   hlt
 ;; L_ok:
  0x000001a775de1c06:   pop    %r10
  0x000001a775de1c08:   pop    %rdi
 ;; } verify_klass
  0x000001a775de1c09:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de1c0c:   mov    0x8(%rdx),%r10d
  0x000001a775de1c10:   movabs $0x800000000,%r11
  0x000001a775de1c1a:   add    %r11,%r10
  0x000001a775de1c1d:   mov    0x18(%r9),%r11d
  0x000001a775de1c21:   push   %r10
  0x000001a775de1c23:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de1c2d:   cmp    (%r10),%r12
  0x000001a775de1c30:   je     0x000001a775de1c52
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de1c36:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de1c40:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1c44:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1c4e:   call   *%r10
  0x000001a775de1c51:   hlt
  0x000001a775de1c52:   pop    %r10
  0x000001a775de1c54:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de1c58:   test   %r11,%r11
  0x000001a775de1c5b:   je     0x000001a775de1ca2
  0x000001a775de1c61:   push   %rdi
  0x000001a775de1c62:   push   %r10
  0x000001a775de1c64:   mov    0x8(%r11),%edi
  0x000001a775de1c68:   movabs $0x800000000,%r10
  0x000001a775de1c72:   add    %r10,%rdi
  0x000001a775de1c75:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de1c7f:   cmp    (%r10),%rdi
  0x000001a775de1c82:   je     0x000001a775de1cbe
  0x000001a775de1c88:   mov    0x40(%rdi),%rdi
  0x000001a775de1c8c:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de1c96:   cmp    (%r10),%rdi
  0x000001a775de1c99:   je     0x000001a775de1cbe
  0x000001a775de1c9f:   pop    %r10
  0x000001a775de1ca1:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de1ca2:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de1cac:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1cb0:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1cba:   call   *%r10
  0x000001a775de1cbd:   hlt
 ;; L_ok:
  0x000001a775de1cbe:   pop    %r10
  0x000001a775de1cc0:   pop    %rdi
 ;; } verify_klass
  0x000001a775de1cc1:   mov    0x10(%r11),%r11
  0x000001a775de1cc5:   cmp    %r11,%r10
  0x000001a775de1cc8:   je     0x000001a775de1d2f
  0x000001a775de1cce:   mov    0x1c(%r11),%eax
  0x000001a775de1cd2:   cmp    (%r10,%rax,1),%r11
  0x000001a775de1cd6:   je     0x000001a775de1d2f
  0x000001a775de1cdc:   cmp    $0x28,%eax
  0x000001a775de1cdf:   jne    0x000001a775de1d13
  0x000001a775de1ce5:   mov    %r11,%rax
  0x000001a775de1ce8:   push   %rcx
  0x000001a775de1ce9:   push   %rdi
  0x000001a775de1cea:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de1cf4:   incl   (%rcx)
  0x000001a775de1cf6:   mov    0x30(%r10),%rdi
  0x000001a775de1cfa:   mov    (%rdi),%ecx
  0x000001a775de1cfc:   add    $0x8,%rdi
  0x000001a775de1d00:   test   %rax,%rax
  0x000001a775de1d03:   repnz scas %es:(%rdi),%rax
  0x000001a775de1d06:   pop    %rdi
  0x000001a775de1d07:   pop    %rcx
  0x000001a775de1d08:   jne    0x000001a775de1d13
  0x000001a775de1d0a:   mov    %r11,0x28(%r10)
  0x000001a775de1d0e:   jmp    0x000001a775de1d2f
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de1d13:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de1d1d:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1d21:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1d2b:   call   *%r10
  0x000001a775de1d2e:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de1d2f:   mov    0xc(%r9),%eax
  0x000001a775de1d33:   shr    $0x18,%eax
  0x000001a775de1d36:   and    $0xf,%eax
  0x000001a775de1d39:   cmp    $0x7,%eax
  0x000001a775de1d3c:   je     0x000001a775de1d5e
 ;; verify_ref_kind expected 7
  0x000001a775de1d42:   movabs $0x1a70c1c8030,%rcx          ;   {external_word}
  0x000001a775de1d4c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1d50:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1d5a:   call   *%r10
  0x000001a775de1d5d:   hlt
 ;; } verify_ref_kind
  0x000001a775de1d5e:   mov    0x24(%r9),%ebx
  0x000001a775de1d62:   push   %r10
  0x000001a775de1d64:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de1d6e:   cmp    (%r10),%r12
  0x000001a775de1d71:   je     0x000001a775de1d93
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de1d77:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de1d81:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de1d85:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de1d8f:   call   *%r10
  0x000001a775de1d92:   hlt
  0x000001a775de1d93:   pop    %r10
  0x000001a775de1d95:   shl    $0x3,%rbx
  0x000001a775de1d99:   mov    0x10(%rbx),%rbx
  0x000001a775de1d9d:   test   %rbx,%rbx
  0x000001a775de1da0:   je     0x000001a775de1da9
  0x000001a775de1da6:   jmp    *0x50(%rbx)
  0x000001a775de1da9:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de1dae:   hlt
  0x000001a775de1daf:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    1115    9     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LLL)V (native)
 total in heap  [0x000001a775de1e10,0x000001a775de2230] = 1056
 relocation     [0x000001a775de1f88,0x000001a775de2018] = 144
 main code      [0x000001a775de2020,0x000001a775de2230] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f97eba8} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)V' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de2020:   test   %r9,%r9
  0x000001a775de2023:   je     0x000001a775de206a
  0x000001a775de2029:   push   %rdi
  0x000001a775de202a:   push   %r10
  0x000001a775de202c:   mov    0x8(%r9),%edi
  0x000001a775de2030:   movabs $0x800000000,%r10
  0x000001a775de203a:   add    %r10,%rdi
  0x000001a775de203d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de2047:   cmp    (%r10),%rdi
  0x000001a775de204a:   je     0x000001a775de2086
  0x000001a775de2050:   mov    0x40(%rdi),%rdi
  0x000001a775de2054:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de205e:   cmp    (%r10),%rdi
  0x000001a775de2061:   je     0x000001a775de2086
  0x000001a775de2067:   pop    %r10
  0x000001a775de2069:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de206a:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de2074:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2078:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2082:   call   *%r10
  0x000001a775de2085:   hlt
 ;; L_ok:
  0x000001a775de2086:   pop    %r10
  0x000001a775de2088:   pop    %rdi
 ;; } verify_klass
  0x000001a775de2089:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de208c:   mov    0x8(%rdx),%r10d
  0x000001a775de2090:   movabs $0x800000000,%r11
  0x000001a775de209a:   add    %r11,%r10
  0x000001a775de209d:   mov    0x18(%r9),%r11d
  0x000001a775de20a1:   push   %r10
  0x000001a775de20a3:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de20ad:   cmp    (%r10),%r12
  0x000001a775de20b0:   je     0x000001a775de20d2
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de20b6:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de20c0:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de20c4:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de20ce:   call   *%r10
  0x000001a775de20d1:   hlt
  0x000001a775de20d2:   pop    %r10
  0x000001a775de20d4:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de20d8:   test   %r11,%r11
  0x000001a775de20db:   je     0x000001a775de2122
  0x000001a775de20e1:   push   %rdi
  0x000001a775de20e2:   push   %r10
  0x000001a775de20e4:   mov    0x8(%r11),%edi
  0x000001a775de20e8:   movabs $0x800000000,%r10
  0x000001a775de20f2:   add    %r10,%rdi
  0x000001a775de20f5:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de20ff:   cmp    (%r10),%rdi
  0x000001a775de2102:   je     0x000001a775de213e
  0x000001a775de2108:   mov    0x40(%rdi),%rdi
  0x000001a775de210c:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de2116:   cmp    (%r10),%rdi
  0x000001a775de2119:   je     0x000001a775de213e
  0x000001a775de211f:   pop    %r10
  0x000001a775de2121:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de2122:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de212c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2130:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de213a:   call   *%r10
  0x000001a775de213d:   hlt
 ;; L_ok:
  0x000001a775de213e:   pop    %r10
  0x000001a775de2140:   pop    %rdi
 ;; } verify_klass
  0x000001a775de2141:   mov    0x10(%r11),%r11
  0x000001a775de2145:   cmp    %r11,%r10
  0x000001a775de2148:   je     0x000001a775de21af
  0x000001a775de214e:   mov    0x1c(%r11),%eax
  0x000001a775de2152:   cmp    (%r10,%rax,1),%r11
  0x000001a775de2156:   je     0x000001a775de21af
  0x000001a775de215c:   cmp    $0x28,%eax
  0x000001a775de215f:   jne    0x000001a775de2193
  0x000001a775de2165:   mov    %r11,%rax
  0x000001a775de2168:   push   %rcx
  0x000001a775de2169:   push   %rdi
  0x000001a775de216a:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de2174:   incl   (%rcx)
  0x000001a775de2176:   mov    0x30(%r10),%rdi
  0x000001a775de217a:   mov    (%rdi),%ecx
  0x000001a775de217c:   add    $0x8,%rdi
  0x000001a775de2180:   test   %rax,%rax
  0x000001a775de2183:   repnz scas %es:(%rdi),%rax
  0x000001a775de2186:   pop    %rdi
  0x000001a775de2187:   pop    %rcx
  0x000001a775de2188:   jne    0x000001a775de2193
  0x000001a775de218a:   mov    %r11,0x28(%r10)
  0x000001a775de218e:   jmp    0x000001a775de21af
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de2193:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de219d:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de21a1:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de21ab:   call   *%r10
  0x000001a775de21ae:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de21af:   mov    0xc(%r9),%eax
  0x000001a775de21b3:   shr    $0x18,%eax
  0x000001a775de21b6:   and    $0xf,%eax
  0x000001a775de21b9:   cmp    $0x7,%eax
  0x000001a775de21bc:   je     0x000001a775de21de
 ;; verify_ref_kind expected 7
  0x000001a775de21c2:   movabs $0x1a70c1c8b70,%rcx          ;   {external_word}
  0x000001a775de21cc:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de21d0:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de21da:   call   *%r10
  0x000001a775de21dd:   hlt
 ;; } verify_ref_kind
  0x000001a775de21de:   mov    0x24(%r9),%ebx
  0x000001a775de21e2:   push   %r10
  0x000001a775de21e4:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de21ee:   cmp    (%r10),%r12
  0x000001a775de21f1:   je     0x000001a775de2213
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de21f7:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de2201:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2205:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de220f:   call   *%r10
  0x000001a775de2212:   hlt
  0x000001a775de2213:   pop    %r10
  0x000001a775de2215:   shl    $0x3,%rbx
  0x000001a775de2219:   mov    0x10(%rbx),%rbx
  0x000001a775de221d:   test   %rbx,%rbx
  0x000001a775de2220:   je     0x000001a775de2229
  0x000001a775de2226:   jmp    *0x50(%rbx)
  0x000001a775de2229:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de222e:   hlt
  0x000001a775de222f:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    1254   10     n 0       java.lang.invoke.MethodHandle::invokeBasic(L)L (native)
 total in heap  [0x000001a775de2290,0x000001a775de2528] = 664
 relocation     [0x000001a775de2408,0x000001a775de2450] = 72
 main code      [0x000001a775de2460,0x000001a775de2528] = 200

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f97fe40} 'invokeBasic' '(Ljava/lang/Object;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # this:     rdx:rdx   = 'java/lang/invoke/MethodHandle'
  # parm0:    r8:r8     = 'java/lang/Object'
  #           [sp+0x0]  (sp of caller)
 ;; jump_to_lambda_form {
  0x000001a775de2460:   mov    0x14(%rdx),%ebx
  0x000001a775de2463:   push   %r10
  0x000001a775de2465:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de246f:   cmp    (%r10),%r12
  0x000001a775de2472:   je     0x000001a775de2494
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de2478:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de2482:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2486:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2490:   call   *%r10
  0x000001a775de2493:   hlt
  0x000001a775de2494:   pop    %r10
  0x000001a775de2496:   shl    $0x3,%rbx
  0x000001a775de249a:   mov    0x28(%rbx),%ebx
  0x000001a775de249d:   push   %r10
  0x000001a775de249f:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de24a9:   cmp    (%r10),%r12
  0x000001a775de24ac:   je     0x000001a775de24ce
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de24b2:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de24bc:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de24c0:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de24ca:   call   *%r10
  0x000001a775de24cd:   hlt
  0x000001a775de24ce:   pop    %r10
  0x000001a775de24d0:   shl    $0x3,%rbx
  0x000001a775de24d4:   mov    0x24(%rbx),%ebx
  0x000001a775de24d7:   push   %r10
  0x000001a775de24d9:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de24e3:   cmp    (%r10),%r12
  0x000001a775de24e6:   je     0x000001a775de2508
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de24ec:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de24f6:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de24fa:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2504:   call   *%r10
  0x000001a775de2507:   hlt
  0x000001a775de2508:   pop    %r10
  0x000001a775de250a:   shl    $0x3,%rbx
  0x000001a775de250e:   mov    0x10(%rbx),%rbx
  0x000001a775de2512:   test   %rbx,%rbx
  0x000001a775de2515:   je     0x000001a775de251e
  0x000001a775de251b:   jmp    *0x50(%rbx)
  0x000001a775de251e:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
 ;; } jump_to_lambda_form
  0x000001a775de2523:   hlt
  0x000001a775de2524:   hlt
  0x000001a775de2525:   hlt
  0x000001a775de2526:   hlt
  0x000001a775de2527:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    1313   11     n 0       java.lang.invoke.MethodHandle::linkToStatic(LL)L (native)
 total in heap  [0x000001a775de2590,0x000001a775de2848] = 696
 relocation     [0x000001a775de2708,0x000001a775de2750] = 72
 main code      [0x000001a775de2760,0x000001a775de2848] = 232

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f9882b0} 'linkToStatic' '(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de2760:   test   %r8,%r8
  0x000001a775de2763:   je     0x000001a775de27aa
  0x000001a775de2769:   push   %rdi
  0x000001a775de276a:   push   %r10
  0x000001a775de276c:   mov    0x8(%r8),%edi
  0x000001a775de2770:   movabs $0x800000000,%r10
  0x000001a775de277a:   add    %r10,%rdi
  0x000001a775de277d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de2787:   cmp    (%r10),%rdi
  0x000001a775de278a:   je     0x000001a775de27c6
  0x000001a775de2790:   mov    0x40(%rdi),%rdi
  0x000001a775de2794:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de279e:   cmp    (%r10),%rdi
  0x000001a775de27a1:   je     0x000001a775de27c6
  0x000001a775de27a7:   pop    %r10
  0x000001a775de27a9:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de27aa:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de27b4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de27b8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de27c2:   call   *%r10
  0x000001a775de27c5:   hlt
 ;; L_ok:
  0x000001a775de27c6:   pop    %r10
  0x000001a775de27c8:   pop    %rdi
 ;; } verify_klass
 ;; verify_ref_kind {
  0x000001a775de27c9:   mov    0xc(%r8),%eax
  0x000001a775de27cd:   shr    $0x18,%eax
  0x000001a775de27d0:   and    $0xf,%eax
  0x000001a775de27d3:   cmp    $0x6,%eax
  0x000001a775de27d6:   je     0x000001a775de27f8
 ;; verify_ref_kind expected 6
  0x000001a775de27dc:   movabs $0x1a70c1c8210,%rcx          ;   {external_word}
  0x000001a775de27e6:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de27ea:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de27f4:   call   *%r10
  0x000001a775de27f7:   hlt
 ;; } verify_ref_kind
  0x000001a775de27f8:   mov    0x24(%r8),%ebx
  0x000001a775de27fc:   push   %r10
  0x000001a775de27fe:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de2808:   cmp    (%r10),%r12
  0x000001a775de280b:   je     0x000001a775de282d
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de2811:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de281b:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de281f:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2829:   call   *%r10
  0x000001a775de282c:   hlt
  0x000001a775de282d:   pop    %r10
  0x000001a775de282f:   shl    $0x3,%rbx
  0x000001a775de2833:   mov    0x10(%rbx),%rbx
  0x000001a775de2837:   test   %rbx,%rbx
  0x000001a775de283a:   je     0x000001a775de2843
  0x000001a775de2840:   jmp    *0x50(%rbx)
  0x000001a775de2843:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
--------------------------------------------------------------------------------

Compiled method (n/a)    1394   12     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LLLL)I (native)
 total in heap  [0x000001a775de2890,0x000001a775de2cb0] = 1056
 relocation     [0x000001a775de2a08,0x000001a775de2a98] = 144
 main code      [0x000001a775de2aa0,0x000001a775de2cb0] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f998e48} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)I' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/Object'
  # parm3:    rdi:rdi   = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de2aa0:   test   %rdi,%rdi
  0x000001a775de2aa3:   je     0x000001a775de2ae9
  0x000001a775de2aa9:   push   %rdi
  0x000001a775de2aaa:   push   %r10
  0x000001a775de2aac:   mov    0x8(%rdi),%edi
  0x000001a775de2aaf:   movabs $0x800000000,%r10
  0x000001a775de2ab9:   add    %r10,%rdi
  0x000001a775de2abc:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de2ac6:   cmp    (%r10),%rdi
  0x000001a775de2ac9:   je     0x000001a775de2b05
  0x000001a775de2acf:   mov    0x40(%rdi),%rdi
  0x000001a775de2ad3:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de2add:   cmp    (%r10),%rdi
  0x000001a775de2ae0:   je     0x000001a775de2b05
  0x000001a775de2ae6:   pop    %r10
  0x000001a775de2ae8:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de2ae9:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de2af3:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2af7:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2b01:   call   *%r10
  0x000001a775de2b04:   hlt
 ;; L_ok:
  0x000001a775de2b05:   pop    %r10
  0x000001a775de2b07:   pop    %rdi
 ;; } verify_klass
  0x000001a775de2b08:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de2b0b:   mov    0x8(%rdx),%r10d
  0x000001a775de2b0f:   movabs $0x800000000,%r11
  0x000001a775de2b19:   add    %r11,%r10
  0x000001a775de2b1c:   mov    0x18(%rdi),%r11d
  0x000001a775de2b20:   push   %r10
  0x000001a775de2b22:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de2b2c:   cmp    (%r10),%r12
  0x000001a775de2b2f:   je     0x000001a775de2b51
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de2b35:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de2b3f:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2b43:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2b4d:   call   *%r10
  0x000001a775de2b50:   hlt
  0x000001a775de2b51:   pop    %r10
  0x000001a775de2b53:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de2b57:   test   %r11,%r11
  0x000001a775de2b5a:   je     0x000001a775de2ba1
  0x000001a775de2b60:   push   %rdi
  0x000001a775de2b61:   push   %r10
  0x000001a775de2b63:   mov    0x8(%r11),%edi
  0x000001a775de2b67:   movabs $0x800000000,%r10
  0x000001a775de2b71:   add    %r10,%rdi
  0x000001a775de2b74:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de2b7e:   cmp    (%r10),%rdi
  0x000001a775de2b81:   je     0x000001a775de2bbd
  0x000001a775de2b87:   mov    0x40(%rdi),%rdi
  0x000001a775de2b8b:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de2b95:   cmp    (%r10),%rdi
  0x000001a775de2b98:   je     0x000001a775de2bbd
  0x000001a775de2b9e:   pop    %r10
  0x000001a775de2ba0:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de2ba1:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de2bab:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2baf:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2bb9:   call   *%r10
  0x000001a775de2bbc:   hlt
 ;; L_ok:
  0x000001a775de2bbd:   pop    %r10
  0x000001a775de2bbf:   pop    %rdi
 ;; } verify_klass
  0x000001a775de2bc0:   mov    0x10(%r11),%r11
  0x000001a775de2bc4:   cmp    %r11,%r10
  0x000001a775de2bc7:   je     0x000001a775de2c2e
  0x000001a775de2bcd:   mov    0x1c(%r11),%eax
  0x000001a775de2bd1:   cmp    (%r10,%rax,1),%r11
  0x000001a775de2bd5:   je     0x000001a775de2c2e
  0x000001a775de2bdb:   cmp    $0x28,%eax
  0x000001a775de2bde:   jne    0x000001a775de2c12
  0x000001a775de2be4:   mov    %r11,%rax
  0x000001a775de2be7:   push   %rcx
  0x000001a775de2be8:   push   %rdi
  0x000001a775de2be9:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de2bf3:   incl   (%rcx)
  0x000001a775de2bf5:   mov    0x30(%r10),%rdi
  0x000001a775de2bf9:   mov    (%rdi),%ecx
  0x000001a775de2bfb:   add    $0x8,%rdi
  0x000001a775de2bff:   test   %rax,%rax
  0x000001a775de2c02:   repnz scas %es:(%rdi),%rax
  0x000001a775de2c05:   pop    %rdi
  0x000001a775de2c06:   pop    %rcx
  0x000001a775de2c07:   jne    0x000001a775de2c12
  0x000001a775de2c09:   mov    %r11,0x28(%r10)
  0x000001a775de2c0d:   jmp    0x000001a775de2c2e
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de2c12:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de2c1c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2c20:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2c2a:   call   *%r10
  0x000001a775de2c2d:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de2c2e:   mov    0xc(%rdi),%eax
  0x000001a775de2c31:   shr    $0x18,%eax
  0x000001a775de2c34:   and    $0xf,%eax
  0x000001a775de2c37:   cmp    $0x7,%eax
  0x000001a775de2c3a:   je     0x000001a775de2c5c
 ;; verify_ref_kind expected 7
  0x000001a775de2c40:   movabs $0x1a70c1c9bb0,%rcx          ;   {external_word}
  0x000001a775de2c4a:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2c4e:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2c58:   call   *%r10
  0x000001a775de2c5b:   hlt
 ;; } verify_ref_kind
  0x000001a775de2c5c:   mov    0x24(%rdi),%ebx
  0x000001a775de2c5f:   push   %r10
  0x000001a775de2c61:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de2c6b:   cmp    (%r10),%r12
  0x000001a775de2c6e:   je     0x000001a775de2c90
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de2c74:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de2c7e:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2c82:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2c8c:   call   *%r10
  0x000001a775de2c8f:   hlt
  0x000001a775de2c90:   pop    %r10
  0x000001a775de2c92:   shl    $0x3,%rbx
  0x000001a775de2c96:   mov    0x10(%rbx),%rbx
  0x000001a775de2c9a:   test   %rbx,%rbx
  0x000001a775de2c9d:   je     0x000001a775de2ca6
  0x000001a775de2ca3:   jmp    *0x50(%rbx)
  0x000001a775de2ca6:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de2cab:   hlt
  0x000001a775de2cac:   hlt
  0x000001a775de2cad:   hlt
  0x000001a775de2cae:   hlt
  0x000001a775de2caf:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    1529   13     n 0       java.lang.invoke.MethodHandle::linkToVirtual(LL)V (native)
 total in heap  [0x000001a775de2d10,0x000001a775de3120] = 1040
 relocation     [0x000001a775de2e88,0x000001a775de2f10] = 136
 main code      [0x000001a775de2f20,0x000001a775de3120] = 512

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f9cef10} 'linkToVirtual' '(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)V' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de2f20:   test   %r8,%r8
  0x000001a775de2f23:   je     0x000001a775de2f6a
  0x000001a775de2f29:   push   %rdi
  0x000001a775de2f2a:   push   %r10
  0x000001a775de2f2c:   mov    0x8(%r8),%edi
  0x000001a775de2f30:   movabs $0x800000000,%r10
  0x000001a775de2f3a:   add    %r10,%rdi
  0x000001a775de2f3d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de2f47:   cmp    (%r10),%rdi
  0x000001a775de2f4a:   je     0x000001a775de2f86
  0x000001a775de2f50:   mov    0x40(%rdi),%rdi
  0x000001a775de2f54:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de2f5e:   cmp    (%r10),%rdi
  0x000001a775de2f61:   je     0x000001a775de2f86
  0x000001a775de2f67:   pop    %r10
  0x000001a775de2f69:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de2f6a:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de2f74:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2f78:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2f82:   call   *%r10
  0x000001a775de2f85:   hlt
 ;; L_ok:
  0x000001a775de2f86:   pop    %r10
  0x000001a775de2f88:   pop    %rdi
 ;; } verify_klass
  0x000001a775de2f89:   mov    0x8(%rdx),%r10d
  0x000001a775de2f8d:   movabs $0x800000000,%r11
  0x000001a775de2f97:   add    %r11,%r10
 ;; check_receiver {
  0x000001a775de2f9a:   mov    0x18(%r8),%r11d
  0x000001a775de2f9e:   push   %r10
  0x000001a775de2fa0:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de2faa:   cmp    (%r10),%r12
  0x000001a775de2fad:   je     0x000001a775de2fcf
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de2fb3:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de2fbd:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de2fc1:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de2fcb:   call   *%r10
  0x000001a775de2fce:   hlt
  0x000001a775de2fcf:   pop    %r10
  0x000001a775de2fd1:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de2fd5:   test   %r11,%r11
  0x000001a775de2fd8:   je     0x000001a775de301f
  0x000001a775de2fde:   push   %rdi
  0x000001a775de2fdf:   push   %r10
  0x000001a775de2fe1:   mov    0x8(%r11),%edi
  0x000001a775de2fe5:   movabs $0x800000000,%r10
  0x000001a775de2fef:   add    %r10,%rdi
  0x000001a775de2ff2:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de2ffc:   cmp    (%r10),%rdi
  0x000001a775de2fff:   je     0x000001a775de303b
  0x000001a775de3005:   mov    0x40(%rdi),%rdi
  0x000001a775de3009:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de3013:   cmp    (%r10),%rdi
  0x000001a775de3016:   je     0x000001a775de303b
  0x000001a775de301c:   pop    %r10
  0x000001a775de301e:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de301f:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de3029:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de302d:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3037:   call   *%r10
  0x000001a775de303a:   hlt
 ;; L_ok:
  0x000001a775de303b:   pop    %r10
  0x000001a775de303d:   pop    %rdi
 ;; } verify_klass
  0x000001a775de303e:   mov    0x10(%r11),%r11
  0x000001a775de3042:   cmp    %r11,%r10
  0x000001a775de3045:   je     0x000001a775de30ac
  0x000001a775de304b:   mov    0x1c(%r11),%eax
  0x000001a775de304f:   cmp    (%r10,%rax,1),%r11
  0x000001a775de3053:   je     0x000001a775de30ac
  0x000001a775de3059:   cmp    $0x28,%eax
  0x000001a775de305c:   jne    0x000001a775de3090
  0x000001a775de3062:   mov    %r11,%rax
  0x000001a775de3065:   push   %rcx
  0x000001a775de3066:   push   %rdi
  0x000001a775de3067:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de3071:   incl   (%rcx)
  0x000001a775de3073:   mov    0x30(%r10),%rdi
  0x000001a775de3077:   mov    (%rdi),%ecx
  0x000001a775de3079:   add    $0x8,%rdi
  0x000001a775de307d:   test   %rax,%rax
  0x000001a775de3080:   repnz scas %es:(%rdi),%rax
  0x000001a775de3083:   pop    %rdi
  0x000001a775de3084:   pop    %rcx
  0x000001a775de3085:   jne    0x000001a775de3090
  0x000001a775de3087:   mov    %r11,0x28(%r10)
  0x000001a775de308b:   jmp    0x000001a775de30ac
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de3090:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de309a:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de309e:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de30a8:   call   *%r10
  0x000001a775de30ab:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de30ac:   mov    0xc(%r8),%eax
  0x000001a775de30b0:   shr    $0x18,%eax
  0x000001a775de30b3:   and    $0xf,%eax
  0x000001a775de30b6:   cmp    $0x5,%eax
  0x000001a775de30b9:   je     0x000001a775de30db
 ;; verify_ref_kind expected 5
  0x000001a775de30bf:   movabs $0x1a70c1c9c50,%rcx          ;   {external_word}
  0x000001a775de30c9:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de30cd:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de30d7:   call   *%r10
  0x000001a775de30da:   hlt
 ;; } verify_ref_kind
  0x000001a775de30db:   mov    0x10(%r8),%r11
  0x000001a775de30df:   cmp    $0x0,%r11d
  0x000001a775de30e3:   jge    0x000001a775de3105
 ;; no virtual index
  0x000001a775de30e9:   movabs $0x7ff80bee8e40,%rcx         ;   {external_word}
  0x000001a775de30f3:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de30f7:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3101:   call   *%r10
  0x000001a775de3104:   hlt
 ;; L_index_ok:
  0x000001a775de3105:   mov    0x1d8(%r10,%r11,8),%rbx
  0x000001a775de310d:   test   %rbx,%rbx
  0x000001a775de3110:   je     0x000001a775de3119
  0x000001a775de3116:   jmp    *0x50(%rbx)
  0x000001a775de3119:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de311e:   hlt
  0x000001a775de311f:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    1642   14     n 0       java.lang.invoke.MethodHandle::linkToStatic(LLL)I (native)
 total in heap  [0x000001a775de3190,0x000001a775de3448] = 696
 relocation     [0x000001a775de3308,0x000001a775de3350] = 72
 main code      [0x000001a775de3360,0x000001a775de3448] = 232

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f9d0ab8} 'linkToStatic' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)I' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de3360:   test   %r9,%r9
  0x000001a775de3363:   je     0x000001a775de33aa
  0x000001a775de3369:   push   %rdi
  0x000001a775de336a:   push   %r10
  0x000001a775de336c:   mov    0x8(%r9),%edi
  0x000001a775de3370:   movabs $0x800000000,%r10
  0x000001a775de337a:   add    %r10,%rdi
  0x000001a775de337d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de3387:   cmp    (%r10),%rdi
  0x000001a775de338a:   je     0x000001a775de33c6
  0x000001a775de3390:   mov    0x40(%rdi),%rdi
  0x000001a775de3394:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de339e:   cmp    (%r10),%rdi
  0x000001a775de33a1:   je     0x000001a775de33c6
  0x000001a775de33a7:   pop    %r10
  0x000001a775de33a9:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de33aa:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de33b4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de33b8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de33c2:   call   *%r10
  0x000001a775de33c5:   hlt
 ;; L_ok:
  0x000001a775de33c6:   pop    %r10
  0x000001a775de33c8:   pop    %rdi
 ;; } verify_klass
 ;; verify_ref_kind {
  0x000001a775de33c9:   mov    0xc(%r9),%eax
  0x000001a775de33cd:   shr    $0x18,%eax
  0x000001a775de33d0:   and    $0xf,%eax
  0x000001a775de33d3:   cmp    $0x6,%eax
  0x000001a775de33d6:   je     0x000001a775de33f8
 ;; verify_ref_kind expected 6
  0x000001a775de33dc:   movabs $0x1a70c1c8530,%rcx          ;   {external_word}
  0x000001a775de33e6:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de33ea:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de33f4:   call   *%r10
  0x000001a775de33f7:   hlt
 ;; } verify_ref_kind
  0x000001a775de33f8:   mov    0x24(%r9),%ebx
  0x000001a775de33fc:   push   %r10
  0x000001a775de33fe:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de3408:   cmp    (%r10),%r12
  0x000001a775de340b:   je     0x000001a775de342d
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de3411:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de341b:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de341f:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3429:   call   *%r10
  0x000001a775de342c:   hlt
  0x000001a775de342d:   pop    %r10
  0x000001a775de342f:   shl    $0x3,%rbx
  0x000001a775de3433:   mov    0x10(%rbx),%rbx
  0x000001a775de3437:   test   %rbx,%rbx
  0x000001a775de343a:   je     0x000001a775de3443
  0x000001a775de3440:   jmp    *0x50(%rbx)
  0x000001a775de3443:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
--------------------------------------------------------------------------------

Compiled method (n/a)    1713   15     n 0       java.lang.invoke.MethodHandle::linkToStatic(LLLL)L (native)
 total in heap  [0x000001a775de3490,0x000001a775de3748] = 696
 relocation     [0x000001a775de3608,0x000001a775de3650] = 72
 main code      [0x000001a775de3660,0x000001a775de3748] = 232

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f9e9380} 'linkToStatic' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/Object'
  # parm3:    rdi:rdi   = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de3660:   test   %rdi,%rdi
  0x000001a775de3663:   je     0x000001a775de36a9
  0x000001a775de3669:   push   %rdi
  0x000001a775de366a:   push   %r10
  0x000001a775de366c:   mov    0x8(%rdi),%edi
  0x000001a775de366f:   movabs $0x800000000,%r10
  0x000001a775de3679:   add    %r10,%rdi
  0x000001a775de367c:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de3686:   cmp    (%r10),%rdi
  0x000001a775de3689:   je     0x000001a775de36c5
  0x000001a775de368f:   mov    0x40(%rdi),%rdi
  0x000001a775de3693:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de369d:   cmp    (%r10),%rdi
  0x000001a775de36a0:   je     0x000001a775de36c5
  0x000001a775de36a6:   pop    %r10
  0x000001a775de36a8:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de36a9:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de36b3:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de36b7:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de36c1:   call   *%r10
  0x000001a775de36c4:   hlt
 ;; L_ok:
  0x000001a775de36c5:   pop    %r10
  0x000001a775de36c7:   pop    %rdi
 ;; } verify_klass
 ;; verify_ref_kind {
  0x000001a775de36c8:   mov    0xc(%rdi),%eax
  0x000001a775de36cb:   shr    $0x18,%eax
  0x000001a775de36ce:   and    $0xf,%eax
  0x000001a775de36d1:   cmp    $0x6,%eax
  0x000001a775de36d4:   je     0x000001a775de36f6
 ;; verify_ref_kind expected 6
  0x000001a775de36da:   movabs $0x1a70c1c8670,%rcx          ;   {external_word}
  0x000001a775de36e4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de36e8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de36f2:   call   *%r10
  0x000001a775de36f5:   hlt
 ;; } verify_ref_kind
  0x000001a775de36f6:   mov    0x24(%rdi),%ebx
  0x000001a775de36f9:   push   %r10
  0x000001a775de36fb:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de3705:   cmp    (%r10),%r12
  0x000001a775de3708:   je     0x000001a775de372a
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de370e:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de3718:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de371c:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3726:   call   *%r10
  0x000001a775de3729:   hlt
  0x000001a775de372a:   pop    %r10
  0x000001a775de372c:   shl    $0x3,%rbx
  0x000001a775de3730:   mov    0x10(%rbx),%rbx
  0x000001a775de3734:   test   %rbx,%rbx
  0x000001a775de3737:   je     0x000001a775de3740
  0x000001a775de373d:   jmp    *0x50(%rbx)
  0x000001a775de3740:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de3745:   hlt
  0x000001a775de3746:   hlt
  0x000001a775de3747:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    1790   16     n 0       java.lang.invoke.MethodHandle::invokeBasic()L (native)
 total in heap  [0x000001a775de3790,0x000001a775de3a28] = 664
 relocation     [0x000001a775de3908,0x000001a775de3950] = 72
 main code      [0x000001a775de3960,0x000001a775de3a28] = 200

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f8804c8} 'invokeBasic' '()Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  #           [sp+0x0]  (sp of caller)
 ;; jump_to_lambda_form {
  0x000001a775de3960:   mov    0x14(%rdx),%ebx
  0x000001a775de3963:   push   %r10
  0x000001a775de3965:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de396f:   cmp    (%r10),%r12
  0x000001a775de3972:   je     0x000001a775de3994
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de3978:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de3982:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de3986:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3990:   call   *%r10
  0x000001a775de3993:   hlt
  0x000001a775de3994:   pop    %r10
  0x000001a775de3996:   shl    $0x3,%rbx
  0x000001a775de399a:   mov    0x28(%rbx),%ebx
  0x000001a775de399d:   push   %r10
  0x000001a775de399f:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de39a9:   cmp    (%r10),%r12
  0x000001a775de39ac:   je     0x000001a775de39ce
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de39b2:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de39bc:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de39c0:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de39ca:   call   *%r10
  0x000001a775de39cd:   hlt
  0x000001a775de39ce:   pop    %r10
  0x000001a775de39d0:   shl    $0x3,%rbx
  0x000001a775de39d4:   mov    0x24(%rbx),%ebx
  0x000001a775de39d7:   push   %r10
  0x000001a775de39d9:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de39e3:   cmp    (%r10),%r12
  0x000001a775de39e6:   je     0x000001a775de3a08
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de39ec:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de39f6:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de39fa:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3a04:   call   *%r10
  0x000001a775de3a07:   hlt
  0x000001a775de3a08:   pop    %r10
  0x000001a775de3a0a:   shl    $0x3,%rbx
  0x000001a775de3a0e:   mov    0x10(%rbx),%rbx
  0x000001a775de3a12:   test   %rbx,%rbx
  0x000001a775de3a15:   je     0x000001a775de3a1e
  0x000001a775de3a1b:   jmp    *0x50(%rbx)
  0x000001a775de3a1e:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
 ;; } jump_to_lambda_form
  0x000001a775de3a23:   hlt
  0x000001a775de3a24:   hlt
  0x000001a775de3a25:   hlt
  0x000001a775de3a26:   hlt
  0x000001a775de3a27:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    1840   17     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LL)L (native)
 total in heap  [0x000001a775de3a90,0x000001a775de3eb0] = 1056
 relocation     [0x000001a775de3c08,0x000001a775de3c98] = 144
 main code      [0x000001a775de3ca0,0x000001a775de3eb0] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f8805e0} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de3ca0:   test   %r8,%r8
  0x000001a775de3ca3:   je     0x000001a775de3cea
  0x000001a775de3ca9:   push   %rdi
  0x000001a775de3caa:   push   %r10
  0x000001a775de3cac:   mov    0x8(%r8),%edi
  0x000001a775de3cb0:   movabs $0x800000000,%r10
  0x000001a775de3cba:   add    %r10,%rdi
  0x000001a775de3cbd:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de3cc7:   cmp    (%r10),%rdi
  0x000001a775de3cca:   je     0x000001a775de3d06
  0x000001a775de3cd0:   mov    0x40(%rdi),%rdi
  0x000001a775de3cd4:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de3cde:   cmp    (%r10),%rdi
  0x000001a775de3ce1:   je     0x000001a775de3d06
  0x000001a775de3ce7:   pop    %r10
  0x000001a775de3ce9:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de3cea:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de3cf4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de3cf8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3d02:   call   *%r10
  0x000001a775de3d05:   hlt
 ;; L_ok:
  0x000001a775de3d06:   pop    %r10
  0x000001a775de3d08:   pop    %rdi
 ;; } verify_klass
  0x000001a775de3d09:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de3d0c:   mov    0x8(%rdx),%r10d
  0x000001a775de3d10:   movabs $0x800000000,%r11
  0x000001a775de3d1a:   add    %r11,%r10
  0x000001a775de3d1d:   mov    0x18(%r8),%r11d
  0x000001a775de3d21:   push   %r10
  0x000001a775de3d23:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de3d2d:   cmp    (%r10),%r12
  0x000001a775de3d30:   je     0x000001a775de3d52
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de3d36:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de3d40:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de3d44:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3d4e:   call   *%r10
  0x000001a775de3d51:   hlt
  0x000001a775de3d52:   pop    %r10
  0x000001a775de3d54:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de3d58:   test   %r11,%r11
  0x000001a775de3d5b:   je     0x000001a775de3da2
  0x000001a775de3d61:   push   %rdi
  0x000001a775de3d62:   push   %r10
  0x000001a775de3d64:   mov    0x8(%r11),%edi
  0x000001a775de3d68:   movabs $0x800000000,%r10
  0x000001a775de3d72:   add    %r10,%rdi
  0x000001a775de3d75:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de3d7f:   cmp    (%r10),%rdi
  0x000001a775de3d82:   je     0x000001a775de3dbe
  0x000001a775de3d88:   mov    0x40(%rdi),%rdi
  0x000001a775de3d8c:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de3d96:   cmp    (%r10),%rdi
  0x000001a775de3d99:   je     0x000001a775de3dbe
  0x000001a775de3d9f:   pop    %r10
  0x000001a775de3da1:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de3da2:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de3dac:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de3db0:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3dba:   call   *%r10
  0x000001a775de3dbd:   hlt
 ;; L_ok:
  0x000001a775de3dbe:   pop    %r10
  0x000001a775de3dc0:   pop    %rdi
 ;; } verify_klass
  0x000001a775de3dc1:   mov    0x10(%r11),%r11
  0x000001a775de3dc5:   cmp    %r11,%r10
  0x000001a775de3dc8:   je     0x000001a775de3e2f
  0x000001a775de3dce:   mov    0x1c(%r11),%eax
  0x000001a775de3dd2:   cmp    (%r10,%rax,1),%r11
  0x000001a775de3dd6:   je     0x000001a775de3e2f
  0x000001a775de3ddc:   cmp    $0x28,%eax
  0x000001a775de3ddf:   jne    0x000001a775de3e13
  0x000001a775de3de5:   mov    %r11,%rax
  0x000001a775de3de8:   push   %rcx
  0x000001a775de3de9:   push   %rdi
  0x000001a775de3dea:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de3df4:   incl   (%rcx)
  0x000001a775de3df6:   mov    0x30(%r10),%rdi
  0x000001a775de3dfa:   mov    (%rdi),%ecx
  0x000001a775de3dfc:   add    $0x8,%rdi
  0x000001a775de3e00:   test   %rax,%rax
  0x000001a775de3e03:   repnz scas %es:(%rdi),%rax
  0x000001a775de3e06:   pop    %rdi
  0x000001a775de3e07:   pop    %rcx
  0x000001a775de3e08:   jne    0x000001a775de3e13
  0x000001a775de3e0a:   mov    %r11,0x28(%r10)
  0x000001a775de3e0e:   jmp    0x000001a775de3e2f
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de3e13:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de3e1d:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de3e21:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3e2b:   call   *%r10
  0x000001a775de3e2e:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de3e2f:   mov    0xc(%r8),%eax
  0x000001a775de3e33:   shr    $0x18,%eax
  0x000001a775de3e36:   and    $0xf,%eax
  0x000001a775de3e39:   cmp    $0x7,%eax
  0x000001a775de3e3c:   je     0x000001a775de3e5e
 ;; verify_ref_kind expected 7
  0x000001a775de3e42:   movabs $0x1a70c1c85d0,%rcx          ;   {external_word}
  0x000001a775de3e4c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de3e50:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3e5a:   call   *%r10
  0x000001a775de3e5d:   hlt
 ;; } verify_ref_kind
  0x000001a775de3e5e:   mov    0x24(%r8),%ebx
  0x000001a775de3e62:   push   %r10
  0x000001a775de3e64:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de3e6e:   cmp    (%r10),%r12
  0x000001a775de3e71:   je     0x000001a775de3e93
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de3e77:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de3e81:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de3e85:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de3e8f:   call   *%r10
  0x000001a775de3e92:   hlt
  0x000001a775de3e93:   pop    %r10
  0x000001a775de3e95:   shl    $0x3,%rbx
  0x000001a775de3e99:   mov    0x10(%rbx),%rbx
  0x000001a775de3e9d:   test   %rbx,%rbx
  0x000001a775de3ea0:   je     0x000001a775de3ea9
  0x000001a775de3ea6:   jmp    *0x50(%rbx)
  0x000001a775de3ea9:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de3eae:   hlt
  0x000001a775de3eaf:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    1962   18     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LL)V (native)
 total in heap  [0x000001a775de3f10,0x000001a775de4330] = 1056
 relocation     [0x000001a775de4088,0x000001a775de4118] = 144
 main code      [0x000001a775de4120,0x000001a775de4330] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f897250} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)V' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de4120:   test   %r8,%r8
  0x000001a775de4123:   je     0x000001a775de416a
  0x000001a775de4129:   push   %rdi
  0x000001a775de412a:   push   %r10
  0x000001a775de412c:   mov    0x8(%r8),%edi
  0x000001a775de4130:   movabs $0x800000000,%r10
  0x000001a775de413a:   add    %r10,%rdi
  0x000001a775de413d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de4147:   cmp    (%r10),%rdi
  0x000001a775de414a:   je     0x000001a775de4186
  0x000001a775de4150:   mov    0x40(%rdi),%rdi
  0x000001a775de4154:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de415e:   cmp    (%r10),%rdi
  0x000001a775de4161:   je     0x000001a775de4186
  0x000001a775de4167:   pop    %r10
  0x000001a775de4169:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de416a:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de4174:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4178:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4182:   call   *%r10
  0x000001a775de4185:   hlt
 ;; L_ok:
  0x000001a775de4186:   pop    %r10
  0x000001a775de4188:   pop    %rdi
 ;; } verify_klass
  0x000001a775de4189:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de418c:   mov    0x8(%rdx),%r10d
  0x000001a775de4190:   movabs $0x800000000,%r11
  0x000001a775de419a:   add    %r11,%r10
  0x000001a775de419d:   mov    0x18(%r8),%r11d
  0x000001a775de41a1:   push   %r10
  0x000001a775de41a3:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de41ad:   cmp    (%r10),%r12
  0x000001a775de41b0:   je     0x000001a775de41d2
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de41b6:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de41c0:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de41c4:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de41ce:   call   *%r10
  0x000001a775de41d1:   hlt
  0x000001a775de41d2:   pop    %r10
  0x000001a775de41d4:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de41d8:   test   %r11,%r11
  0x000001a775de41db:   je     0x000001a775de4222
  0x000001a775de41e1:   push   %rdi
  0x000001a775de41e2:   push   %r10
  0x000001a775de41e4:   mov    0x8(%r11),%edi
  0x000001a775de41e8:   movabs $0x800000000,%r10
  0x000001a775de41f2:   add    %r10,%rdi
  0x000001a775de41f5:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de41ff:   cmp    (%r10),%rdi
  0x000001a775de4202:   je     0x000001a775de423e
  0x000001a775de4208:   mov    0x40(%rdi),%rdi
  0x000001a775de420c:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de4216:   cmp    (%r10),%rdi
  0x000001a775de4219:   je     0x000001a775de423e
  0x000001a775de421f:   pop    %r10
  0x000001a775de4221:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de4222:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de422c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4230:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de423a:   call   *%r10
  0x000001a775de423d:   hlt
 ;; L_ok:
  0x000001a775de423e:   pop    %r10
  0x000001a775de4240:   pop    %rdi
 ;; } verify_klass
  0x000001a775de4241:   mov    0x10(%r11),%r11
  0x000001a775de4245:   cmp    %r11,%r10
  0x000001a775de4248:   je     0x000001a775de42af
  0x000001a775de424e:   mov    0x1c(%r11),%eax
  0x000001a775de4252:   cmp    (%r10,%rax,1),%r11
  0x000001a775de4256:   je     0x000001a775de42af
  0x000001a775de425c:   cmp    $0x28,%eax
  0x000001a775de425f:   jne    0x000001a775de4293
  0x000001a775de4265:   mov    %r11,%rax
  0x000001a775de4268:   push   %rcx
  0x000001a775de4269:   push   %rdi
  0x000001a775de426a:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de4274:   incl   (%rcx)
  0x000001a775de4276:   mov    0x30(%r10),%rdi
  0x000001a775de427a:   mov    (%rdi),%ecx
  0x000001a775de427c:   add    $0x8,%rdi
  0x000001a775de4280:   test   %rax,%rax
  0x000001a775de4283:   repnz scas %es:(%rdi),%rax
  0x000001a775de4286:   pop    %rdi
  0x000001a775de4287:   pop    %rcx
  0x000001a775de4288:   jne    0x000001a775de4293
  0x000001a775de428a:   mov    %r11,0x28(%r10)
  0x000001a775de428e:   jmp    0x000001a775de42af
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de4293:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de429d:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de42a1:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de42ab:   call   *%r10
  0x000001a775de42ae:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de42af:   mov    0xc(%r8),%eax
  0x000001a775de42b3:   shr    $0x18,%eax
  0x000001a775de42b6:   and    $0xf,%eax
  0x000001a775de42b9:   cmp    $0x7,%eax
  0x000001a775de42bc:   je     0x000001a775de42de
 ;; verify_ref_kind expected 7
  0x000001a775de42c2:   movabs $0x1a70c1c8fd0,%rcx          ;   {external_word}
  0x000001a775de42cc:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de42d0:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de42da:   call   *%r10
  0x000001a775de42dd:   hlt
 ;; } verify_ref_kind
  0x000001a775de42de:   mov    0x24(%r8),%ebx
  0x000001a775de42e2:   push   %r10
  0x000001a775de42e4:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de42ee:   cmp    (%r10),%r12
  0x000001a775de42f1:   je     0x000001a775de4313
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de42f7:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de4301:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4305:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de430f:   call   *%r10
  0x000001a775de4312:   hlt
  0x000001a775de4313:   pop    %r10
  0x000001a775de4315:   shl    $0x3,%rbx
  0x000001a775de4319:   mov    0x10(%rbx),%rbx
  0x000001a775de431d:   test   %rbx,%rbx
  0x000001a775de4320:   je     0x000001a775de4329
  0x000001a775de4326:   jmp    *0x50(%rbx)
  0x000001a775de4329:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de432e:   hlt
  0x000001a775de432f:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    2075   19     n 0       java.lang.invoke.MethodHandle::linkToInterface(LLL)I (native)
 total in heap  [0x000001a775de4390,0x000001a775de4748] = 952
 relocation     [0x000001a775de4508,0x000001a775de4580] = 120
 main code      [0x000001a775de4580,0x000001a775de4748] = 456

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f898848} 'linkToInterface' '(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/invoke/MemberName;)I' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/Object'
  # parm2:    r9:r9     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de4580:   test   %r9,%r9
  0x000001a775de4583:   je     0x000001a775de45ca
  0x000001a775de4589:   push   %rdi
  0x000001a775de458a:   push   %r10
  0x000001a775de458c:   mov    0x8(%r9),%edi
  0x000001a775de4590:   movabs $0x800000000,%r10
  0x000001a775de459a:   add    %r10,%rdi
  0x000001a775de459d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de45a7:   cmp    (%r10),%rdi
  0x000001a775de45aa:   je     0x000001a775de45e6
  0x000001a775de45b0:   mov    0x40(%rdi),%rdi
  0x000001a775de45b4:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de45be:   cmp    (%r10),%rdi
  0x000001a775de45c1:   je     0x000001a775de45e6
  0x000001a775de45c7:   pop    %r10
  0x000001a775de45c9:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de45ca:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de45d4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de45d8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de45e2:   call   *%r10
  0x000001a775de45e5:   hlt
 ;; L_ok:
  0x000001a775de45e6:   pop    %r10
  0x000001a775de45e8:   pop    %rdi
 ;; } verify_klass
  0x000001a775de45e9:   mov    0x8(%rdx),%r10d
  0x000001a775de45ed:   movabs $0x800000000,%r11
  0x000001a775de45f7:   add    %r11,%r10
 ;; check_receiver {
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de45fa:   mov    0xc(%r9),%eax
  0x000001a775de45fe:   shr    $0x18,%eax
  0x000001a775de4601:   and    $0xf,%eax
  0x000001a775de4604:   cmp    $0x9,%eax
  0x000001a775de4607:   je     0x000001a775de4629
 ;; verify_ref_kind expected 9
  0x000001a775de460d:   movabs $0x1a70c1c82b0,%rcx          ;   {external_word}
  0x000001a775de4617:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de461b:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4625:   call   *%r10
  0x000001a775de4628:   hlt
 ;; } verify_ref_kind
  0x000001a775de4629:   mov    0x18(%r9),%eax
  0x000001a775de462d:   push   %r10
  0x000001a775de462f:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de4639:   cmp    (%r10),%r12
  0x000001a775de463c:   je     0x000001a775de465e
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de4642:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de464c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4650:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de465a:   call   *%r10
  0x000001a775de465d:   hlt
  0x000001a775de465e:   pop    %r10
  0x000001a775de4660:   shl    $0x3,%rax
 ;; verify_klass {
  0x000001a775de4664:   test   %rax,%rax
  0x000001a775de4667:   je     0x000001a775de46ad
  0x000001a775de466d:   push   %rdi
  0x000001a775de466e:   push   %r10
  0x000001a775de4670:   mov    0x8(%rax),%edi
  0x000001a775de4673:   movabs $0x800000000,%r10
  0x000001a775de467d:   add    %r10,%rdi
  0x000001a775de4680:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de468a:   cmp    (%r10),%rdi
  0x000001a775de468d:   je     0x000001a775de46c9
  0x000001a775de4693:   mov    0x40(%rdi),%rdi
  0x000001a775de4697:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de46a1:   cmp    (%r10),%rdi
  0x000001a775de46a4:   je     0x000001a775de46c9
  0x000001a775de46aa:   pop    %r10
  0x000001a775de46ac:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de46ad:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de46b7:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de46bb:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de46c5:   call   *%r10
  0x000001a775de46c8:   hlt
 ;; L_ok:
  0x000001a775de46c9:   pop    %r10
  0x000001a775de46cb:   pop    %rdi
 ;; } verify_klass
  0x000001a775de46cc:   mov    0x10(%rax),%rax
  0x000001a775de46d0:   mov    0x10(%r9),%rbx
  0x000001a775de46d4:   cmp    $0x0,%ebx
  0x000001a775de46d7:   jge    0x000001a775de46f9
 ;; invalid vtable index for MH.invokeInterface
  0x000001a775de46dd:   movabs $0x7ff80bee8e68,%rcx         ;   {external_word}
  0x000001a775de46e7:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de46eb:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de46f5:   call   *%r10
  0x000001a775de46f8:   hlt
  0x000001a775de46f9:   mov    0x18(%r10),%r11d
  0x000001a775de46fd:   lea    0x1d8(%r10,%r11,8),%r11
  0x000001a775de4705:   lea    (%r10,%rbx,8),%r10
  0x000001a775de4709:   mov    (%r11),%rbx
  0x000001a775de470c:   cmp    %rbx,%rax
  0x000001a775de470f:   je     0x000001a775de4726
  0x000001a775de4711:   test   %rbx,%rbx
  0x000001a775de4714:   je     0x000001a775de473f
  0x000001a775de471a:   add    $0x10,%r11
  0x000001a775de471e:   mov    (%r11),%rbx
  0x000001a775de4721:   cmp    %rbx,%rax
  0x000001a775de4724:   jne    0x000001a775de4711
  0x000001a775de4726:   mov    0x8(%r11),%r11d
  0x000001a775de472a:   mov    (%r10,%r11,1),%rbx
  0x000001a775de472e:   test   %rbx,%rbx
  0x000001a775de4731:   je     0x000001a775de473a
  0x000001a775de4737:   jmp    *0x50(%rbx)
  0x000001a775de473a:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de473f:   jmp    0x000001a76e3ba740           ;   {runtime_call IncompatibleClassChangeError throw_exception}
  0x000001a775de4744:   hlt
  0x000001a775de4745:   hlt
  0x000001a775de4746:   hlt
  0x000001a775de4747:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    2265   20     n 0       java.lang.invoke.MethodHandle::linkToInterface(LL)L (native)
 total in heap  [0x000001a775de4790,0x000001a775de4b48] = 952
 relocation     [0x000001a775de4908,0x000001a775de4980] = 120
 main code      [0x000001a775de4980,0x000001a775de4b48] = 456

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f8df1d8} 'linkToInterface' '(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de4980:   test   %r8,%r8
  0x000001a775de4983:   je     0x000001a775de49ca
  0x000001a775de4989:   push   %rdi
  0x000001a775de498a:   push   %r10
  0x000001a775de498c:   mov    0x8(%r8),%edi
  0x000001a775de4990:   movabs $0x800000000,%r10
  0x000001a775de499a:   add    %r10,%rdi
  0x000001a775de499d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de49a7:   cmp    (%r10),%rdi
  0x000001a775de49aa:   je     0x000001a775de49e6
  0x000001a775de49b0:   mov    0x40(%rdi),%rdi
  0x000001a775de49b4:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de49be:   cmp    (%r10),%rdi
  0x000001a775de49c1:   je     0x000001a775de49e6
  0x000001a775de49c7:   pop    %r10
  0x000001a775de49c9:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de49ca:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de49d4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de49d8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de49e2:   call   *%r10
  0x000001a775de49e5:   hlt
 ;; L_ok:
  0x000001a775de49e6:   pop    %r10
  0x000001a775de49e8:   pop    %rdi
 ;; } verify_klass
  0x000001a775de49e9:   mov    0x8(%rdx),%r10d
  0x000001a775de49ed:   movabs $0x800000000,%r11
  0x000001a775de49f7:   add    %r11,%r10
 ;; check_receiver {
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de49fa:   mov    0xc(%r8),%eax
  0x000001a775de49fe:   shr    $0x18,%eax
  0x000001a775de4a01:   and    $0xf,%eax
  0x000001a775de4a04:   cmp    $0x9,%eax
  0x000001a775de4a07:   je     0x000001a775de4a29
 ;; verify_ref_kind expected 9
  0x000001a775de4a0d:   movabs $0x1a70c1c8350,%rcx          ;   {external_word}
  0x000001a775de4a17:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4a1b:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4a25:   call   *%r10
  0x000001a775de4a28:   hlt
 ;; } verify_ref_kind
  0x000001a775de4a29:   mov    0x18(%r8),%eax
  0x000001a775de4a2d:   push   %r10
  0x000001a775de4a2f:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de4a39:   cmp    (%r10),%r12
  0x000001a775de4a3c:   je     0x000001a775de4a5e
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de4a42:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de4a4c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4a50:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4a5a:   call   *%r10
  0x000001a775de4a5d:   hlt
  0x000001a775de4a5e:   pop    %r10
  0x000001a775de4a60:   shl    $0x3,%rax
 ;; verify_klass {
  0x000001a775de4a64:   test   %rax,%rax
  0x000001a775de4a67:   je     0x000001a775de4aad
  0x000001a775de4a6d:   push   %rdi
  0x000001a775de4a6e:   push   %r10
  0x000001a775de4a70:   mov    0x8(%rax),%edi
  0x000001a775de4a73:   movabs $0x800000000,%r10
  0x000001a775de4a7d:   add    %r10,%rdi
  0x000001a775de4a80:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de4a8a:   cmp    (%r10),%rdi
  0x000001a775de4a8d:   je     0x000001a775de4ac9
  0x000001a775de4a93:   mov    0x40(%rdi),%rdi
  0x000001a775de4a97:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de4aa1:   cmp    (%r10),%rdi
  0x000001a775de4aa4:   je     0x000001a775de4ac9
  0x000001a775de4aaa:   pop    %r10
  0x000001a775de4aac:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de4aad:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de4ab7:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4abb:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4ac5:   call   *%r10
  0x000001a775de4ac8:   hlt
 ;; L_ok:
  0x000001a775de4ac9:   pop    %r10
  0x000001a775de4acb:   pop    %rdi
 ;; } verify_klass
  0x000001a775de4acc:   mov    0x10(%rax),%rax
  0x000001a775de4ad0:   mov    0x10(%r8),%rbx
  0x000001a775de4ad4:   cmp    $0x0,%ebx
  0x000001a775de4ad7:   jge    0x000001a775de4af9
 ;; invalid vtable index for MH.invokeInterface
  0x000001a775de4add:   movabs $0x7ff80bee8e68,%rcx         ;   {external_word}
  0x000001a775de4ae7:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4aeb:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4af5:   call   *%r10
  0x000001a775de4af8:   hlt
  0x000001a775de4af9:   mov    0x18(%r10),%r11d
  0x000001a775de4afd:   lea    0x1d8(%r10,%r11,8),%r11
  0x000001a775de4b05:   lea    (%r10,%rbx,8),%r10
  0x000001a775de4b09:   mov    (%r11),%rbx
  0x000001a775de4b0c:   cmp    %rbx,%rax
  0x000001a775de4b0f:   je     0x000001a775de4b26
  0x000001a775de4b11:   test   %rbx,%rbx
  0x000001a775de4b14:   je     0x000001a775de4b3f
  0x000001a775de4b1a:   add    $0x10,%r11
  0x000001a775de4b1e:   mov    (%r11),%rbx
  0x000001a775de4b21:   cmp    %rbx,%rax
  0x000001a775de4b24:   jne    0x000001a775de4b11
  0x000001a775de4b26:   mov    0x8(%r11),%r11d
  0x000001a775de4b2a:   mov    (%r10,%r11,1),%rbx
  0x000001a775de4b2e:   test   %rbx,%rbx
  0x000001a775de4b31:   je     0x000001a775de4b3a
  0x000001a775de4b37:   jmp    *0x50(%rbx)
  0x000001a775de4b3a:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de4b3f:   jmp    0x000001a76e3ba740           ;   {runtime_call IncompatibleClassChangeError throw_exception}
  0x000001a775de4b44:   hlt
  0x000001a775de4b45:   hlt
  0x000001a775de4b46:   hlt
  0x000001a775de4b47:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    2372   21     n 0       java.lang.invoke.MethodHandle::linkToStatic(LL)I (native)
 total in heap  [0x000001a775de4b90,0x000001a775de4e48] = 696
 relocation     [0x000001a775de4d08,0x000001a775de4d50] = 72
 main code      [0x000001a775de4d60,0x000001a775de4e48] = 232

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f8e6b88} 'linkToStatic' '(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)I' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de4d60:   test   %r8,%r8
  0x000001a775de4d63:   je     0x000001a775de4daa
  0x000001a775de4d69:   push   %rdi
  0x000001a775de4d6a:   push   %r10
  0x000001a775de4d6c:   mov    0x8(%r8),%edi
  0x000001a775de4d70:   movabs $0x800000000,%r10
  0x000001a775de4d7a:   add    %r10,%rdi
  0x000001a775de4d7d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de4d87:   cmp    (%r10),%rdi
  0x000001a775de4d8a:   je     0x000001a775de4dc6
  0x000001a775de4d90:   mov    0x40(%rdi),%rdi
  0x000001a775de4d94:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de4d9e:   cmp    (%r10),%rdi
  0x000001a775de4da1:   je     0x000001a775de4dc6
  0x000001a775de4da7:   pop    %r10
  0x000001a775de4da9:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de4daa:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de4db4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4db8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4dc2:   call   *%r10
  0x000001a775de4dc5:   hlt
 ;; L_ok:
  0x000001a775de4dc6:   pop    %r10
  0x000001a775de4dc8:   pop    %rdi
 ;; } verify_klass
 ;; verify_ref_kind {
  0x000001a775de4dc9:   mov    0xc(%r8),%eax
  0x000001a775de4dcd:   shr    $0x18,%eax
  0x000001a775de4dd0:   and    $0xf,%eax
  0x000001a775de4dd3:   cmp    $0x6,%eax
  0x000001a775de4dd6:   je     0x000001a775de4df8
 ;; verify_ref_kind expected 6
  0x000001a775de4ddc:   movabs $0x1a70c1c9cf0,%rcx          ;   {external_word}
  0x000001a775de4de6:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4dea:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4df4:   call   *%r10
  0x000001a775de4df7:   hlt
 ;; } verify_ref_kind
  0x000001a775de4df8:   mov    0x24(%r8),%ebx
  0x000001a775de4dfc:   push   %r10
  0x000001a775de4dfe:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de4e08:   cmp    (%r10),%r12
  0x000001a775de4e0b:   je     0x000001a775de4e2d
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de4e11:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de4e1b:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de4e1f:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de4e29:   call   *%r10
  0x000001a775de4e2c:   hlt
  0x000001a775de4e2d:   pop    %r10
  0x000001a775de4e2f:   shl    $0x3,%rbx
  0x000001a775de4e33:   mov    0x10(%rbx),%rbx
  0x000001a775de4e37:   test   %rbx,%rbx
  0x000001a775de4e3a:   je     0x000001a775de4e43
  0x000001a775de4e40:   jmp    *0x50(%rbx)
  0x000001a775de4e43:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
--------------------------------------------------------------------------------

Compiled method (n/a)    2432   22     n 0       java.lang.invoke.MethodHandle::linkToVirtual(LL)L (native)
 total in heap  [0x000001a775de4e90,0x000001a775de52a0] = 1040
 relocation     [0x000001a775de5008,0x000001a775de5090] = 136
 main code      [0x000001a775de50a0,0x000001a775de52a0] = 512

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f8e9388} 'linkToVirtual' '(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)Ljava/lang/Object;' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de50a0:   test   %r8,%r8
  0x000001a775de50a3:   je     0x000001a775de50ea
  0x000001a775de50a9:   push   %rdi
  0x000001a775de50aa:   push   %r10
  0x000001a775de50ac:   mov    0x8(%r8),%edi
  0x000001a775de50b0:   movabs $0x800000000,%r10
  0x000001a775de50ba:   add    %r10,%rdi
  0x000001a775de50bd:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de50c7:   cmp    (%r10),%rdi
  0x000001a775de50ca:   je     0x000001a775de5106
  0x000001a775de50d0:   mov    0x40(%rdi),%rdi
  0x000001a775de50d4:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de50de:   cmp    (%r10),%rdi
  0x000001a775de50e1:   je     0x000001a775de5106
  0x000001a775de50e7:   pop    %r10
  0x000001a775de50e9:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de50ea:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de50f4:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de50f8:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de5102:   call   *%r10
  0x000001a775de5105:   hlt
 ;; L_ok:
  0x000001a775de5106:   pop    %r10
  0x000001a775de5108:   pop    %rdi
 ;; } verify_klass
  0x000001a775de5109:   mov    0x8(%rdx),%r10d
  0x000001a775de510d:   movabs $0x800000000,%r11
  0x000001a775de5117:   add    %r11,%r10
 ;; check_receiver {
  0x000001a775de511a:   mov    0x18(%r8),%r11d
  0x000001a775de511e:   push   %r10
  0x000001a775de5120:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de512a:   cmp    (%r10),%r12
  0x000001a775de512d:   je     0x000001a775de514f
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de5133:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de513d:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de5141:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de514b:   call   *%r10
  0x000001a775de514e:   hlt
  0x000001a775de514f:   pop    %r10
  0x000001a775de5151:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de5155:   test   %r11,%r11
  0x000001a775de5158:   je     0x000001a775de519f
  0x000001a775de515e:   push   %rdi
  0x000001a775de515f:   push   %r10
  0x000001a775de5161:   mov    0x8(%r11),%edi
  0x000001a775de5165:   movabs $0x800000000,%r10
  0x000001a775de516f:   add    %r10,%rdi
  0x000001a775de5172:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de517c:   cmp    (%r10),%rdi
  0x000001a775de517f:   je     0x000001a775de51bb
  0x000001a775de5185:   mov    0x40(%rdi),%rdi
  0x000001a775de5189:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de5193:   cmp    (%r10),%rdi
  0x000001a775de5196:   je     0x000001a775de51bb
  0x000001a775de519c:   pop    %r10
  0x000001a775de519e:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de519f:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de51a9:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de51ad:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de51b7:   call   *%r10
  0x000001a775de51ba:   hlt
 ;; L_ok:
  0x000001a775de51bb:   pop    %r10
  0x000001a775de51bd:   pop    %rdi
 ;; } verify_klass
  0x000001a775de51be:   mov    0x10(%r11),%r11
  0x000001a775de51c2:   cmp    %r11,%r10
  0x000001a775de51c5:   je     0x000001a775de522c
  0x000001a775de51cb:   mov    0x1c(%r11),%eax
  0x000001a775de51cf:   cmp    (%r10,%rax,1),%r11
  0x000001a775de51d3:   je     0x000001a775de522c
  0x000001a775de51d9:   cmp    $0x28,%eax
  0x000001a775de51dc:   jne    0x000001a775de5210
  0x000001a775de51e2:   mov    %r11,%rax
  0x000001a775de51e5:   push   %rcx
  0x000001a775de51e6:   push   %rdi
  0x000001a775de51e7:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de51f1:   incl   (%rcx)
  0x000001a775de51f3:   mov    0x30(%r10),%rdi
  0x000001a775de51f7:   mov    (%rdi),%ecx
  0x000001a775de51f9:   add    $0x8,%rdi
  0x000001a775de51fd:   test   %rax,%rax
  0x000001a775de5200:   repnz scas %es:(%rdi),%rax
  0x000001a775de5203:   pop    %rdi
  0x000001a775de5204:   pop    %rcx
  0x000001a775de5205:   jne    0x000001a775de5210
  0x000001a775de5207:   mov    %r11,0x28(%r10)
  0x000001a775de520b:   jmp    0x000001a775de522c
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de5210:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de521a:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de521e:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de5228:   call   *%r10
  0x000001a775de522b:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de522c:   mov    0xc(%r8),%eax
  0x000001a775de5230:   shr    $0x18,%eax
  0x000001a775de5233:   and    $0xf,%eax
  0x000001a775de5236:   cmp    $0x5,%eax
  0x000001a775de5239:   je     0x000001a775de525b
 ;; verify_ref_kind expected 5
  0x000001a775de523f:   movabs $0x1a70c1c9a70,%rcx          ;   {external_word}
  0x000001a775de5249:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de524d:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de5257:   call   *%r10
  0x000001a775de525a:   hlt
 ;; } verify_ref_kind
  0x000001a775de525b:   mov    0x10(%r8),%r11
  0x000001a775de525f:   cmp    $0x0,%r11d
  0x000001a775de5263:   jge    0x000001a775de5285
 ;; no virtual index
  0x000001a775de5269:   movabs $0x7ff80bee8e40,%rcx         ;   {external_word}
  0x000001a775de5273:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de5277:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de5281:   call   *%r10
  0x000001a775de5284:   hlt
 ;; L_index_ok:
  0x000001a775de5285:   mov    0x1d8(%r10,%r11,8),%rbx
  0x000001a775de528d:   test   %rbx,%rbx
  0x000001a775de5290:   je     0x000001a775de5299
  0x000001a775de5296:   jmp    *0x50(%rbx)
  0x000001a775de5299:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de529e:   hlt
  0x000001a775de529f:   hlt
--------------------------------------------------------------------------------

Compiled method (n/a)    2546   23     n 0       java.lang.invoke.MethodHandle::linkToSpecial(LL)I (native)
 total in heap  [0x000001a775de5310,0x000001a775de5730] = 1056
 relocation     [0x000001a775de5488,0x000001a775de5518] = 144
 main code      [0x000001a775de5520,0x000001a775de5730] = 528

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Verified Entry Point]
  # {method} {0x000001a77f8edd40} 'linkToSpecial' '(Ljava/lang/Object;Ljava/lang/invoke/MemberName;)I' in 'java/lang/invoke/MethodHandle'
  # parm0:    rdx:rdx   = 'java/lang/Object'
  # parm1:    r8:r8     = 'java/lang/invoke/MemberName'
  #           [sp+0x0]  (sp of caller)
 ;; verify_klass {
  0x000001a775de5520:   test   %r8,%r8
  0x000001a775de5523:   je     0x000001a775de556a
  0x000001a775de5529:   push   %rdi
  0x000001a775de552a:   push   %r10
  0x000001a775de552c:   mov    0x8(%r8),%edi
  0x000001a775de5530:   movabs $0x800000000,%r10
  0x000001a775de553a:   add    %r10,%rdi
  0x000001a775de553d:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de5547:   cmp    (%r10),%rdi
  0x000001a775de554a:   je     0x000001a775de5586
  0x000001a775de5550:   mov    0x40(%rdi),%rdi
  0x000001a775de5554:   movabs $0x7ff80c249f10,%r10         ;   {external_word}
  0x000001a775de555e:   cmp    (%r10),%rdi
  0x000001a775de5561:   je     0x000001a775de5586
  0x000001a775de5567:   pop    %r10
  0x000001a775de5569:   pop    %rdi
 ;; MemberName required for invokeVirtual etc.
  0x000001a775de556a:   movabs $0x7ff80bee8d38,%rcx         ;   {external_word}
  0x000001a775de5574:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de5578:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de5582:   call   *%r10
  0x000001a775de5585:   hlt
 ;; L_ok:
  0x000001a775de5586:   pop    %r10
  0x000001a775de5588:   pop    %rdi
 ;; } verify_klass
  0x000001a775de5589:   cmp    (%rdx),%rax
 ;; check_receiver {
  0x000001a775de558c:   mov    0x8(%rdx),%r10d
  0x000001a775de5590:   movabs $0x800000000,%r11
  0x000001a775de559a:   add    %r11,%r10
  0x000001a775de559d:   mov    0x18(%r8),%r11d
  0x000001a775de55a1:   push   %r10
  0x000001a775de55a3:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de55ad:   cmp    (%r10),%r12
  0x000001a775de55b0:   je     0x000001a775de55d2
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de55b6:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de55c0:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de55c4:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de55ce:   call   *%r10
  0x000001a775de55d1:   hlt
  0x000001a775de55d2:   pop    %r10
  0x000001a775de55d4:   shl    $0x3,%r11
 ;; verify_klass {
  0x000001a775de55d8:   test   %r11,%r11
  0x000001a775de55db:   je     0x000001a775de5622
  0x000001a775de55e1:   push   %rdi
  0x000001a775de55e2:   push   %r10
  0x000001a775de55e4:   mov    0x8(%r11),%edi
  0x000001a775de55e8:   movabs $0x800000000,%r10
  0x000001a775de55f2:   add    %r10,%rdi
  0x000001a775de55f5:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de55ff:   cmp    (%r10),%rdi
  0x000001a775de5602:   je     0x000001a775de563e
  0x000001a775de5608:   mov    0x40(%rdi),%rdi
  0x000001a775de560c:   movabs $0x7ff80c249d70,%r10         ;   {external_word}
  0x000001a775de5616:   cmp    (%r10),%rdi
  0x000001a775de5619:   je     0x000001a775de563e
  0x000001a775de561f:   pop    %r10
  0x000001a775de5621:   pop    %rdi
 ;; MH argument is a Class
  0x000001a775de5622:   movabs $0x7ff80bee8858,%rcx         ;   {external_word}
  0x000001a775de562c:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de5630:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de563a:   call   *%r10
  0x000001a775de563d:   hlt
 ;; L_ok:
  0x000001a775de563e:   pop    %r10
  0x000001a775de5640:   pop    %rdi
 ;; } verify_klass
  0x000001a775de5641:   mov    0x10(%r11),%r11
  0x000001a775de5645:   cmp    %r11,%r10
  0x000001a775de5648:   je     0x000001a775de56af
  0x000001a775de564e:   mov    0x1c(%r11),%eax
  0x000001a775de5652:   cmp    (%r10,%rax,1),%r11
  0x000001a775de5656:   je     0x000001a775de56af
  0x000001a775de565c:   cmp    $0x28,%eax
  0x000001a775de565f:   jne    0x000001a775de5693
  0x000001a775de5665:   mov    %r11,%rax
  0x000001a775de5668:   push   %rcx
  0x000001a775de5669:   push   %rdi
  0x000001a775de566a:   movabs $0x7ff80c22c314,%rcx         ;   {external_word}
  0x000001a775de5674:   incl   (%rcx)
  0x000001a775de5676:   mov    0x30(%r10),%rdi
  0x000001a775de567a:   mov    (%rdi),%ecx
  0x000001a775de567c:   add    $0x8,%rdi
  0x000001a775de5680:   test   %rax,%rax
  0x000001a775de5683:   repnz scas %es:(%rdi),%rax
  0x000001a775de5686:   pop    %rdi
  0x000001a775de5687:   pop    %rcx
  0x000001a775de5688:   jne    0x000001a775de5693
  0x000001a775de568a:   mov    %r11,0x28(%r10)
  0x000001a775de568e:   jmp    0x000001a775de56af
 ;; receiver class disagrees with MemberName.clazz
  0x000001a775de5693:   movabs $0x7ff80bee8df8,%rcx         ;   {external_word}
  0x000001a775de569d:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de56a1:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de56ab:   call   *%r10
  0x000001a775de56ae:   hlt
 ;; } check_receiver
 ;; verify_ref_kind {
  0x000001a775de56af:   mov    0xc(%r8),%eax
  0x000001a775de56b3:   shr    $0x18,%eax
  0x000001a775de56b6:   and    $0xf,%eax
  0x000001a775de56b9:   cmp    $0x7,%eax
  0x000001a775de56bc:   je     0x000001a775de56de
 ;; verify_ref_kind expected 7
  0x000001a775de56c2:   movabs $0x1a70c1c99d0,%rcx          ;   {external_word}
  0x000001a775de56cc:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de56d0:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de56da:   call   *%r10
  0x000001a775de56dd:   hlt
 ;; } verify_ref_kind
  0x000001a775de56de:   mov    0x24(%r8),%ebx
  0x000001a775de56e2:   push   %r10
  0x000001a775de56e4:   movabs $0x7ff80c138568,%r10         ;   {external_word}
  0x000001a775de56ee:   cmp    (%r10),%r12
  0x000001a775de56f1:   je     0x000001a775de5713
 ;; MacroAssembler::decode_heap_oop: heap base corrupted?
  0x000001a775de56f7:   movabs $0x7ff80bed4040,%rcx         ;   {external_word}
  0x000001a775de5701:   and    $0xfffffffffffffff0,%rsp
  0x000001a775de5705:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a775de570f:   call   *%r10
  0x000001a775de5712:   hlt
  0x000001a775de5713:   pop    %r10
  0x000001a775de5715:   shl    $0x3,%rbx
  0x000001a775de5719:   mov    0x10(%rbx),%rbx
  0x000001a775de571d:   test   %rbx,%rbx
  0x000001a775de5720:   je     0x000001a775de5729
  0x000001a775de5726:   jmp    *0x50(%rbx)
  0x000001a775de5729:   jmp    0x000001a76e3baa40           ;   {runtime_call AbstractMethodError throw_exception}
  0x000001a775de572e:   hlt
  0x000001a775de572f:   hlt
--------------------------------------------------------------------------------

============================= C1-compiled nmethod ==============================
----------------------------------- Assembly -----------------------------------

Compiled method (c1)    2720   24       3       org.fenixsoft.jvm.chapter4.Bar::sum (11 bytes)
 total in heap  [0x000001a76e8b0010,0x000001a76e8b0398] = 904
 relocation     [0x000001a76e8b0188,0x000001a76e8b01b8] = 48
 main code      [0x000001a76e8b01c0,0x000001a76e8b02a0] = 224
 stub code      [0x000001a76e8b02a0,0x000001a76e8b0300] = 96
 oops           [0x000001a76e8b0300,0x000001a76e8b0308] = 8
 metadata       [0x000001a76e8b0308,0x000001a76e8b0310] = 8
 scopes data    [0x000001a76e8b0310,0x000001a76e8b0330] = 32
 scopes pcs     [0x000001a76e8b0330,0x000001a76e8b0390] = 96
 dependencies   [0x000001a76e8b0390,0x000001a76e8b0398] = 8

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Entry Point]
  # {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar'
  # this:     rdx:rdx   = 'org/fenixsoft/jvm/chapter4/Bar'
  # parm0:    r8        = int
  #           [sp+0x40]  (sp of caller)
 ;;  block B1 [0, 0]
  0x000001a76e8b01c0:   mov    0x8(%rdx),%r10d
  0x000001a76e8b01c4:   movabs $0x800000000,%r11
  0x000001a76e8b01ce:   add    %r11,%r10
  0x000001a76e8b01d1:   cmp    %rax,%r10
  0x000001a76e8b01d4:   jne    0x000001a76e425c20           ;   {runtime_call ic_miss_stub}
  0x000001a76e8b01da:   nop
  0x000001a76e8b01db:   nop
  0x000001a76e8b01dc:   nop
  0x000001a76e8b01dd:   nop
  0x000001a76e8b01de:   nop
  0x000001a76e8b01df:   nop
[Verified Entry Point]
  0x000001a76e8b01e0:   mov    %eax,-0x9000(%rsp)
  0x000001a76e8b01e7:   push   %rbp
  0x000001a76e8b01e8:   sub    $0x30,%rsp
  0x000001a76e8b01ec:   movabs $0x1a77f80f680,%rax          ;   {metadata(method data for {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar')}
  0x000001a76e8b01f6:   mov    0x104(%rax),%esi
  0x000001a76e8b01fc:   add    $0x2,%esi
  0x000001a76e8b01ff:   mov    %esi,0x104(%rax)
 ;;   16 branch [AL] [CounterOverflowStub: 0x000001a70c2de3b0]
  0x000001a76e8b0205:   jmp    0x000001a76e8b0232           ;*aload_0 {reexecute=0 rethrow=0 return_oop=0}
                                                            ; - org.fenixsoft.jvm.chapter4.Bar::sum@0 (line 7)
 ;;  block B2 [0, 0]
 ;;  block B0 [0, 10]
  0x000001a76e8b020a:   mov    0xc(%rdx),%eax               ;*getfield a {reexecute=0 rethrow=0 return_oop=0}
                                                            ; - org.fenixsoft.jvm.chapter4.Bar::sum@1 (line 7)
  0x000001a76e8b020d:   movabs $0x7190934e0,%rsi            ;   {oop(a 'java/lang/Class'{0x00000007190934e0} = 'org/fenixsoft/jvm/chapter4/Bar')}
  0x000001a76e8b0217:   mov    0x70(%rsi),%esi              ;*getstatic b {reexecute=0 rethrow=0 return_oop=0}
                                                            ; - org.fenixsoft.jvm.chapter4.Bar::sum@4 (line 7)
  0x000001a76e8b021a:   add    %esi,%eax
  0x000001a76e8b021c:   add    %r8d,%eax
  0x000001a76e8b021f:   add    $0x30,%rsp
  0x000001a76e8b0223:   pop    %rbp
  0x000001a76e8b0224:   cmp    0x390(%r15),%rsp             ;   {poll_return}
  0x000001a76e8b022b:   ja     0x000001a76e8b0250
  0x000001a76e8b0231:   ret
 ;; CounterOverflowStub slow case
  0x000001a76e8b0232:   movabs $0x1a77f80f300,%r10          ;   {metadata({method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar')}
  0x000001a76e8b023c:   mov    %r10,0x8(%rsp)
  0x000001a76e8b0241:   movq   $0xffffffffffffffff,(%rsp)
  0x000001a76e8b0249:   call   0x000001a76e3855c0           ; ImmutableOopMap {rdx=Oop }
                                                            ;*synchronization entry
                                                            ; - org.fenixsoft.jvm.chapter4.Bar::sum@-1 (line 7)
                                                            ;   {runtime_call counter_overflow Runtime1 stub}
  0x000001a76e8b024e:   jmp    0x000001a76e8b020a
 ;; C1SafepointPollStub slow case
  0x000001a76e8b0250:   movabs $0x1a76e8b0224,%r10          ;   {internal_word}
  0x000001a76e8b025a:   mov    %r10,0x3a8(%r15)
  0x000001a76e8b0261:   jmp    0x000001a76e42c420           ;   {runtime_call SafepointBlob}
  0x000001a76e8b0266:   nop
  0x000001a76e8b0267:   nop
 ;; Unwind handler
  0x000001a76e8b0268:   mov    0x430(%r15),%rax
  0x000001a76e8b026f:   movq   $0x0,0x430(%r15)
  0x000001a76e8b027a:   movq   $0x0,0x438(%r15)
  0x000001a76e8b0285:   add    $0x30,%rsp
  0x000001a76e8b0289:   pop    %rbp
  0x000001a76e8b028a:   jmp    0x000001a76e38d940           ;   {runtime_call unwind_exception Runtime1 stub}
  0x000001a76e8b028f:   hlt
  0x000001a76e8b0290:   hlt
  0x000001a76e8b0291:   hlt
  0x000001a76e8b0292:   hlt
  0x000001a76e8b0293:   hlt
  0x000001a76e8b0294:   hlt
  0x000001a76e8b0295:   hlt
  0x000001a76e8b0296:   hlt
  0x000001a76e8b0297:   hlt
  0x000001a76e8b0298:   hlt
  0x000001a76e8b0299:   hlt
  0x000001a76e8b029a:   hlt
  0x000001a76e8b029b:   hlt
  0x000001a76e8b029c:   hlt
  0x000001a76e8b029d:   hlt
  0x000001a76e8b029e:   hlt
  0x000001a76e8b029f:   hlt
[Exception Handler]
  0x000001a76e8b02a0:   movabs $0xdead,%rbx                 ;   {no_reloc}
  0x000001a76e8b02aa:   movabs $0xdead,%rcx
  0x000001a76e8b02b4:   movabs $0xdead,%rsi
  0x000001a76e8b02be:   movabs $0xdead,%rdi
  0x000001a76e8b02c8:   call   0x000001a76e389d40           ;   {runtime_call handle_exception_from_callee Runtime1 stub}
  0x000001a76e8b02cd:   movabs $0x7ff80bd91f90,%rcx         ;   {external_word}
  0x000001a76e8b02d7:   and    $0xfffffffffffffff0,%rsp
  0x000001a76e8b02db:   movabs $0x7ff80b85f020,%r10         ;   {runtime_call MacroAssembler::debug64}
  0x000001a76e8b02e5:   call   *%r10
  0x000001a76e8b02e8:   hlt
[Deopt Handler Code]
  0x000001a76e8b02e9:   movabs $0x1a76e8b02e9,%r10          ;   {section_word}
  0x000001a76e8b02f3:   push   %r10
  0x000001a76e8b02f5:   jmp    0x000001a76e42b560           ;   {runtime_call DeoptimizationBlob}
  0x000001a76e8b02fa:   hlt
  0x000001a76e8b02fb:   hlt
  0x000001a76e8b02fc:   hlt
  0x000001a76e8b02fd:   hlt
  0x000001a76e8b02fe:   hlt
  0x000001a76e8b02ff:   hlt
--------------------------------------------------------------------------------
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Oops:
  0x000001a76e8b0300:   0x00000007193cda48 a 'jdk/internal/loader/ClassLoaders$AppClassLoader'{0x00000007193cda48}
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Metadata:
  0x000001a76e8b0308:   0x000001a77f80f300 {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar'
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
pc-bytecode offsets:
PcDesc(pc=0x000001a76e8b01bf offset=ffffffff bits=0):
PcDesc(pc=0x000001a76e8b020a offset=4a bits=0):
   org.fenixsoft.jvm.chapter4.Bar::sum@0 (line 7)
PcDesc(pc=0x000001a76e8b020d offset=4d bits=0):
   org.fenixsoft.jvm.chapter4.Bar::sum@1 (line 7)
PcDesc(pc=0x000001a76e8b021a offset=5a bits=0):
   org.fenixsoft.jvm.chapter4.Bar::sum@4 (line 7)
PcDesc(pc=0x000001a76e8b024e offset=8e bits=0):
   org.fenixsoft.jvm.chapter4.Bar::sum@-1 (line 7)
   Locals
    - l0: reg rdx [4],oop
    - l1: reg r8 [16],int
PcDesc(pc=0x000001a76e8b0301 offset=141 bits=0):
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
oop maps:ImmutableOopMapSet contains 1 OopMaps

ImmutableOopMap {rdx=Oop } pc offsets: 142
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
scopes:
ScopeDesc(pc=0x000001a76e8b020a offset=4a):
   org.fenixsoft.jvm.chapter4.Bar::sum@0 (line 7)
ScopeDesc(pc=0x000001a76e8b020d offset=4d):
   org.fenixsoft.jvm.chapter4.Bar::sum@1 (line 7)
ScopeDesc(pc=0x000001a76e8b021a offset=5a):
   org.fenixsoft.jvm.chapter4.Bar::sum@4 (line 7)
ScopeDesc(pc=0x000001a76e8b024e offset=8e):
   org.fenixsoft.jvm.chapter4.Bar::sum@-1 (line 7)
   Locals
    - l0: reg rdx [4],oop
    - l1: reg r8 [16],int
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
relocations:
         @0x000001a76e8b0188: 6014
relocInfo@0x000001a76e8b0188 [type=6(runtime_call) addr=0x000001a76e8b01d4 offset=20] | [destination=0x000001a76e425c20]
         @0x000001a76e8b018a: c018
relocInfo@0x000001a76e8b018a [type=12(metadata) addr=0x000001a76e8b01ec offset=24] | [metadata_addr=0x000001a76e8b01ee *=0x000001a77f80f680 offset=0]metadata_value=0x000001a77f80f680: method data for {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar'
         @0x000001a76e8b018c: 1021
relocInfo@0x000001a76e8b018c [type=1(oop) addr=0x000001a76e8b020d offset=33] | [oop_addr=0x000001a76e8b020f *=0x00000007190934e0 offset=0]
         @0x000001a76e8b018e: b017
relocInfo@0x000001a76e8b018e [type=11(poll_return) addr=0x000001a76e8b0224 offset=23]
         @0x000001a76e8b0190: c00e
relocInfo@0x000001a76e8b0190 [type=12(metadata) addr=0x000001a76e8b0232 offset=14] | [metadata_addr=0x000001a76e8b0234 *=0x000001a77f80f300 offset=0]metadata_value=0x000001a77f80f300: {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar'
         @0x000001a76e8b0192: 6417
relocInfo@0x000001a76e8b0192 [type=6(runtime_call) addr=0x000001a76e8b0249 offset=23 format=1] | [destination=0x000001a76e3855c0]
         @0x000001a76e8b0194: f02c8007
relocInfo@0x000001a76e8b0196 [type=8(internal_word) addr=0x000001a76e8b0250 offset=7 data=44] | [target=0x000001a76e8b0224]
         @0x000001a76e8b0198: 6411
relocInfo@0x000001a76e8b0198 [type=6(runtime_call) addr=0x000001a76e8b0261 offset=17 format=1] | [destination=0x000001a76e42c420]
         @0x000001a76e8b019a: 6429
relocInfo@0x000001a76e8b019a [type=6(runtime_call) addr=0x000001a76e8b028a offset=41 format=1] | [destination=0x000001a76e38d940]
         @0x000001a76e8b019c: 0016
relocInfo@0x000001a76e8b019c [type=0(none) addr=0x000001a76e8b02a0 offset=22]
         @0x000001a76e8b019e: 6428
relocInfo@0x000001a76e8b019e [type=6(runtime_call) addr=0x000001a76e8b02c8 offset=40 format=1] | [destination=0x000001a76e389d40]
         @0x000001a76e8b01a0: f8030bd91f907ff87005
relocInfo@0x000001a76e8b01a8 [type=7(external_word) addr=0x000001a76e8b02cd offset=5 data={0bd91f907ff8}] | [target=0x00007ff80bd91f90]
         @0x000001a76e8b01aa: 600e
relocInfo@0x000001a76e8b01aa [type=6(runtime_call) addr=0x000001a76e8b02db offset=14] | [destination=0x00007ff80b85f020]
         @0x000001a76e8b01ac: f801fede900e
relocInfo@0x000001a76e8b01b0 [type=9(section_word) addr=0x000001a76e8b02e9 offset=14 data=-290] | [target=0x000001a76e8b02e9]
         @0x000001a76e8b01b2: 640c
relocInfo@0x000001a76e8b01b2 [type=6(runtime_call) addr=0x000001a76e8b02f5 offset=12 format=1] | [destination=0x000001a76e42b560]
         @0x000001a76e8b01b4: 0000
relocInfo@0x000001a76e8b01b4 [type=0(none) addr=0x000001a76e8b02f5 offset=0]
         @0x000001a76e8b01b6: 0000
relocInfo@0x000001a76e8b01b6 [type=0(none) addr=0x000001a76e8b02f5 offset=0]
         @0x000001a76e8b01b8:
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Dependencies:
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
ExceptionHandlerTable (size = 0 bytes)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
ImplicitExceptionTable is empty
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Recorded oops:
#0: 0x0000000000000000 NULL-oop
#1: 0x00000007193cda48 a 'jdk/internal/loader/ClassLoaders$AppClassLoader'{0x00000007193cda48}
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Recorded metadata:
#0: 0x0000000000000000 NULL-oop
#1: 0x000001a77f80f300 {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar'
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

============================= C2-compiled nmethod ==============================
----------------------- MetaData before Compile_id = 25 ------------------------
{method}
 - this oop:          0x000001a77f80f300
 - method holder:     'org/fenixsoft/jvm/chapter4/Bar'
 - constants:         0x000001a77f80f030 constant pool [34] {0x000001a77f80f038} for 'org/fenixsoft/jvm/chapter4/Bar' cache=0x000001a77f80f4c0
 - access:            0x81000001  public
 - name:              'sum'
 - signature:         '(I)I'
 - max stack:         3
 - max locals:        2
 - size of params:    2
 - method size:       13
 - highest level:     3
 - vtable index:      5
 - i2i entry:         0x000001a76e31da00
 - adapters:          AHE@0x000001a76e2a1790: 0xba i2c: 0x000001a76e42ac60 c2i: 0x000001a76e42ad26 c2iUV: 0x000001a76e42acf0 c2iNCI: 0x000001a76e42ad63
 - compiled entry     0x000001a76e8b01e0
 - code size:         11
 - code start:        0x000001a77f80f2d8
 - code end (excl):   0x000001a77f80f2e3
 - method data:       0x000001a77f80f680
 - checked ex length: 0
 - linenumber start:  0x000001a77f80f2e3
 - localvar length:   2
 - localvar start:    0x000001a77f80f2e6
 - compiled code: nmethod   2886   24       3       org.fenixsoft.jvm.chapter4.Bar::sum (11 bytes)

------------------------ OptoAssembly for Compile_id = 25 -----------------------
#
#  int ( org/fenixsoft/jvm/chapter4/Bar:NotNull *, int )
#
#r016 rdx:rdx   : parm 0: org/fenixsoft/jvm/chapter4/Bar:NotNull *
#r004 r8   : parm 1: int
# -- Old rsp -- Framesize: 32 --
#r591 rsp+28: in_preserve
#r590 rsp+24: return address
#r589 rsp+20: in_preserve
#r588 rsp+16: saved fp register
#r587 rsp+12: pad2, stack alignment
#r586 rsp+ 8: pad2, stack alignment
#r585 rsp+ 4: Fixed slot 1
#r584 rsp+ 0: Fixed slot 0
#
000     N23: #  out( B1 ) <- BLOCK HEAD IS JUNK  Freq: 1
000     movl    rscratch1, [j_rarg0 + oopDesc::klass_offset_in_bytes()] # compressed klass
        decode_klass_not_null rscratch1, rscratch1
        cmpq    rax, rscratch1   # Inline cache check
        jne     SharedRuntime::_ic_miss_stub
        nop     # nops to align entry point

        nop     # 4 bytes pad for loops and calls

020     B1: #   out( N23 ) <- BLOCK HEAD IS JUNK  Freq: 1
020     # stack bang (96 bytes)
        pushq   rbp     # Save rbp
        subq    rsp, #16        # Create frame

02c     movq    R10, java/lang/Class:exact *    # ptr
036     movl    R10, [R10 + #112 (8-bit)]       # int ! Field: org/fenixsoft/jvm/chapter4/Bar.b
03a     addl    R10, [RDX + #12 (8-bit)]        # int
03e     movl    RAX, R8 # spill
041     addl    RAX, R10        # int
044     addq    rsp, 16 # Destroy frame
        popq    rbp
        cmpq     rsp, poll_offset[r15_thread]
        ja       #safepoint_stub        # Safepoint: poll for GC

056     ret

--------------------------------------------------------------------------------
----------------------------------- Assembly -----------------------------------

Compiled method (c2)    2915   25       4       org.fenixsoft.jvm.chapter4.Bar::sum (11 bytes)
 total in heap  [0x000001a775de5790,0x000001a775de5a20] = 656
 relocation     [0x000001a775de5908,0x000001a775de5920] = 24
 main code      [0x000001a775de5920,0x000001a775de59a0] = 128
 stub code      [0x000001a775de59a0,0x000001a775de59b8] = 24
 oops           [0x000001a775de59b8,0x000001a775de59c0] = 8
 metadata       [0x000001a775de59c0,0x000001a775de59c8] = 8
 scopes data    [0x000001a775de59c8,0x000001a775de59d8] = 16
 scopes pcs     [0x000001a775de59d8,0x000001a775de5a18] = 64
 dependencies   [0x000001a775de5a18,0x000001a775de5a20] = 8

--------------------------------------------------------------------------------
[Constant Pool (empty)]

--------------------------------------------------------------------------------

[Entry Point]
  # {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar'
  # this:     rdx:rdx   = 'org/fenixsoft/jvm/chapter4/Bar'
  # parm0:    r8        = int
  #           [sp+0x20]  (sp of caller)
 ;; N23: #      out( B1 ) <- BLOCK HEAD IS JUNK  Freq: 1
  0x000001a775de5920:   mov    0x8(%rdx),%r10d
  0x000001a775de5924:   movabs $0x800000000,%r11
  0x000001a775de592e:   add    %r11,%r10
  0x000001a775de5931:   cmp    %r10,%rax
  0x000001a775de5934:   jne    0x000001a76e425c20           ;   {runtime_call ic_miss_stub}
  0x000001a775de593a:   nop
  0x000001a775de593b:   nop
  0x000001a775de593c:   nop
  0x000001a775de593d:   nop
  0x000001a775de593e:   nop
  0x000001a775de593f:   nop
[Verified Entry Point]
 ;; B1: #       out( N23 ) <- BLOCK HEAD IS JUNK  Freq: 1
  0x000001a775de5940:   mov    %eax,-0x9000(%rsp)
  0x000001a775de5947:   push   %rbp
  0x000001a775de5948:   sub    $0x10,%rsp                   ;*synchronization entry
                                                            ; - org.fenixsoft.jvm.chapter4.Bar::sum@-1 (line 7)
  0x000001a775de594c:   movabs $0x7190934e0,%r10            ;   {oop(a 'java/lang/Class'{0x00000007190934e0} = 'org/fenixsoft/jvm/chapter4/Bar')}
  0x000001a775de5956:   mov    0x70(%r10),%r10d
  0x000001a775de595a:   add    0xc(%rdx),%r10d
  0x000001a775de595e:   mov    %r8d,%eax
  0x000001a775de5961:   add    %r10d,%eax                   ;*iadd {reexecute=0 rethrow=0 return_oop=0}
                                                            ; - org.fenixsoft.jvm.chapter4.Bar::sum@9 (line 7)
  0x000001a775de5964:   add    $0x10,%rsp
  0x000001a775de5968:   pop    %rbp
  0x000001a775de5969:   cmp    0x390(%r15),%rsp             ;   {poll_return}
  0x000001a775de5970:   ja     0x000001a775de5977
  0x000001a775de5976:   ret
  0x000001a775de5977:   movabs $0x1a775de5969,%r10          ;   {internal_word}
  0x000001a775de5981:   mov    %r10,0x3a8(%r15)
  0x000001a775de5988:   jmp    0x000001a76e42c420           ;   {runtime_call SafepointBlob}
  0x000001a775de598d:   hlt
  0x000001a775de598e:   hlt
  0x000001a775de598f:   hlt
  0x000001a775de5990:   hlt
  0x000001a775de5991:   hlt
  0x000001a775de5992:   hlt
  0x000001a775de5993:   hlt
  0x000001a775de5994:   hlt
  0x000001a775de5995:   hlt
  0x000001a775de5996:   hlt
  0x000001a775de5997:   hlt
  0x000001a775de5998:   hlt
  0x000001a775de5999:   hlt
  0x000001a775de599a:   hlt
  0x000001a775de599b:   hlt
  0x000001a775de599c:   hlt
  0x000001a775de599d:   hlt
  0x000001a775de599e:   hlt
  0x000001a775de599f:   hlt
[Exception Handler]
  0x000001a775de59a0:   jmp    0x000001a76e38d220           ;   {no_reloc}
[Deopt Handler Code]
  0x000001a775de59a5:   call   0x000001a775de59aa
  0x000001a775de59aa:   subq   $0x5,(%rsp)
  0x000001a775de59af:   jmp    0x000001a76e42b560           ;   {runtime_call DeoptimizationBlob}
  0x000001a775de59b4:   hlt
  0x000001a775de59b5:   hlt
  0x000001a775de59b6:   hlt
  0x000001a775de59b7:   hlt
--------------------------------------------------------------------------------
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Oops:
  0x000001a775de59b8:   0x00000007193cda48 a 'jdk/internal/loader/ClassLoaders$AppClassLoader'{0x00000007193cda48}
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Metadata:
  0x000001a775de59c0:   0x000001a77f80f300 {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar'
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
pc-bytecode offsets:
PcDesc(pc=0x000001a775de591f offset=ffffffff bits=0):
PcDesc(pc=0x000001a775de594c offset=2c bits=0):
   org.fenixsoft.jvm.chapter4.Bar::sum@-1 (line 7)
PcDesc(pc=0x000001a775de5964 offset=44 bits=0):
   org.fenixsoft.jvm.chapter4.Bar::sum@9 (line 7)
PcDesc(pc=0x000001a775de59b9 offset=99 bits=0):
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
oop maps:ImmutableOopMapSet contains 0 OopMaps

- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
scopes:
ScopeDesc(pc=0x000001a775de594c offset=2c):
   org.fenixsoft.jvm.chapter4.Bar::sum@-1 (line 7)
ScopeDesc(pc=0x000001a775de5964 offset=44):
   org.fenixsoft.jvm.chapter4.Bar::sum@9 (line 7)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
relocations:
         @0x000001a775de5908: 6014
relocInfo@0x000001a775de5908 [type=6(runtime_call) addr=0x000001a775de5934 offset=20] | [destination=0x000001a76e425c20]
         @0x000001a775de590a: 1018
relocInfo@0x000001a775de590a [type=1(oop) addr=0x000001a775de594c offset=24] | [oop_addr=0x000001a775de594e *=0x00000007190934e0 offset=0]
         @0x000001a775de590c: b01d
relocInfo@0x000001a775de590c [type=11(poll_return) addr=0x000001a775de5969 offset=29]
         @0x000001a775de590e: f00e800e
relocInfo@0x000001a775de5910 [type=8(internal_word) addr=0x000001a775de5977 offset=14 data=14] | [target=0x000001a775de5969]
         @0x000001a775de5912: 6411
relocInfo@0x000001a775de5912 [type=6(runtime_call) addr=0x000001a775de5988 offset=17 format=1] | [destination=0x000001a76e42c420]
         @0x000001a775de5914: 0018
relocInfo@0x000001a775de5914 [type=0(none) addr=0x000001a775de59a0 offset=24]
         @0x000001a775de5916: 6400
relocInfo@0x000001a775de5916 [type=6(runtime_call) addr=0x000001a775de59a0 offset=0 format=1] | [destination=0x000001a76e38d220]
         @0x000001a775de5918: 640f
relocInfo@0x000001a775de5918 [type=6(runtime_call) addr=0x000001a775de59af offset=15 format=1] | [destination=0x000001a76e42b560]
         @0x000001a775de591a: 0000
relocInfo@0x000001a775de591a [type=0(none) addr=0x000001a775de59af offset=0]
         @0x000001a775de591c: 0000
relocInfo@0x000001a775de591c [type=0(none) addr=0x000001a775de59af offset=0]
         @0x000001a775de591e: 0000
relocInfo@0x000001a775de591e [type=0(none) addr=0x000001a775de59af offset=0]
         @0x000001a775de5920:
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Dependencies:
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
ExceptionHandlerTable (size = 0 bytes)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
ImplicitExceptionTable is empty
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Recorded oops:
#0: 0x0000000000000000 NULL-oop
#1: 0x00000007193cda48 a 'jdk/internal/loader/ClassLoaders$AppClassLoader'{0x00000007193cda48}
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Recorded metadata:
#0: 0x0000000000000000 NULL-oop
#1: 0x000001a77f80f300 {method} {0x000001a77f80f308} 'sum' '(I)I' in 'org/fenixsoft/jvm/chapter4/Bar'
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

 */