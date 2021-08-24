package com.weifuchow.jdk.learn.classloader;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/2/23 10:39
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);
        System.out.println(new ClassLoaderTest().getClass().getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader());
    }

}
