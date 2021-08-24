package com.weifuchow.jdk.learn.threads;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/3/27 14:07
 */
public class VolatileTest {
    private static  boolean flag = true;
    static AtomicInteger integer = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (flag){
                        integer.incrementAndGet();
                    }
                    System.out.println(Thread.currentThread().getName() + " sub thread flag = false,will be complete " + integer.get());
                }
            }).start();
        }

        Thread.sleep(1000);
        flag = false;
        System.out.println("main thread flag setting false,");
    }
}
