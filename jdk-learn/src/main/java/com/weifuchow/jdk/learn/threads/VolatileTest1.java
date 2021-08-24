package com.weifuchow.jdk.learn.threads;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/3/27 14:07
 */
public class VolatileTest1 {

    boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        VolatileTest1 t1 = new VolatileTest1();
        t1.test();
        t1.stop();
    }

    public void test(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    System.out.println(1);
                }
                System.out.println(Thread.currentThread().getName() + " sub thread flag = false,will be complete");
            }
        }).start();
    }


    public void stop() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag = false;
                System.out.println("thread execute stop");
            }
        }).start();
    }
}
