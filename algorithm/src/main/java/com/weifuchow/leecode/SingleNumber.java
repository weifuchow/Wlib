package com.weifuchow.leecode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
// 输入: [2,2,1]
//输出: 1
//
//
// 示例 2:
//
// 输入: [4,1,2,1,2]
//输出: 4
public class SingleNumber {

    public int singleNumber(int[] nums) {
        // 出现了多少次
        HashSet<Integer> set = new HashSet();
        for (int i = 0; i < nums.length ; i++) {
            if(!set.add(nums[i])){
                set.remove(nums[i]);
            }
        }
        return set.iterator().next();
    }


    public static void main(String[] args) {
        int[] arrays = new int[]{4,1,2,1,2};
        SingleNumber solution = new SingleNumber();
        System.out.println(solution.singleNumber(arrays));
    }
}
