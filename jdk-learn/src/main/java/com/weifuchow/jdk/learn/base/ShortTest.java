package com.weifuchow.jdk.learn.base;

import cn.hutool.core.util.RandomUtil;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/11 11:52
 */
public class ShortTest {

    public static void main(String[] args) {
        System.out.println(Short.valueOf((short) RandomUtil.randomInt(65535)));
        System.out.println(Byte.valueOf((byte) RandomUtil.randomInt(255)));
        System.out.println((byte)(int)new Integer(12));

        System.out.println(Byte.valueOf("127"));

        System.out.println(RandomUtil.randomLong());

    }


}
