package com.weifuchow.jdk.learn.future;

import cn.hutool.core.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright © 2016-2021 Stander robots RIOT platform
 *
 * @Desc CompleteFutureTest
 * @Author zhouweifu
 * @Date 2022/1/19
 */

public class CompleteFutureTest {

    private static final Logger LOG = LoggerFactory.getLogger(CompleteFutureTest.class);
    private static final AtomicInteger ai = new AtomicInteger(0);
    private static final ExecutorService pool = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {

        CompletableFuture[] futures = new CompletableFuture[10];
        for (int i = 0; i < 10; i++) {
            futures[i] = CompletableFuture.runAsync(() -> CompleteFutureTest.task(ai.incrementAndGet()), pool);
        }
        for (int i = 0; i < futures.length; i++) {
            futures[i].join();
        }
        LOG.info("execute all finished!!");
    }


    // mock 耗时任务
    public static void task(int id) {
        int random = RandomUtil.randomInt(5000);
        LOG.info("execute task id {} time {} ms...", id, random);
        try {
            Thread.sleep(random);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.info("execute task id {}  finished...", id);
    }
}
