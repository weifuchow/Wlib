package com.weifuchow.disruptor.queue;

import com.weifuchow.disruptor.queue.impl.DisruptorQueue;
import com.weifuchow.disruptor.queue.impl.JVMBlockingQueue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/27 10:15
 */
public class Main {

    public  static  long BEGIN = System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException {
        long begin = System.currentTimeMillis();
        IQueue queue = new JVMBlockingQueue();
//        IQueue queue = new DisruptorQueue();
        testQueue(queue,16,16,3000000);

    }

    public static String getRunTime(){
        return System.currentTimeMillis() - BEGIN + " ms ";
    }

    public static void testQueue(IQueue queue,int producerSize,int consumerSize,int eachProducerCount) throws InterruptedException {

        long now = System.currentTimeMillis();

        ExecutorService producerThreads = Executors.newFixedThreadPool(producerSize, new ThreadFactory() {
            int count = 0;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"producer-"+ (++count));
            }
        });
//        ExecutorService consumerThreads = Executors.newFixedThreadPool(consumerSize);
        AtomicInteger messageCounter = new AtomicInteger(0);

        CountDownLatch latch = new CountDownLatch(producerSize * eachProducerCount);
        for (int i = 0; i < producerSize * eachProducerCount; i++) {
            CountDownLatch finalLatch = latch;
            producerThreads.submit(() -> {
                try{
                    queue.send(generateMessage(messageCounter));
                }finally {
                    finalLatch.countDown();
                }
            });
        }

        latch.await();
        System.out.println("producer size => " + (consumerSize*eachProducerCount) + ",use time => " + (System.currentTimeMillis() - now) + " ms");
        //
//        now = System.currentTimeMillis();
//        latch = new CountDownLatch(producerSize * eachProducerCount);
//        for (int i = 0; i < consumerSize * eachProducerCount; i++) {
//            CountDownLatch finalLatch1 = latch;
//            consumerThreads.submit(() ->  {
//                try{
//                    Message message = queue.get();
////                    System.out.println(message);
//                }finally {
//                    finalLatch1.countDown();
//                }
//            });
//        }
//        latch.await();
//        System.out.println("consumer size => " + (consumerSize*eachProducerCount) + ",use time => " + (System.currentTimeMillis() - now) +" ms");


    }

    public static Message generateMessage(AtomicInteger counter){
        int count = counter.incrementAndGet();
        return new Message("name-"+count,"content-"+count);
    }

}
