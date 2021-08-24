package com.weifuchow.leecode.topK;


import cn.hutool.core.util.RandomUtil;

import java.util.*;
import java.util.Random;

//给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
//
// 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
//
//
//
// 示例 1:
//
//
//输入: [3,2,1,5,6,4] 和 k = 2
//输出: 5
//
//
// 示例 2:
//
//
//输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
//输出: 4
public class FindKthLargest {


    public int findKthLargestByHeap(int[] nums,int k){

        // 维护一个大根堆。最后n+klog2n
        // 大顶堆
        PriorityQueue<Integer> queue = new PriorityQueue(5, (o1, o2) -> {
            Integer i1 = (Integer) o1;
            Integer i2 = (Integer) o2;
            return i2.compareTo(i1);
        });

        for (int i = 0; i < nums.length; i++) {
            queue.add(nums[i]);
        }

        int res = 0;
        for (int i = 1; i <= k ; i++) {
            res = queue.poll();
        }
        return res;
    }

    public int findKthLargest(int[] nums, int k) {
        // 维护 一个 k 的数组，小顶堆。
        // 当新插入元素时.使用插入排序，维护好这个数组，保持前K组。最小的值剔除掉。

        // 最傻逼方法，快排，取数组第K值。可以利用二分,划分出第K个数。递归
        // 为什么因为二分，左边区域是固定。快排最快
        return findKthLargestQuickSort(nums,0, nums.length -1, nums.length - k );
    }

    // k => k - 1, [3,2,3,1,2,4,5,5,6] 和 k = 4 , 第4大元素，对应下标 targetPosition = length - k
    // [3 2 3 1] 3 [4 5 5 6]
    // mid =
    // 从指定位置 begin , end 寻找targetPostion 的值
    public int findKthLargestQuickSort(int[] nums,int begin,int end,int targetPosition){
        int midPostion = splitArrays(nums,begin,end);
        if(midPostion == targetPosition){
            return nums[midPostion];
        }else if(midPostion > targetPosition){
            // 在左区域
            return findKthLargestQuickSort(nums,begin,midPostion - 1,targetPosition);
        }else {
            // 在右区
            return findKthLargestQuickSort(nums,midPostion+1,end,targetPosition);
        }

    }


    // return split location 返回切割点。
    public int splitArrays(int[] nums,int begin,int end){
        // 避免最坏情况，随机选择分割点
        Random random = new Random();
        // 0~5
        int randomPosition = random.nextInt(end - begin + 1) + begin;
        swap(nums,begin,randomPosition);
        int position = begin;
        int positionVal = nums[position];
        // 切割数组 双指针
        //  1 3 0 2 3 6 9 7 4
        //    left         right'
        //
        // 左边发现比position 大的位置则，放置右区域中。
        // 右边发现比position 小的位置则，放置左区域中。
        int left = begin;
        int right = end;
        // 将基准值交换的left = right 的区域，
        // 从后面开始，因为前面开始，最终无法确定基准点的准确位置
        while (left < right){
            // 从后面比较,一直找到right区域比基准点大的位置。
            // 0 1 2 3 4 5 6 7 8
            while (nums[right] > positionVal && right > left){
                right --;
            }
            // 直到right找到比基准点小于等于的，进行交换
            swap(nums,right,left);
            while (nums[left] <= positionVal && right > left){
                left++;
            }
            swap(nums,left,right);
        }
        return left;
    }


    public void swap(int[] array,int p1,int p2){
        int temp = array[p1];
        array[p1] = array[p2];
        array[p2] = temp;
    }


    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue(5, (o1, o2) -> {
            Integer i1 = (Integer) o1;
            Integer i2 = (Integer) o2;
            return i2.compareTo(i1);
        });

        queue.add(1);
        queue.add(-2);
        queue.add(23);

        queue.add(-5);

        System.out.println(queue.poll());

        FindKthLargest solution = new FindKthLargest();
        int[][] array = new int[][]{
          new int[]{3,2,1,5,6,4},
          new int[]{3,2,3,1,2,4,5,5,6}
        };
        int[] k = new int[]{2,4};
        verifiy(array,k,solution);
    }


    public static void verifiy(int[][] arrays, int[] kArrays, FindKthLargest solution){
        for (int i = 0; i < kArrays.length; i++) {
            int result = solution.findKthLargestByHeap(arrays[i],kArrays[i]);
            System.out.println("********* begin *********");
            System.out.println("array = > " + Arrays.toString(arrays[i]));
            System.out.println("k = > " +  kArrays[i]);
            System.out.println("result => " + result);
            System.out.println("********* end ************\n");
        }
    }
}
