package com.weifuchow.jdk.learn.juc;


import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static void main(String[] args) {

        LockSupportTest solution = new LockSupportTest();

        Thread thread1 = new Thread(()->solution.test("AAA"));


        Thread thread2 = new Thread(()->solution.test("BBB"));

        thread1.start();
        thread2.start();


        LockSupport.unpark(thread1);
        LockSupport.unpark(thread2);

    }

    // LockSupport
    public void test(String str){
        if(str.equals("AAA")){
            System.out.println(str);
            LockSupport.park();
            System.out.println(str + "notify");
        }else{
            System.out.println(str);
            LockSupport.park();
            System.out.println(str + "notify");
        }
    }



}
