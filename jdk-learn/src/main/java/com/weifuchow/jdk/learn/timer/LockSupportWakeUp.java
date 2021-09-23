package com.weifuchow.jdk.learn.timer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class LockSupportWakeUp {


    public static void main(String[] args) throws InterruptedException {

        AtomicInteger counter = new AtomicInteger(0);
        // main 线程多个park
        Thread main = Thread.currentThread();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                counter.incrementAndGet();
                LockSupport.unpark(main);
            }
            System.out.println("finished = > unpark");
        });
        thread.start();
        TimeUnit.SECONDS.sleep(5);
        for (int i = 0; i < 6; i++) {
            if (counter.decrementAndGet() < 0) {
                LockSupport.park();
            }
            System.out.println("wake up times = > " + i);
        }
        System.out.println("wait");
    }


}
