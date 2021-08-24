package com.weifuchow.jdk.learn.threads;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {

    @Test
    // 实际都是基于阻塞队列。readlock ，wirtelock 排队。当第一个writelock 未释放时，writelock 就只能等待。
    // 当readlock 重入是时，counter ++.不需要进行检即可执行。
    // 注意：counter要释放到0
    public void testReadWriteRead() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Runnable read2Task = new Runnable() {
            @Override
            public void run() {
                ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
                try{
                    readLock.lock();
                    Thread.sleep(3000);
                    System.out.println("read lock 1 doing work");
                    readLock.lock();
                    System.out.println("read lock 2 doing work");
                    readLock.unlock();
                }catch (Exception e ){
                    e.printStackTrace();
                }finally {
                    readLock.unlock();
                }
            }
        };
        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
                try{
                    Thread.sleep(1000);
                    writeLock.lock();
                    System.out.println("write lock 1 doing work");
                }catch (Exception e ){
                    e.printStackTrace();
                }finally {
                    writeLock.unlock();
                }
            }
        };

        new Thread(read2Task).start();
        new Thread(writeTask).start();
        Thread.sleep(50000);
    }

    @Test
    public void testNotReentrant() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Runnable readTask = new Runnable() {
            @Override
            public void run() {
                ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
                try{
                    readLock.lock();
                    Thread.sleep(3000);
                    System.out.println(readLock.toString());
                    System.out.println("read lock 1 doing work");
                }catch (Exception e ){
                    e.printStackTrace();
                }finally {
                    readLock.unlock();
                }
            }
        };
        Runnable readTask2 = new Runnable() {
            @Override
            public void run() {
                ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
                try{
                    Thread.sleep(3000);
                    readLock.lock();
                    System.out.println(readLock.toString());
                    System.out.println("read lock 2 doing work");
                }catch (Exception e ){
                    e.printStackTrace();
                }finally {
                    readLock.unlock();
                }
            }
        };

        Runnable writeTask = new Runnable() {
            @Override
            public void run() {
                ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
                try{
                    Thread.sleep(1000);
                    writeLock.lock();
                    System.out.println("write lock 1 doing work");
                    Thread.sleep(5000);
                }catch (Exception e ){
                    e.printStackTrace();
                }finally {
                    writeLock.unlock();
                    System.out.println("write release");
                }
            }
        };

        new Thread(readTask).start();
        new Thread(readTask2).start();
        new Thread(writeTask).start();
        Thread.sleep(50000);
    }



}