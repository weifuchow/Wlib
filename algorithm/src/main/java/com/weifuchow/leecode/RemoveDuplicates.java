package com.weifuchow.leecode;

import java.util.Arrays;

/**
 * @desc: 删除重复数字
 * @author: weifuchow
 * @date: 2021/7/2 17:34
 */
public class RemoveDuplicates {

    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * 输入：nums = [1,1,2]
     * 输出：2, nums = [1,2]
     * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
     * <p>
     * <p>
     * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
     * 输出：5, nums = [0,1,2,3,4]
     * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
     */

    public int removeDuplicates(int[] nums) {
        // 定义两指针
        int slow = 0;
        int fast = 1;
        int size = nums.length;
        //
        while (fast < size) {
            if (nums[slow] == nums[fast]) {
                fast++;
            } else {
                slow++;
                nums[slow] = nums[fast];
                fast++;
            }
        }
        return slow;
    }

    public static void main(String[] args) {
        RemoveDuplicates solution = new RemoveDuplicates();
        //
//        int[] arrays = new int[]{1, 2, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 7, 7, 7, 8, 8, 8, 9, 9, 10};
        int[] arrays = new int[]{1,1,2};

        System.out.println(solution.removeDuplicates(arrays));
        System.out.println(Arrays.toString(arrays));
    }


}
