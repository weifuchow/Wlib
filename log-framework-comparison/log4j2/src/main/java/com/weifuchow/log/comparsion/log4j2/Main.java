package com.weifuchow.log.comparsion.log4j2;

import cn.hutool.http.HttpUtil;
import com.weifuchow.log.comparsion.bench.BenchMark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/18 11:35
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws InterruptedException {
        System.out.println(System.getProperty("isProd"));
        System.setProperty("LOG_APP","log42");
//        System.setProperty("enableAppender2","true");
        System.out.println(System.getProperty("Log4jContextSelector"));

//        System.setProperty("Log4jContextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
        Long now = System.currentTimeMillis();
        BenchMark mark = new BenchMark();
        // 1kb * 2 ^ 20 = 1gb
        mark.mark(10485760,102);
        // 1077 * 1048576 = 1.129316352GB
        System.out.println("total size  = 1077 * 1048576 = 1.129316352GB,total time =  " + (System.currentTimeMillis() - now) + " ms");
        logger.info("total size  = 1077 * 1048576 = 1.129316352GB,total time =  " + (System.currentTimeMillis() - now) + " ms");
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                logger.info("xxxxxxxxxxxxxx--------------sssssssss");
            }
            Thread.sleep(1000);
        }

    }
}
