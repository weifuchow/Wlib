package com.weifuchow.jdk.learn.timer;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.system.SystemUtil;
import com.google.common.collect.Queues;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Desc GuavaBatchExecute
 * @Author zhouweifu
 * @Date 2022/3/31
 */
public class GuavaBatchExecute {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        int queueBatchSize = 200;
        int timeout = 200;

        // write thread
        new Thread(() -> {
            for (int i = 0; i < 400; i++) {
                try {
                    blockingQueue.put(i + RandomUtil.randomString(100));
                    if (i > 200){
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // read thread
        while (true) {
            ArrayList list = new ArrayList<String>();
            long now = System.currentTimeMillis();
            Queues.drain(blockingQueue, list, queueBatchSize, timeout, TimeUnit.MILLISECONDS);
            System.out.println("use => " + (System.currentTimeMillis() - now) + " size => " + list.size());
        }


    }
}
