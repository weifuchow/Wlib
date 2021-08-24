package com.weifuchow.jdk.learn.juc;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReadWriteLockTest solution = new ReadWriteLockTest();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock =  readWriteLock.readLock();
        Lock writeLock =  readWriteLock.writeLock();
        for (int i = 0; i < 10; i++) {
            new Thread(()-> solution.readTask(readLock)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(()-> solution.writeTask(writeLock)).start();
        }
    }

    public void readTask(Lock readLock){
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() +" -- read");
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() +" -- read finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
    }


    public void writeTask(Lock writeLock){
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() +" -- write");
            Thread.sleep(100);
            System.out.println(Thread.currentThread().getName() +" -- write finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

}
