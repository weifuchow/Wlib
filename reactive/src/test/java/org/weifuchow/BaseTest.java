package org.weifuchow;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.Test;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/1/22 16:05
 */
public class BaseTest {

    @Test
    public void testBlockingFlow() throws InterruptedException {
        Flowable.range(1, 10)
                .observeOn(Schedulers.computation())
                .map(v -> {
                    int result = v * v;
                    System.out.println(Thread.currentThread() + " \t" + result);
                    return  result;
                })
                .blockingSubscribe(System.out::println);

        Thread.sleep(1000);
    }


    @Test
    public void testParallelFlow() {
        Flowable.range(1, 10)
                .flatMap(v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> {
                                    int result = w * w;
                                    System.out.println(Thread.currentThread() + "\t" + result);
                                    return result;
                                })
                )
                .blockingSubscribe((data) -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + data);
                });
    }


    @Test
    public void ParallelFlowOneByOne(){
        //  concatMap 流任务（map计算 -> subscibe 处理）一个接一个
        Flowable.range(1, 10)
                .concatMap(v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> {
                                    int result = w * w;
                                    System.out.println(Thread.currentThread() + "\t" + result);
                                    return result;
                                })
                )
                .blockingSubscribe((data) -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + data);
                });

    }

    @Test
    public void ParallelFlowforallFlow(){
        //  它“一次”运行所有内部流，但输出流将按照内部流创建的顺序运行。
        Flowable.range(1, 10)
                .concatMapEager (v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> {
                                    int result = w * w;
                                    System.out.println(Thread.currentThread() + "\t" + result);
                                    return result;
                                })
                )
                .blockingSubscribe((data) -> {
                    System.out.println(Thread.currentThread().getName() + "\t" + data);
                });
    }


    @Test
    public void ParallelFlowforallFlow1() throws InterruptedException {
        //  它“一次”运行所有内部流，但输出流将按照内部流创建的顺序运行。
        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> {
                    int result = v * v;
                    System.out.println(Thread.currentThread() + " \t" + result);
                    return  result;
                })
                .sequential()
                .blockingSubscribe(System.out::println);

        Thread.sleep(1000);
    }

}



