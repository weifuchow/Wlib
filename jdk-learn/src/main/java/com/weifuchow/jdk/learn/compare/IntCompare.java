package com.weifuchow.jdk.learn.compare;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/20 17:18
 */
public class IntCompare {
    public static void main(String[] args) {
        int s = 180;
        int f = 120;
        if(s > f){
            System.out.println("ss");
        }else{
            System.out.println("dd");
        }

        //
        Integer i = new Integer(2000);
        int j = 2000;
        System.out.println(i == j);//返回false

    }
}
