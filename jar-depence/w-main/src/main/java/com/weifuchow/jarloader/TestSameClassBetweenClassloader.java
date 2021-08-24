package com.weifuchow.jarloader;

import com.weifuchow.commons.Module;
import com.weifuchow.commons.SourceType;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/25 15:41
 */
public class TestSameClassBetweenClassloader {

    public static void main(String[] args) throws Exception {

        String classs = "D:/ideaworkspace/Wlib/jar-depence/w-commons/target/w-commons-1.3-SNAPSHOT-jar-with-dependencies.jar";
        args = classs.split(",");
        URL[] urls =  new URL[args.length];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = new File(args[i]).toURL();
            System.out.println("jar path:" + urls[i]);
        }
        //
        System.out.println("original begin");
        printSourceTypes(SourceType.E);
        System.out.println("original end");
        //

        System.out.println("....");
        System.out.println("....");

        System.out.println("load others common begin");
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class<SourceType> clazz = (Class<SourceType>) classLoader.loadClass("com.weifuchow.commons.SourceType");
        System.out.println(clazz.getClassLoader());
        System.out.println(classLoader);
        System.out.println(classLoader.getParent().getParent());
        printSourceTypes(clazz.getEnumConstants()[3]);
        Thread.sleep(100000);
//        System.out.println();
        //


    }

    public static void printSourceTypes(SourceType type){
        System.out.println(type);
        System.out.println(type.getClass().getClassLoader());
        for (SourceType type1:SourceType.values()) {
            System.out.println(type1);
        }
    }
}
