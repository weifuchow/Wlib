package com.weifuchow.jdk.learn.string;

import java.util.Arrays;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/3/10 16:22
 */
public class SplitTest {

    public static void main(String[] args) {
        String test1 = "Fuck.you";
        String test2 = "Fuck|you";
        String test3 = "Fuck\\you";
        String test4 = "\\";

        // regex 正则表达式。 . | 在代表有特殊的含义，所以需要转移
        // . 任意一个字符
        // | 用来连接两个表达式,表示或的关系
        // \ 转义字符,当一个符号自身有意义而又要表示这个字符的时候,就需要转,又因为java \\ 代表一个字符 \
        System.out.println(Arrays.toString(test1.split(".")));
        System.out.println(Arrays.toString(test1.split("\\.")).toString());

        System.out.println(Arrays.toString(test2.split("|")).toString());
        System.out.println(Arrays.toString(test2.split("\\|")).toString());

        System.out.println(Arrays.toString(test3.split("\\\\")).toString());


    }
}
