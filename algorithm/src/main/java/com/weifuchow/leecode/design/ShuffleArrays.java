package com.weifuchow.leecode.design;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class ShuffleArrays {

    int[] nums;
    Random random = new Random();


    public ShuffleArrays(int[] nums) {
        this.nums = nums;
    }

    /**
     * Resets the array to its original configuration and return it.
     */
    public int[] reset() {
        return nums;
    }

    /**
     * Returns a random shuffling of the array.
     */
    // 1 2 3 4 5
    // 5 4 2 1 3
    public int[] shuffle() {
        // [1 2 3 4 5]
        int[] result = new int[nums.length];
        shuffleRecursion(result, new AtomicInteger(0), 0, nums.length - 1);
        return result;

    }
    // 递归，不复制数据，
    public void shuffleRecursion(int[] result, AtomicInteger index, int left, int right) {
        if(left > right){
            return;
        }
        if (left == right) {
            result[index.getAndIncrement()] = nums[left];
            return;
        } else {
            int randInt = random.nextInt(right - left + 1) + left;
            result[index.getAndIncrement()] = nums[randInt];
//            if(index == result.length){
//                return;
//            }
//            System.out.println("random => " + randInt + " ,[" + left + " , " + right + "]");
//            System.out.println("left = > " + left + "," + (randInt-1));
//            System.out.println("right = > " + (randInt + 1) + "," + right);
//            System.out.println("******************** " + Arrays.toString(result) + " ********************");
            // left
            shuffleRecursion(result,index,left,randInt -1);
            shuffleRecursion(result,index,randInt + 1,right);
        }
    }


    public static void main(String[] args) {
//        Random random = new Random();
//        int left = 1;
//        int right = 2;
//        for (int i = 0; i < 100; i++) {
//            // 0 - 2
//            System.out.println(random.nextInt(right - left +1) + left);
//        }

        ShuffleArrays shuffle = new ShuffleArrays(new int[]{1,2,3,4,6,7,8,9,0});
        for (int i=0;i< 100;i++){
            System.out.println(Arrays.toString(shuffle.shuffle()));

        }
        System.out.println(Arrays.toString(shuffle.shuffle()));
//        System.out.println(Arrays.toString(shuffle.shuffle()));
        System.out.println(Arrays.toString(shuffle.reset()));

    }
}