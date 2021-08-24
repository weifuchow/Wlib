package com.weifuchow.jdk.learn.threads;


public class ThreadLocalLeek {

    // -XX:+PrintGCDetails
    public static void main(String[] args) throws InterruptedException {
        // Thread local 泄漏问题是因为这个是因为localMap中 ThreadLocal 被回收。且线程对象还在。
        // 而map中的节对应value 还在。key 已经被回收。

        // thread local 的模型是
        // thread 对象 包含有 thread local map ,初始值为空
        // threadLocal 进行操作时会获取当前线程对象，转而去能够获取并创建thread local map.
        // thread local map 中 key 为 thread local 弱引用对象，
        // 那么获取值时，就可以通过thread local 去获取线程对应的map。从而得到值或者进行操作等。

        // 设计。threadlocalMap 为弱引用是因为.threadlocal 置位空。threadlocal entry 缺还没有被回收掉。
        // 如果不为弱引用，那么直到线程结束，entry 也不会被回收。
        // memory leek 原因： threadlocal 被回收时，threadlocalmap中对应的entry
        ThreadLocal local = new ThreadLocal();
        local.remove();
        local.set(new Byte[1024 * 1024 * 10]);
        Thread.sleep(10000);
        System.gc();
        System.out.println("回收完成");
        Thread.sleep(1000000);
    }
}
