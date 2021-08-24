package com.weifuchow.jdk.learn.jvm;

public class TestStaticObjCinitOrder {


    public static void main(String[] args) throws ClassNotFoundException {

        // class.forName 会对类进行初始化。静态变量及代码块init
//        Class.forName("com.weifuchow.jdk.learn.jvm.TestStaticObjCinitOrder$StaticObj");

        // ClassLoader.loadClass 不会对类进行加载
//        TestStaticObjCinitOrder.class.getClassLoader().loadClass("com.weifuchow.jdk.learn.jvm.TestStaticObjCinitOrder$StaticObj");

        // 会进行初始化。cinit
        System.out.println(StaticObj.NAME);
        System.out.println();
    }


    public static class StaticObj {

        public static String NAME = "STATIC_OBJ";
        static {
            System.out.println("static init");
        }
    }
}
