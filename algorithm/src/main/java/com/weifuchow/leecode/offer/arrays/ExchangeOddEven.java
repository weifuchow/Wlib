package com.weifuchow.leecode.offer.arrays;


import java.util.Arrays;

public class ExchangeOddEven {


    // 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
    // 输入：nums = [1,2,3,4]
    // 输出：[1,3,2,4]
    // 注：[3,1,2,4] 也是正确的答案之一。
    public int[] exchange(int[] nums) {
        //
        int[] results = new int[nums.length];
        int left = 0;
        int right = nums.length - 1;
        for (int i = 0; i <nums.length ; i++) {
            if(nums[i] % 2 == 1 ){
                results[left++] = nums[i];
            }else{
                results[right--] = nums[i];
            }
        }
        return results;
    }


    public static void main(String[] args) {
        ExchangeOddEven solution = new ExchangeOddEven();
        int[] res = solution.exchange(new int[]{1,2,3,4});
        System.out.println(Arrays.toString(res));
    }
}
