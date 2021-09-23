package com.weifuchow.jdk.learn.timer;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 微批处理;1000条数据。或者100
 * 多线程生产。单线程消费。
 */
public class BatchExecute {

    private int roundSize;
    private long timeLimit;
    private Queue queue = new ConcurrentLinkedDeque();
    private AtomicInteger counter = new AtomicInteger(0);
    private AtomicInteger canConsumerCounter = new AtomicInteger(0);
    private Thread readThread = null;

    public BatchExecute(int roundSize, int timeLimit) {
        this.roundSize = roundSize;
        this.timeLimit = timeLimit;
    }


    public List<String> getData() {
        // 延时队列是一个实时put进去，取数时阻塞100ms.取出数据
        // 阻塞100ms.或者当达到条件时触发。
        readThread = Thread.currentThread();
        if (canConsumerCounter.decrementAndGet() < 0) {
            LockSupport.parkNanos(timeLimit * 1000000);
        }
//        LockSupport.parkNanos(timeLimit * 1000000);
        List list = new ArrayList();
        int size = Math.min(queue.size(), roundSize);
        for (int i = 0; i < size; i++) {
            list.add(queue.poll());
        }
        return list;
    }

    // 线程在读取
    public void putData(String data) {
        queue.add(data);
        if (counter.incrementAndGet() % 1000 == 0) {
            canConsumerCounter.incrementAndGet();
            System.out.println("finish = > " + counter.get());
            LockSupport.unpark(readThread);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        BatchExecute queue = new BatchExecute(1000, 1000);

        // Write Thread
        new Thread(() -> {
            long begin = System.currentTimeMillis();
            for (int i = 0; i < 50000; i++) {
                queue.putData("str = > " + i);
            }
            System.out.println((System.currentTimeMillis() - begin) + " --  put end ");
        }).start();

        // Read Thread
        new Thread(() -> {
            for (int i = 0; i < 52; i++) {
                long begin = System.currentTimeMillis();
                List list = queue.getData();
                System.out.println("getData = > ,use time [" + (System.currentTimeMillis() - begin) + " ms] -> " + list);
            }
        }).start();


        Thread.sleep(5000);
    }

}
