package com.weifuchow.jdk.learn.finallyy;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/4/19 17:34
 */
public class Test {

    public static void main(String[] args) throws Exception {
        try(FuckResource resouce = get()){
            System.out.println("hello");
            System.out.println("ff");
        }
    }

    public static  FuckResource get() throws Exception {
        try(FuckResource resouce = new FuckResource()){
            return resouce;
        }
    }

}
