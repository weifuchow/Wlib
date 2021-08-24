package com.weifuchow.leecode;

import java.util.Arrays;
import java.util.HashMap;

//给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
//
//
//
// 进阶：
//
//
// 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
// 你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？
public class RotateArray {

    // 示例 1:
    //
    //
    //输入: nums = [1,2,3,4,5,6,7], k = 3
    // [1,2,3,4,5,6,7] => [1]
    //输出: [5,6,7,1,2,3,4] = > 保留最后一个元素， 然后所有节点后移，最后替换第一个节点。
    //解释:
    //向右旋转 1 步: [7,1,2,3,4,5,6]
    //向右旋转 2 步: [6,7,1,2,3,4,5]
    // [1,2,3,4,5,6,7] = >  1 => 4,2=>5,3=>6,4=>7,5=>8,6=>9,7=>10
    //向右旋转 3 步: [5,6,7,1,2,3,4]
    //
    //
    // 示例 2:
    //
    //
    //输入：nums = [-1,-100,3,99], k = 2
    //输出：[3,99,-1,-100]
    //解释:
    //向右旋转 1 步: [99,-1,-100,3]
    //向右旋转 2 步: [3,99,-1,-100]
    public void rotate(int[] nums, int k) {
        rotateByCopyArray(nums,k);
    }

    // [1,2,3,4,5,6,7] =
    // [1,2,3,4,5,6,7]
    // [5,6,7,1,2,3,4]
    public void rotateByCopyArray(int[] nums, int k) {
        //
        int length = nums.length;
        int[] copyArrays = new int[nums.length];
        for (int i = 0; i < length; i++) {
            copyArrays[i] = nums[i];
        }
        //
        for (int i = 0; i < length; i++) {
            nums[(i + k) % length] = copyArrays[i];
        }



    }

    public void rotateByJumpStep(int[] nums,int k){
        HashMap<Integer,Integer> originalMap = new HashMap<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            int jumpIndex = (i + k) % length ;
            if(originalMap.containsKey(i)){
                originalMap.put(jumpIndex, nums[jumpIndex]);
                int originVal = originalMap.get(i);
                nums[jumpIndex] = originVal;
            }else {
                originalMap.put(jumpIndex, nums[jumpIndex]);
                nums[jumpIndex] = nums[i];
            }
        }
    }

    public void rotateByPower(int[] nums, int k){
        for (int i = 0; i < k; i++) {
            int last = nums[nums.length - 1];
            for (int j = 0; j < nums.length; j++) {
                int temp = nums[j];
                nums[j] = last;
                last = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arrays = new int[]{1,2,3,4,5,6,7};
        RotateArray solution = new RotateArray();
        solution.rotateByCopyArray(arrays,3);
        System.out.println(Arrays.toString(arrays));
    }
}
