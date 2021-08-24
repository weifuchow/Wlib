package com.weifuchow.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/6/1 17:17
 */
public class QuickSort implements ISort {
    @Override
    public int[] sort(int[] arrays) {
        // 递归过程。recursion
        // 0 ~ 9, 0 ~ 4,5 ~9
        // 0 ~ 10 ,0 5,6 10
        quickRecursion(arrays, 0, arrays.length - 1);
//        System.out.println(Arrays.toString(arrays));
        return arrays;
    }

    public void quickRecursion(int[] arrays, int left, int right) {
//        System.out.println(left + " -- " + right);
        if (left >= right) {
            return;
        } else {
            int[] paths = spiltSortByPartition(arrays, left, right);
            int middleFirstIndex = paths[0];
            int middleRegion = paths[1];
//            System.out.println("分割结果left：" + left + "," + (middleFirstIndex - 1));
//            System.out.println("分割结果right：" + middleRegion + "," + right);

            // left
            quickRecursion(arrays, left, middleFirstIndex - 1);
            // right
            quickRecursion(arrays, middleRegion, right);
        }
    }

    /**
     * 思路，划分成三区域。小于区，等于区，大于区。
     * @param arryas
     * @param left
     * @param right
     * @return
     */
    public int[] spiltSortByPartition(int[] arryas, int left, int right) {
        int partitionIndex = new Random().nextInt(right - left) + left;
        int partitionVal = arryas[partitionIndex];
//        System.out.println("index = > " + partitionIndex + " ,val = > " + arryas[partitionIndex]);
        int middle = 0;
        int firstMiddleIndex = -1;
        int leftBegin = left;
        int rightEnd = right;
        while (left <= right) {
            // 从左开始遍历，当发现左边的值小于 i++,
            if (arryas[left] < partitionVal) {
                if (firstMiddleIndex > -1) {
                    SortHelper.swap(arryas, firstMiddleIndex, left);
                    // 1,2,5,5,5,5,3
                    // 1,2,5,5,5,5,3
                    // middle = 4, firstIndex = 2; lastIndex =  2 + 4 -1;
                    // 1,2,3,5,5,5,5
                    firstMiddleIndex++;
                }
                left++;

            }
            // 当值比right大。则与right值交换。
            else if (arryas[left] > partitionVal) {
                SortHelper.swap(arryas, left, right);
                right--;
            } else {
                // 增加中间区域操作
                if (firstMiddleIndex == -1) {
                    firstMiddleIndex = left;
                }
                middle++;
                left++;
            }
        }
//        int[] middles = Arrays.copyOfRange(arryas,firstMiddleIndex,firstMiddleIndex+middle);
//        int[] lefts = Arrays.copyOfRange(arryas,leftBegin,firstMiddleIndex);
//        int[] rights = Arrays.copyOfRange(arryas,firstMiddleIndex+middle,rightEnd+1);
//        System.out.println(Arrays.toString(arryas));
//        System.out.println("middles: "+ Arrays.toString(middles));
//        System.out.println("lefts " + Arrays.toString(lefts));
//        System.out.println("rights " + Arrays.toString(rights));
        return new int[]{firstMiddleIndex, firstMiddleIndex + middle};
    }


    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] arrays = new int[]{1, 3, 2, 7, 9, 2, 5, 7, 7, 7, 7, 7, 2, 10, 8, 7, 6, 3, 2};
//        int[] arrays = new int[]{1,3,2,5,5,5,0,1,2,3,5,1,2,10,8,6,5,7,8,6,8,9,1,7};
//        System.out.printf(Arrays.toString(arrays));

        quickSort.sort(arrays);
        // 1 3 7 5
        // 10
//        quickSort.spiltSortByPartition(arrays,0, arrays.length - 1);
    }
}
