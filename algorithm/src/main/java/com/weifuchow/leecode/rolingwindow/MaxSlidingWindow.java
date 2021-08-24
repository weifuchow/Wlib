package com.weifuchow.leecode.rolingwindow;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 示例 1：
//
//
//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
//解释：
//滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
public class MaxSlidingWindow {


    public int[] maxSlidingWindow(int[] nums, int k) {
        if(nums.length == 0 && k == 0){
            return new int[0];
        }
        int[] result = new int[nums.length - (k - 1)];
        LinkedList<Integer> window = new LinkedList<>();
        // 进入队列
        int counter = 0;
        int curMax = Integer.MIN_VALUE;
        for (int i = 0; i < k ; i++) {
            curMax = Math.max(curMax,nums[i]);
            window.addLast(nums[i]);
        }
        result[counter++] = curMax;
        for (int i = k; i < nums.length; i++) {
            window.addLast(nums[i]);
            int remove = window.peekFirst();
            window.removeFirst();
            // 删掉了最大的值
            if(remove == curMax){
                //
                curMax = Integer.MIN_VALUE;
                for (int j = 0; j < window.size(); j++) {
                    // 三者中最大
                    curMax = Math.max(curMax,window.get(j));
                }
            }else{
                curMax = Math.max(curMax,nums[i]);

            }
            result[counter++] = curMax;
        }
        return result;
    }




    public static void main(String[] args) {
        MaxSlidingWindow window = new MaxSlidingWindow();
//        int[] arrays = new int[]{3,1,-1,-3,5,3,6,7};
        int[] arrays = new int[]{};

        int[] result = window.maxSlidingWindow(arrays,0);
        System.out.println(Arrays.toString(result));
    }

}
