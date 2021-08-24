package com.weifuchow.sort;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @desc: 冒泡排序
 * @author: weifuchow
 * @date: 2021/6/1 16:53
 */
public class BubbleSort implements ISort {

    @Override
    public int[] sort(int[] arrays) {
        // 先局部目标：通过两两比较找到最大的值，然后并交换到最后一位.如果前一个数比第二个数大，就交换。
        // 到下次循环中。这个数再与后一个进行比较。重复此。直到最大的数被交换到最后
        for (int round = 1; round < arrays.length; round++) {
            for (int i = 0; i < arrays.length - round; i++) {
                if (arrays[i] > arrays[i + 1]) {
                    SortHelper.swap(arrays, i, i + 1);
                }
            }
        }

        return arrays;
    }
}
