package com.weifuchow.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * @desc: 通过不断交换基准值，实现两部分分割。
 * @author: weifuchow
 * @date: 2021/6/11 9:53
 */
public class QuickSortV1 implements ISort {
    @Override
    public int[] sort(int[] arrays) {
        quickRecursion(arrays, 0, arrays.length - 1);
        return arrays;
    }


    public void quickRecursion(int[] arrays, int left, int right) {
        if (left >= right) {
            return;
        } else {
            int position = splitTwoArea(arrays, left, right);
            quickRecursion(arrays, left, position - 1);
            quickRecursion(arrays, position + 1, right);
        }
    }

    /**
     * @param arrays
     * @param left
     * @param right
     * @return 分割点位置
     */
    public int splitTwoArea(int[] arrays, int left, int right) {
        // 随机快排
        int randomIndex = new Random().nextInt(right - left) + left;
        SortHelper.swap(arrays,left,randomIndex);
        //
        while (left < right) {
            // 找到第一个
            for (; arrays[right] > arrays[left] && right > left; right--) {
            }
            // swap
            SortHelper.swap(arrays, right, left);
            // 从后面找，
            for (; arrays[left] <= arrays[right] && right > left; left++) {

            }
            SortHelper.swap(arrays, right, left);
        }
        return right;
    }

}
