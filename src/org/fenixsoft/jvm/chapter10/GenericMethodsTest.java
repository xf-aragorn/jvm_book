package org.fenixsoft.jvm.chapter10;

import java.util.ArrayList;
import java.util.List;

public class GenericMethodsTest {
        // 只能在jdk 1.6下编译？
//        public  String method(List<String> list) {
//            System.out.println("invoke method(List<String> list)");
//            return "";
//        }
        public  int method(List<Integer> list) {
            System.out.println("invoke method(List<Integer> list)");
            return 1;
        }
        public static void main(String[] args) {
            GenericMethodsTest o = new GenericMethodsTest();
//            o.method(new ArrayList<String>());
            o.method(new ArrayList<Integer>());
        }
    }