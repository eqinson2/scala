package com.ericsson.scala;

/**
 * Created by eqinson on 2016/10/8.
 */
public class StringDemo {
    public static void main(String... args) {
        final String a = "ab";
        String b = a + "cd";
        String c = "abcd";
        System.out.println(c == b);
    }
}
