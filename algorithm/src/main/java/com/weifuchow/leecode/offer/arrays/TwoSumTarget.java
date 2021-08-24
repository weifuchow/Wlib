package com.weifuchow.leecode.offer.arrays;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumTarget {


    //输入一个递增排序的数组和一个数字s，在数组中查找两个数，使得它们的和正好是s。如果有多对数字的和等于s，则输出任意一对即可。
    //
    //
    //
    // 示例 1：
    //
    // 输入：nums = [2,7,11,15], target = 9
    //输出：[2,7] 或者 [7,2]
    //
    //
    // 示例 2：
    //
    // 输入：nums = [10,26,30,31,47,60], target = 40
    //输出：[10,30] 或者 [30,10]

    public int[] twoSum(int[] nums, int target) {
        // 5 4 9
        Map<Integer, Integer> remainMap = new HashMap<>();
        int[] arrays = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (remainMap.containsKey(nums[i])) {
                arrays = new int[]{remainMap.get(nums[i]), nums[i]};
                return arrays;
            } else {
                remainMap.put(target - nums[i], nums[i]);
            }
        }
        return arrays;
    }

    public static void main(String[] args) {
        TwoSumTarget solution = new TwoSumTarget();
        int[] arrays = new int[]{10,47,30,47,0,60};

        System.out.println(Arrays.toString(solution.twoSum(arrays,40)));

    }


}
