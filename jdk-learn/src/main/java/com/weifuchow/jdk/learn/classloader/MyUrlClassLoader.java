package com.weifuchow.jdk.learn.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/2/23 16:03
 */
public class MyUrlClassLoader {

    public static void main(String[] args) throws Exception {
        ClassLoader originloader = MyUrlClassLoader.class.getClassLoader();
        System.out.println(originloader);
        URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("D:\\ideaworkspace\\Wlib\\reactive\\target\\reactive-1.0-SNAPSHOT.jar").toURL()});
        Class<?> clazz = classLoader.loadClass("org.weifuchow.HelloWorld");
        clazz.newInstance();
        // throw exception class not found
        Class<?> clazz1 = originloader.loadClass("org.weifuchow.HelloWorld");
        System.out.println(clazz1);

    }
}
