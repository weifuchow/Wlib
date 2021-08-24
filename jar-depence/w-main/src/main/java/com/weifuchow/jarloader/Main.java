package com.weifuchow.jarloader;

import com.weifuchow.commons.Module;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/25 15:41
 */
public class Main {

    public static void main(String[] args) throws Exception {

        URL[] urls =  new URL[args.length];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = new File(args[i]).toURL();
            System.out.println("jar path:" + urls[i]);
        }
        //
        URLClassLoader classLoader = new URLClassLoader(urls);
        //


        
        // com.weifuchow.a.AModuleInfo
        // com.weifuchow.b.BModuleInfo
        // com.weifuchow.c.CModuleInfo
        Class<Module> clazz = (Class<Module>) classLoader.loadClass("com.weifuchow.a.AModuleInfo");
        Module modulea = clazz.newInstance();
        modulea.print();



        Class<Module> clazz1 = (Class<Module>) classLoader.loadClass("com.weifuchow.b.BModuleInfo");
        Module moduleb = clazz1.newInstance();
        moduleb.print();

        Class<Module> clazz2 = (Class<Module>) classLoader.loadClass("com.weifuchow.c.CModuleInfo");
        Module modulec = clazz2.newInstance();
        modulec.print();

    }
}
