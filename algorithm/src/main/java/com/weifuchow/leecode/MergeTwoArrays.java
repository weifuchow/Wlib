package com.weifuchow.leecode;


import java.util.Arrays;

public class MergeTwoArrays {

    //给你两个有序整数数组 nums1 和 nums2，
    // 请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
    // 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。你可以假设 nums1 的空间大小等于 m + n，这样它就有足够的空间保存来自 nu
    //ms2 的元素。
    //输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
    //输出：[1,2,2,3,5,6]

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] result = new int[m+n];
        int k = 0;
        int i = 0,j =0;
        for (; i < m && j < n; ) {
            // 稳定排序
            // nums[i] == nums[j], i left , right.
            if( nums1[i] > nums2[j] ){
                 result[k++] = nums2[j++];
            }else{
                result[k++] = nums1[i++];
            }
        }
        // 判断哪个处理完。
        // 100 || 1 2 3 4 5 6
        if(j == n){
            while (i < m){
                result[k++] = nums1[i++];
            }
        }
        // 1 2 3 4 5 6  || 100
        if(i == m){
            while (j < n){
                result[k++] = nums2[j++];
            }
        }

        for (int l = 0; l < m+n; l++) {
            nums1[l] = result[l];
        }

    }

    public static void main(String[] args) {
        MergeTwoArrays solution = new MergeTwoArrays();
        int[] arrays = new int[]{1,2,3,0,0,0};
        solution.merge(arrays,3,new int[]{-2,-1,0,0,0,0},3);
        System.out.println(Arrays.toString(arrays));
    }

}
