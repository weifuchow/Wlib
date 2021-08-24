package com.weifuchow;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: weifuchow
 * @date: 2021/6/8 16:26
 */
public class ConcurrentTest {

    public static void main(String[] args) throws Exception {
        // 并发测试
        Loader loader = new ConcurrentControllerByReadWriteLock();
        loader.loadJars();
        ExecutorService service = Executors.newFixedThreadPool(16);

        for (int i = 0; i < 100 ; i++) {
            service.submit(() -> read("C",loader));
        }
        // replace
        service.submit(() -> write("C","D:\\ideaworkspace\\Wlib\\jar-depence\\w-C\\target\\w-C-1.5-SNAPSHOT-jar-with-dependencies.jar",loader));
        for (int i = 0; i < 100 ; i++) {
            service.submit(() -> read("C", loader));
        }
        // replace again
        service.submit(() -> write("C","D:\\ideaworkspace\\Wlib\\jar-depence\\w-C\\target\\w-C-1.4-SNAPSHOT-jar-with-dependencies.jar",loader));
        for (int i = 0; i < 100 ; i++) {
            service.submit(() -> read("C", loader));
        }

    }

    public static void read(String name,Loader loader){
       loader.getLoader(name);
    }

    public static void write(String name,String newPath,Loader loader){
        try {
            loader.updateLoader(name,newPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
