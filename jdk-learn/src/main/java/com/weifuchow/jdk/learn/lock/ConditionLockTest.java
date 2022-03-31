package com.weifuchow.jdk.learn.lock;


import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionLockTest {

    private  static ReentrantLock lock = new ReentrantLock(true);

    public static synchronized void main(String[] args) {

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
             threads[i] = new Thread(ConditionLockTest::readData);
             threads[i].start();
        }


    }


    public static void readData(){
        try {
            lock.lock();
            System.out.println(Thread.currentThread()+"--" + LocalDateTime.now());
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
