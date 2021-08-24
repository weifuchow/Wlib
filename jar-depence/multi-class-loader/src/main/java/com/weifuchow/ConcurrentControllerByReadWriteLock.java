package com.weifuchow;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用计数器实现
 *  要求，
 *      1.当存在有其他线程使用着classloader则不更新。直到为使用完成。才能更新
 *      2.更新时，直到更新完成。其他线程获取会一直阻塞直到更新完成。
 * @author: weifuchow
 * @date: 2021/6/8 14:46
 */
public class ConcurrentControllerByReadWriteLock extends Loader {


    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    //获得读锁
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    //获得写锁
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


    @Override
    public ClassLoader getLoader(String name) {
        try {
            readLock.lock();
            ClassLoader loader =  super.getLoader(name);
            readMethod(loader);
            return loader;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return null;
    }

    @Override
    public void updateLoader(String name, String newPath) throws Exception {
        try {
            writeLock.lock();
            System.out.println("正在替换");
            super.updateLoader(name, newPath);
            Thread.sleep(3000);
            System.out.println("替换完成！");
        }finally {
            writeLock.unlock();
        }
    }


    public void readMethod(ClassLoader loader) throws Exception {
        Class clz = loader.loadClass("com.weifuchow.c.CModuleInfo");
        Object instance = clz.newInstance();
        Method method = clz.getMethod("print");
//        System.out.println(Thread.currentThread());
        method.invoke(instance,null);
        Thread.sleep(5000);
    }

}
