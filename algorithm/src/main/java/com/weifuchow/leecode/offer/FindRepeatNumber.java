package com.weifuchow.leecode.offer;


import java.util.HashSet;

public class FindRepeatNumber {

    // 输入：
    //[2, 3, 1, 0, 2, 5, 3]
    //输出：2 或 3
    //在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请
    //找出数组中任意一个重复的数字
    public int findRepeatNumber(int[] nums) {
        // hashset
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if(hashSet.add(nums[i]) == false){
                return nums[i];
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        FindRepeatNumber solution = new FindRepeatNumber();
        int[] arrays = new int[]{3, 4, 2, 0, 0, 1};
        System.out.println(solution.findRepeatNumber(arrays));
    }

}
