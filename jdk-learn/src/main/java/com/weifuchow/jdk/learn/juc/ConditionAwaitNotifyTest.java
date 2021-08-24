package com.weifuchow.jdk.learn.juc;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionAwaitNotifyTest {


    private static Lock lock = new ReentrantLock();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();
    private static Condition conditionD = lock.newCondition();

    public static void main(String[] args) {
        // 多线程通知唤醒。
        // 精确通知、唤醒、条件唤醒、await释放锁。single唤醒时重新获得锁
        ConditionAwaitNotifyTest test = new ConditionAwaitNotifyTest();
        new Thread(()-> test.printA()).start();
        new Thread(()-> test.printB()).start();
        new Thread(()-> test.printC()).start();
        new Thread(()-> test.printD()).start();

    }

    public void printA(){
        try{
            Thread.sleep(1000);
            lock.lock();
            for (int i = 0; i < 10 ; i++) {
                System.out.println("AAAAAAAAAAAAA");
            }
            conditionB.signal();
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println("释放锁");
        }
    }

    public void printB()  {
        try{
//            Thread.sleep(1000);
            lock.lock();
            // 没人唤醒他。
            conditionB.await();
            for (int i = 0; i < 10 ; i++) {
                System.out.println("BBBBBBBBBBB");
            }
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        try{
            lock.lock();
            conditionC.await();
            for (int i = 0; i < 100 ; i++) {
                System.out.println("CCCCCCCCCC");
            }
            conditionD.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void printD() {
        try{
            lock.lock();
            conditionD.await();
            for (int i = 0; i < 100 ; i++) {
                System.out.println("DDDDDDDDD");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
