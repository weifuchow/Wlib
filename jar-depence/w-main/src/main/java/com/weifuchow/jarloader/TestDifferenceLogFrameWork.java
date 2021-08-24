package com.weifuchow.jarloader;

import com.weifuchow.commons.SourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/25 19:30
 */
public class TestDifferenceLogFrameWork {
    private  static Logger logger = LoggerFactory.getLogger(TestDifferenceLogFrameWork.class);
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 1000 ; j++) {
                logger.info("execute main => " + (i)*1000 + j);
            }
            Thread.sleep(5000);
            System.out.println("正在休眠");
            if (i == 0 ){
                String classs = "D:/ideaworkspace/Wlib/jar-depence/w-commons/target/w-commons-1.3-SNAPSHOT-jar-with-dependencies.jar";
                URLClassLoader classLoader = new URLClassLoader(new URL[]{new File(classs).toURL()});
                Class<org.apache.logging.log4j.core.appender.ConsoleAppender> clazz = (Class<org.apache.logging.log4j.core.appender.ConsoleAppender>) classLoader.loadClass("org.apache.logging.log4j.core.appender.ConsoleAppender");
                System.out.println(clazz.getClassLoader());
                Class clazz2 =  classLoader.loadClass("org.apache.log4j.ConsoleAppender");
                System.out.println(clazz2.getClassLoader());
                logger.info("execute ~~");
            }
            Thread.sleep(1000);

        }
    }
}
