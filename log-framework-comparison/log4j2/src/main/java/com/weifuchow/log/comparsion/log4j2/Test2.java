package com.weifuchow.log.comparsion.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/19 16:11
 */
public class Test2 {

    static Logger logger = LoggerFactory.getLogger(Test2.class);
    public static void main(String[] args) throws InterruptedException {

        logger.info("fuck test");
//        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//        Configuration config = ctx.getConfiguration();
//        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
//        loggerConfig.setLevel(Level.TRACE);
//        ctx.updateLoggers();

        logger.debug("fuck test");
        System.setProperty("level","error");
        long now = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 100; j++) {
                logger.info((i*10000 + j) + "  = > fufufufufuf");
                System.out.println(i*1000 + j);
            }
            System.out.println("time = " + i);
//            Thread.sleep(100000);
        }
        System.out.println("execue time = >" + (System.currentTimeMillis() - now));

    }
}
