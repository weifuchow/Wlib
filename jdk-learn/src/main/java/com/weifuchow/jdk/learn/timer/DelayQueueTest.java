package com.weifuchow.jdk.learn.timer;


import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {

    public static void main(String[] args) throws InterruptedException {

        DelayQueue queue = new DelayQueue();
        queue.add(new Data("first",300));
        queue.add(new Data("second",100));


        Thread.sleep(100);
        System.out.println(queue.take().toString());

    }

    public static class Data implements Delayed{

        String val;
        long delay;

        public Data(String val, long delay) {
            this.val = val;
            this.delay = delay + System.currentTimeMillis();
        }

        // 递减 + 固定写法
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(this.delay - System.currentTimeMillis() , TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        @Override
        public String toString() {
            return "Data{" +
                    "val='" + val + '\'' +
                    ", delay=" + delay +
                    '}';
        }
    }
}
