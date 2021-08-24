package com.weifuchow.leecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 */
public class TwoNumberSum {

    // 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
    // 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> remainNumMap = new HashMap<>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length ; i++) {
            // 检查是否有其值跟他一样
            int remain = target - nums[i];
            if(remainNumMap.containsKey(remain)){
                res[0] = remainNumMap.get(remain);
                res[1] = i;
                break;
            }else{
                remainNumMap.put(nums[i],i);
            }
        }
        return res;
    }

    public int[] twoSumByHash(int[] nums,int target){
        //输入：nums = [2,7,11,15], target = 9
        //输出：[0,1]

        // cur = 2, 检查map中有没有值为 7的key。没有入库。将cur的值，及下标入map.
        // cur = 7.
        HashMap<Integer,Integer> remainNumMap = new HashMap<>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length ; i++) {
            // 检查是否有其值跟他一样
            int remain = target - nums[i];
            if(remainNumMap.containsKey(remain)){
                res[0] = remainNumMap.get(remain);
                res[1] = i;
                break;
            }else{
                remainNumMap.put(remain,i);
            }
        }
        return res;
    }

    public int[] twoSumPower(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if(nums[i] + nums[j] == target){
                    res[0] = i;
                    res[1] = j;
                    break;
                }
            }
        }
        return res;
    }


    public static void main(String[] args) {
        int[] arrays = new int[]{2,7,11,15};
        TwoNumberSum solution = new TwoNumberSum();
        int[] res = solution.twoSum(arrays, 9);
        System.out.println(Arrays.toString(res));
    }
}
