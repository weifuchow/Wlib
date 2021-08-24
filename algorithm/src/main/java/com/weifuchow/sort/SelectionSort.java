package com.weifuchow.sort;

import java.util.Arrays;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/6/1 16:08
 */
public class SelectionSort implements ISort{


    // select 优化，同时找出最大最小的值在一趟排序中。只需要/2即可。
    @Override
    public int[] sort(int[] arrays) {
        for (int round = 0; round < arrays.length ; round++) {
            // 选择排序。选择最小于最前交换
            int minIndex = round;
            for (int i = round + 1; i < arrays.length; i++) {
                if (arrays[minIndex] > arrays[i]) {
                    minIndex = i;
                }
            }
            SortHelper.swap(arrays, minIndex, round);
        }
        return arrays;
        // 0 ~ N-1
        // 1 ~ N-1
    }

    public static void main(String[] args) {
        int[] arrays = new int[]{1,3,5,2,6,8,1,2,-2};
        SelectionSort sort = new SelectionSort();
        System.out.println(Arrays.toString(sort.sort(arrays)));
    }


}
