package com.weifuchow.leecode.threads;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultiThreadPrintOrder {

    private volatile int nextState = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition firstConndition = lock.newCondition();
    private Condition secondCondition = lock.newCondition();
    private Condition thirdCondition = lock.newCondition();

    public void first(Runnable printFirst) throws InterruptedException {

        // printFirst.run() outputs "first". Do not change or remove this line.

        try{
            lock.lock();
            if (nextState != 1){
                firstConndition.await();
            }
            printFirst.run();
            nextState = 2;
            secondCondition.signal();
        }finally {
            lock.unlock();
        }

    }

    public void second(Runnable printSecond) throws InterruptedException {

        // printSecond.run() outputs "second". Do not change or remove this line.
        try{
            lock.lock();
            if (nextState != 2){
                secondCondition.await();
            }
            printSecond.run();
            nextState = 3;
            thirdCondition.signal();
        }finally {
            lock.unlock();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {

        // printThird.run() outputs "third". Do not change or remove this line.
        try{
            lock.lock();
            if (nextState != 3){
                thirdCondition.await();
            }
            printThird.run();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MultiThreadPrintOrder solution = new MultiThreadPrintOrder();
        Print a = new Print("first");
        Print b = new Print("second");
        Print c = new Print("third");

        // 无论什么顺序执行都要保证first先执行
        ReentrantLock lock = new ReentrantLock();
        new Thread(()-> {
            try {
                solution.third(c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()-> {
            try {
                solution.second(b);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(()-> {
            try {
                solution.first(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();




    }

    public static class Print implements  Runnable{
        String word ;
        Print(String word){
            this.word = word;
        }

        @Override
        public void run() {
            System.out.println(word);
        }
    }

}
