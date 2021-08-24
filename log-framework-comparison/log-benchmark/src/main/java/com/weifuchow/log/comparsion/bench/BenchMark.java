package com.weifuchow.log.comparsion.bench;

import cn.hutool.core.util.RandomUtil;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/18 10:38
 */
public class BenchMark {


    MetricRegistry metrics = new MetricRegistry();
    Timer callTimer = metrics.timer("-timer-test");
    Logger logger = LoggerFactory.getLogger(BenchMark.class);
    AtomicInteger atomicInteger = new AtomicInteger(0);
    ExecutorService service = new ThreadPoolExecutor(16,
            16, 10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "benchmark-" + atomicInteger.incrementAndGet());
                }
            }
    );


    public void mark(int counter,int contentLength) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(counter);
        //
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(2,TimeUnit.SECONDS);
        //
        for (int i = 0; i < counter; i++) {
            service.submit(() -> task(contentLength,latch));
        }
        latch.await();
        reporter.report();
        reporter.stop();
        service.shutdown();
    }

    private void task(int contentLength,CountDownLatch latch){
        try(Timer.Context context = callTimer.time()) {
            logger.info(RandomUtil.randomString(contentLength));
        }finally {
            latch.countDown();
        }
    }

}
