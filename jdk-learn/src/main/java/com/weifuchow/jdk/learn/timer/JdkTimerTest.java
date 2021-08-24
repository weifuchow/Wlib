package com.weifuchow.jdk.learn.timer;


import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class JdkTimerTest {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        // 单位为猫喵
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " -- " + LocalDateTime.now() + " -- 11");
            }
        },1000);
        System.out.println(LocalDateTime.now() + " start ");

//        DelayQueue queue = new DelayQueue();
//        queue.add(new Delayed() {
//            @Override
//            public long getDelay(TimeUnit unit) {
//                return 10;
//            }
//
//            @Override
//            public int compareTo(Delayed o) {
//                return 0;
//            }
//        })

    }
}
