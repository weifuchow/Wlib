package com.weifuchow.log.comparsion.bench;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Copyright © weifuchow., Ltd. . .
 * 测试 统计方法（代码）所执行时间
 * @author: weifuchow
 * @date: 2021/1/13 16:01
 */
public class TimerTest {

    private static final MetricRegistry metrics = new MetricRegistry();
    private static final Timer callTimer = metrics.timer("-timer-test");


    static void startReport()  {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);

    }

    public static void mockMethodCall(){
        try(final Timer.Context context = callTimer.time()) {
            int useTime = new Random().nextInt(2000);
            Thread.sleep(useTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    TimerTest.mockMethodCall();
                }
            }
        }).start();

        startReport();

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
