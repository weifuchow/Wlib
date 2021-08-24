package com.weifuchow.jdk.learn.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Copyright © weifuchow., Ltd. . .
 * @desc: 验证是否上次任务未执行完，仍会继续执行
 * @author: weifuchow
 * @date: 2021/5/11 15:50
 */
public class ScheduledExecutorTest {

    public static void main(String[] args) {
        // 定时器
        // 上次任务未执行完。则会阻塞等待。
        ScheduledExecutorService monitorScheduledService = Executors.newScheduledThreadPool(10);
        monitorScheduledService.scheduleAtFixedRate(()-> task(), 1,
                100, TimeUnit.MILLISECONDS);
    }

    public static void task(){
        System.out.println(Thread.currentThread().getName() + " sleeping  5000 ms");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " finished");

    }

}
