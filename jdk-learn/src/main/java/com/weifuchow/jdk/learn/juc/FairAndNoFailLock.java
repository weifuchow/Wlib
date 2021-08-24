package com.weifuchow.jdk.learn.juc;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairAndNoFailLock {

    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);
        FairAndNoFailLock solution = new FairAndNoFailLock();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> solution.task(lock), "thread - " + (i + 1)).start();
        }
    }


    public void task(Lock lock) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " -- " + atomicInteger.incrementAndGet());
        } finally {
            lock.unlock();
        }
    }


}
