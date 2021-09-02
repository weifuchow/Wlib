package com.weifuchow.leecode.threads;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AlternatelyPrintFooBar {

    public static void main(String[] args) throws InterruptedException {

        FooBar fooBar = new FooBar(10);
        Print foo = new Print("foo");
        Print bar = new Print("bar");




        new Thread(()-> {
            try {
                fooBar.foo(foo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        new Thread(()-> {
            try {
                fooBar.bar(bar);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }


    public static class FooBar {
        private int n;

        public FooBar(int n) {
            this.n = n;
        }

        private volatile int state = 1;
        ReentrantLock lock = new ReentrantLock();
        Condition fooCondition = lock.newCondition();
        Condition barCondition = lock.newCondition();


        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {

                // printFoo.run() outputs "foo". Do not change or remove this line.
                try{
                    lock.lock();
                    if (state != 1) {
                        fooCondition.await();
                    }
                    printFoo.run();
                    state = 2;
                    barCondition.signal();
                }finally {
                    lock.unlock();
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                // printBar.run() outputs "bar". Do not change or remove this line.
                try{
                    lock.lock();
                    if (state != 2) {
                        barCondition.await();
                    }
                    printBar.run();
                    state = 1;
                    fooCondition.signal();
                }finally {
                    lock.unlock();
                }
            }
        }
    }


    public static class Print implements Runnable{
        String word ;
        Print(String word){
            this.word = word;
        }

        @Override
        public void run() {
            System.out.print(word);
        }
    }
}
