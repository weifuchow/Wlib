package org.weifuchow;

import io.reactivex.rxjava3.core.Flowable;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/1/22 11:10
 */
public class HelloWorld {

    public HelloWorld(){
        print();
    }
    public static void main(String[] args) {
        Flowable.just("Hello world你好").subscribe(System.out::println);
        System.out.println(new HelloWorld().getClass().getClassLoader());
    }

    public void print(){
        System.out.println("hello world init success");
        System.out.println(this.getClass().getClassLoader());
        Flowable.just("Hello world你好").subscribe(System.out::println);
    }
}
