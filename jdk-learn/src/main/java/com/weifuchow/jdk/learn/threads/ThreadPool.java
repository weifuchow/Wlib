package com.weifuchow.jdk.learn.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/10 15:36
 */
public class ThreadPool {

    private static Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    public static void main(String[] args) {
        // 4个核心线程，最大线程8，keepAliveTime&TimeUnit:额外的线程空闲多少秒就会被回收，（使用queue.poll(time,unit)实现）
        // 阻塞队列,线程工厂
        // 前置worker 过程：worker 通过task是否为空，判断是否为核心线程。若在task不为空。直接执行task.若为空。则从队列里面获取queue.poll(time,unit)。并执行
        // 预测执行过程：1.先创建4个核心线程，并创建worker。核心线程满了之后，
        //              2.【若队列不满，放入队列之中，并创建worker（worker 在队列里边获取任务。
        //3.若队列已满，（若运行的线程并未超过最大线程，则代表之前的任务再这个时刻可能已经执行完毕。有空余出来。若已满最大线程。则执行拒绝策略）

        // 任务每次执行需要消耗20s,假设1s内全部进入开始执行。此时4个核心线程在跑。4个非核心线程，此时队列长度为，101-4 = 97,仍然未完。
        // 有个问题：队列未满时，并不会开启非核心线程Why
//        testPoolByTaskTimes(100,100);

        // 任务每次执行需要消耗20s,假设1s内全部进入开始执行。此时4个核心线程在跑。4个非核心线程，此时队列长度为，108-8 = 100,队列已满
        // 达到临界点
//        testPoolByTaskTimes(108,100);

        // 任务每次执行需要消耗20s,假设1s内全部进入开始执行。此时4个核心线程在跑。4个非核心线程，此时队列长度为，108-8 = 100,队列已满
        // 下一个任务
        testPoolByTaskTimes(109,100);


    }


    public static void testPoolByTaskTimes(int worktimes,int queueSize){
        AtomicInteger counter = new AtomicInteger();
        ExecutorService executorService = new ThreadPoolExecutor(4, 8, 2, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r,"AsyncJDBCCommonScript-"+counter.incrementAndGet());
                    }
                });

        for (int i = 0; i < worktimes; i++) {
            try{
                int finalI = i;
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        logger.info(finalI + " this thread execute prepare ");
                        try {
                            Thread.sleep(200000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        logger.info(finalI + "this thread execute complete");

                    }

                    @Override
                    public String toString() {
                        return "task-" + finalI;
                    }
                });
            }catch (Exception e){
                logger.error((i + 1) + " execute error",e);
            }

        }
    }

}
