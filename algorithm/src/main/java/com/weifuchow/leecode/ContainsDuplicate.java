package com.weifuchow.leecode;


import java.util.HashMap;
import java.util.HashSet;

public class ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        boolean result = false;
        HashSet<Integer> set = new HashSet();
        for (int i = 0; i < nums.length; i++) {
            if(!set.add(nums[i])){
                return true;
            }
        }
        return  result;
    }

    // 如果存在一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
    // 示例 1:
    //输入: [1,2,3,1]
    //输出: true
    //
    // 示例 2:
    //输入: [1,2,3,4]
    //输出: false
    //
    // 示例 3:
    //输入: [1,1,1,3,3,4,3,2,4,2]
    //输出: true
    public static void main(String[] args) {
        int[] arrays = new int[]{1,1,1,3,3,4,3,2,4,2};
        ContainsDuplicate solution = new ContainsDuplicate();
        System.out.println(solution.containsDuplicate(arrays));
    }
}
