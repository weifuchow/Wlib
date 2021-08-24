package com.weifuchow.leecode.rolingwindow;


import com.weifuchow.leecode.MergeTwoListNode;

import java.util.LinkedList;

public class MinSubArrayLen {


    //  输入：target = 7, nums = [2,3,1,5,4,3]
    //  输出：2
    public int minSubArrayLen(int target, int[] nums) {
        LinkedList<Integer> window = new LinkedList();
        int currentSum = 0;
        int minSize = Integer.MAX_VALUE;
        boolean isFind = false;
        for (int i = 0; i < nums.length ; i++) {
            //
            window.addLast(nums[i]);
            if(nums[i] + currentSum == target){
                isFind = true;
                minSize = Math.min(minSize,window.size());
                currentSum = target - window.getFirst();
                window.removeFirst();
            }else if (nums[i] + currentSum > target){
                // 需要恢复到小于 target
                // 2,3,1,8
                isFind = true;
                minSize = Math.min(minSize,window.size());
                //
                currentSum = nums[i] + currentSum - window.getFirst();
                window.removeFirst();
                while (currentSum >= target){
                    minSize = Math.min(minSize,window.size());
                    currentSum = currentSum - window.getFirst();
                    window.removeFirst();
                }

            }else{
                currentSum = currentSum + nums[i];
            }
        }

        return isFind ? minSize : 0;
    }


    public static void main(String[] args) {
        MinSubArrayLen solution = new MinSubArrayLen();

        int[] arrays = new int[]{1,2,3,4,5,20};

        //  输入：target = 7, nums = [2,3,1,5,4,3]

        System.out.println(solution.minSubArrayLen(15,arrays));

    }
}
