package com.weifuchow.log.comparsion.log4j2;

import cn.hutool.core.util.RandomUtil;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/9 11:24
 */
public class TestJson {


    private static Logger logger = LoggerFactory.getLogger(TestJson.class);

    public static void main(String[] args) throws InterruptedException {
        // -DisProd=true -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector -Dfile.encoding=UTF-8
        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());
        int total = 1000; //100w
        JsonOutPutTest jsonOut  = new JsonOutPutTest();
        Thread thread1 = new Thread(() -> jsonOut.print(total),"jsonOutPutThread");
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < total ; i++) {
                logger.info(RandomUtil.randomString(10)+"我勒个去");
            }
        },"commonOutPutThread");

        Long time = System.currentTimeMillis();
        //
        thread1.start();
        thread2.start();
        //
        thread1.join();
        thread2.join();
        //
        logger.info("use time ={}",System.currentTimeMillis() - time);
        System.out.println("use time = " + (System.currentTimeMillis() - time));

        // 没有设置异步打印 29598ms
        // 设置异步情况  24198ms up了1/6
    }
}
