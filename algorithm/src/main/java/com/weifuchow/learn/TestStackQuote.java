package com.weifuchow.learn;

public class TestStackQuote {
    public static void main(String[] args) {
        String a = "abc";
        // 因为a 传递过去是一个引用类型，代表a指向 "ABC" 地址的空间。
        // 当进行方法调用，实际上是引用值复制到methodA 中。存储在方法栈中局部变量中。当修改了改值。也只是方法该局部变量发生变化并不会影响主方法
        // 所以a 并不会受影响。其实引用类型 实际上存储的就是改引用空间的起始地址，及大小空间。
        // 都只有一种值 拷贝
        methodA(a);
        System.out.println(a);
    }


    public static void methodA(String a){
        a = "bcd";
    }

}