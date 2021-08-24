package com.weifuchow.jdk.learn.juc;


import java.util.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteListTest {

    public static void main(String[] args) {
        CopyOnWriteListTest solution = new CopyOnWriteListTest();
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        solution.test(list);
        // ArrayList list = new ArrayList();
        // solution.test(list);

        // 总结使用CopyOnWirteList 更新操作不影响写。因为都是copy 副本。下一次使用。volatile 只能保证下一次再从list中获取最新。
        // 但是已经在持有旧引用的值并不会去更新。
    }

    public void test(List list){
        for (int i = 0; i < 100 ; i++) {
            list.add(" i => " + i);
        }
        //
        new Thread(()->write(list)).start();
        new Thread(()->read(list)).start();

    }

    // 10 个线程 一个读

    public void read(List list){

       list.forEach((obj)->{
           System.out.println(obj);
           try {
               Thread.sleep(20);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       });
    }

    public synchronized void syncWrite(List list)  {
        for (int i = 0; i < 100; i++) {
            list.remove(i);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(List list) {
        for (int i = 0; i < 100; i++) {
            list.remove(i);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
