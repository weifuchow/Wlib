package com.weifuchow.log.comparsion.logback;

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
        Long now = System.currentTimeMillis();
        logger.info("test");
        BenchMark mark = new BenchMark();
        // 1kb * 2 ^ 20 = 1gb
        mark.mark(10485760,102);
        // 1077 * 1048576 = 1.129316352GB
        System.out.println("total size  = 1077 * 1048576 = 1.129316352GB,total time =  " + (System.currentTimeMillis() - now) + " ms");
        logger.info("total size  = 1077 * 1048576 = 1.129316352GB,total time =  " + (System.currentTimeMillis() - now) + " ms");
    }
}
