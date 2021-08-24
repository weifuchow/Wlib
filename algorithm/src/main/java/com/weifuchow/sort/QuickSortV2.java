package com.weifuchow.sort;

import java.util.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * @desc: 辅助空间实现，三部分分割
 * @author: weifuchow
 * @date: 2021/6/11 9:53
 */
public class QuickSortV2 implements ISort {
    @Override
    public int[] sort(int[] arrays) {
        quickRecursion(arrays, 0, arrays.length - 1);
        return arrays;
    }


    public void quickRecursion(int[] arrays, int left, int right) {
        if (left >= right) {
            return;
        } else {
            int[] positions = splitThreeArea(arrays, left, right);
            quickRecursion(arrays, left, positions[0] - 1);
            quickRecursion(arrays, positions[1], right);
        }
    }

    /** 利用辅助数据
     * @param arrays
     * @param left
     * @param right
     * @return 分割点位置
     */
    public int[] splitThreeArea(int[] arrays, int left, int right) {
        // 随机快排
        List<Integer> leftHelper = new ArrayList<>();
        List<Integer> rightHelper = new ArrayList<>();
        List<Integer> middleHelper = new ArrayList<>();

        int postition = arrays[left];
        //
        for (int i = left ; i <= right; i++) {
            if (arrays[i] < postition) {
                leftHelper.add(arrays[i]);
            } else if (arrays[i] == postition) {
                middleHelper.add(arrays[i]);
            } else {
                rightHelper.add(arrays[i]);
            }
        }
        //
        int i = left;
        for (int j = 0; j < leftHelper.size(); j++) {
            arrays[i++] = leftHelper.get(j);
        }
        //
        for (int j = 0; j < middleHelper.size(); j++) {
            arrays[i++] = middleHelper.get(j);
        }
        //
        for (int j = 0; j < rightHelper.size(); j++) {
            arrays[i++] = rightHelper.get(j);
        }
//        System.out.println(Arrays.toString(arrays));
        return new int[]{left + leftHelper.size(), left + leftHelper.size()+middleHelper.size()};
    }


    public static void main(String[] args) {
        QuickSortV2 v2 = new QuickSortV2();
        int[] arrays = new int[]{1, 4, 6, 8, 1, 1, -2, -1, 2, 3, 0, 9};
        v2.sort(arrays);
        System.out.println(Arrays.toString(arrays));
    }

}
