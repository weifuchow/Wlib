package com.weifuchow;

import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/7 10:31
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Loader loader = new Loader();
        Thread.sleep(10000);
        Map<String,ClassLoader> loaderMap = loader.loadJars();
        //
        runAMethod(loaderMap.get("A"));
        runBMethod(loaderMap.get("B"));
        runCMethod(loaderMap.get("C"));
        runCommonMethod(loaderMap.get("COMMON"));
        //
        System.out.println("休眠10秒。然后重新更新C模块");
        Thread.sleep(10000);
        System.out.println("正在更新C模块");
        loader.hotLoader(loaderMap,"C","D:\\ideaworkspace\\Wlib\\jar-depence\\w-C\\target\\w-C-1.4-SNAPSHOT-jar-with-dependencies.jar");
        //
        ClassLoader newClassLoader = loader.load("D:\\ideaworkspace\\Wlib\\jar-depence\\w-C\\target\\w-C-1.5-SNAPSHOT-jar-with-dependencies.jar");
        //
        System.out.println("正在执行替换后C模块");
        runCMethod(loaderMap.get("C"));
        System.out.println("加载另一个C模块");
        runCMethod(newClassLoader);
        Thread.sleep(10000);
    }

    public static void runAMethod(ClassLoader loader) throws Exception {
        Class clz = loader.loadClass("com.weifuchow.a.AModuleInfo");
        Object instance = clz.newInstance();
        Method method = clz.getMethod("print");
        method.invoke(instance,null);
        System.out.println(clz);
    }

    public static void runBMethod(ClassLoader loader) throws Exception {
        Class clz = loader.loadClass("com.weifuchow.b.BModuleInfo");
        Object instance = clz.newInstance();
        Method method = clz.getMethod("print");
        method.invoke(instance,null);
        System.out.println(clz);
    }

    public static void runCommonMethod(ClassLoader loader) throws Exception {
        Class clz = loader.loadClass("com.weifuchow.commons.SourceType");
        for (int i = 0; i < clz.getEnumConstants().length ; i++) {
            System.out.println(clz.getEnumConstants()[i]);
        }
    }

    public static void runCMethod(ClassLoader loader) throws Exception {
        Class clz = loader.loadClass("com.weifuchow.c.CModuleInfo");
        Object instance = clz.newInstance();
        Method method = clz.getMethod("print");
        method.invoke(instance,null);
        System.out.println(clz);
    }

}
