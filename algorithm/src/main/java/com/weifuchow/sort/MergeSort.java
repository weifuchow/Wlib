package com.weifuchow.sort;

import java.util.Arrays;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/6/1 17:14
 */
public class MergeSort implements ISort {
    @Override
    public int[] sort(int[] arrays) {
        // 归并排序思想
        // 将数据分割成两部分，左边有序，右边有序。
        // 两部分有序的代码合并，通过两个指针前后两指针进行交换。
        // 那么如何分割成两部分有序的数组呢，利用递归。
        // 先将局部有序，当长度为1，自然有序，两个长度为1的数据，进行合并成长度为2的数组，以此内推。2个数组长度再进行合并成了4个。递归整个过程。
        // 完成局部有序 0,1,2,3,4,5,6
        mergeRecursion(arrays, 0, arrays.length / 2, arrays.length / 2, arrays.length - arrays.length / 2);
        return arrays;
    }


    public void mergeRecursion(int[] arrays, int leftPoint, int leftLength, int rightPoint, int rightLength) {
//        System.out.println(leftPoint + "," + leftLength + "," + rightPoint + "," + rightLength);
        if (leftPoint == rightPoint) {
//            System.out.println("已完成排序" +leftPoint + "," + leftLength + "," + rightPoint + "," + rightLength);
            return;
        } else {
            // 分为两部分
            mergeRecursion(arrays, leftPoint, leftLength / 2, leftPoint + leftLength / 2, leftLength - leftLength / 2);
            mergeRecursion(arrays, rightPoint, rightLength / 2, rightPoint + rightLength / 2, rightLength - rightLength / 2);
//            System.out.println("最先进入循环数组：" + leftPoint + ", " + leftLength + ", " + rightPoint + "," + rightLength);
            mergeArrays(arrays, leftPoint, leftLength, rightPoint, rightLength);
        }
    }

    // 先局部到整体
    // 只关注。合并过程
    // 若 [1 ,3, 6, 6, 8] [2,3,6,8,9]
    // left 1 ~ right 9,分割成两部分。
    // 若 [1,1,3, 6, 6, 8] [1,2,3,6,8,9,11]
    // left point 2,rightPoint = 2 , [1 2]两个值需要插入 插入 3 之前的位置
    // [1,1,3,6,6,8] [1,2]
    // [1,1,3,6,6,8] [2]
    // [1,1,2,3,6,6,8]
    // [1,1,2,2,3,6,6,8]
    // [1,1,1,2,3, 6, 6, 8] [3,6,8,9,11]
    public void mergeArrays(int[] arrays, int leftPoint,int leftLength, int rightPoint, int rightLength) {
        // right 为奇数。则右半部分比较大。
        // 同时遍历left rightPoint
        int totalLength = leftLength + rightLength;
        int rightRange = rightPoint + rightLength -1;
//        System.out.println("傳遞參數：" + Arrays.toString(arrays) + ",leftPonit:" + leftPoint + " ,rightPoint: " + rightPoint);
        while (leftPoint < rightPoint && rightPoint <= rightRange) {
            // 从left 数组找到第一个比 right 数组大的数
//            for (; leftPoint < totalLength - 1; leftPoint++) {
//                if (arrays[leftPoint] > arrays[rightPoint]) {
//                    break;
//                }
//            }
            while(leftPoint < rightRange && arrays[leftPoint]<= arrays[rightPoint]){
                leftPoint++;
            }
            // 从right数组找到比leftpoint 小的数
            int rightStart = rightPoint;
//            for (; rightPoint < totalLength - 1; rightPoint++) {
//                if (arrays[leftPoint] < arrays[rightPoint]) {
//                    break;
//                }
//            }
            while (rightPoint < rightRange && arrays[leftPoint] >= arrays[rightPoint]){
                rightPoint++;
            }
//            // 需要区别是最后一个都小于左边。第一个还是到达最后一个之前的数
            if(rightRange == rightPoint && arrays[leftPoint] >= arrays[rightPoint] ){
                rightPoint++;
            }
            //
//            System.out.println("需要将rightPoint:" + rightPoint + " 之前的" + (rightPoint - rightStart) +
//                    "个数，插入到leftpoint: " + leftPoint + " 之前");

            for (int i = rightPoint; i > rightStart; i--) {
                for (int j = rightPoint - 1; j > leftPoint; j--) {
                    SortHelper.swap(arrays, j, j - 1);
                }
//                System.out.println("交换结果：" + Arrays.toString(arrays));
            }
            leftPoint = (rightPoint - rightStart) + leftPoint;
        }
//        System.out.println("交换结束结果：" + Arrays.toString(arrays) + ",leftPonit:" + leftPoint + " ,rightPoint: " + rightPoint);
//        System.out.println(Arrays.toString(arrays));
    }


    public static void main(String[] args) {
        MergeSort sort = new MergeSort();
        int[] arrays = new int[]{5,3,2,1,8,9,33,44,88,99,-2,-8,6,0,2,8,11,8};
//        sort.mergeArrays(arrays,0,1,1,1);
        System.out.println(Arrays.toString(sort.sort(arrays)));
    }
}
