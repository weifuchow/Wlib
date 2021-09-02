package com.weifuchow.jdk.learn.jvm;

public class StringTest {

    public static void main(String[] args) {
        String s = "hello";
        String s1 = new String("hello");
        System.out.println(s1 == s);
        // string.intern(拘留软禁） 放在常量池中
        // 查询常量池是否存在 。如果不存在则放入到池中
        // 直接new 出来的对象放在heap堆中
        System.out.println(s1.intern() == s);
        String fuck = new String("fuck");
        System.out.println(fuck.intern() == "fuck");

    }


}
