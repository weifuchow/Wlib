package com.weifuchow.leecode.offer.binary;

import java.util.Arrays;

//一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
//
//
//
// 示例 1：
//
// 输入：nums = [4,1,4,6]
//输出：[1,6] 或 [6,1]
//
//
// 示例 2：
//
// 输入：nums = [1,2,10,4,1,4,3,3]
//输出：[2,10] 或 [10,2]
public class SingleNumberInArrays {

    public int[] singleNumbers(int[] nums) {
        // a ,b ,c ,c d,d,e,e,g,g
        // 1  3 22 55 66 77

        // 1010
        // 0010
        // 1000
        int result = nums[0];
        // 异或相同的为0.
        for (int i = 1; i < nums.length ; i++) {
            result ^= nums[i];
        }
        // 1 ^ 6
        // 0001
        // 0110
        // 异或
        // 0111 。 找到其第一位1的值。并根据他进行分组。 记为m. m = 1
        // 说明可以将数组划分成两部分。第一个是 与m为异或为。 肯定有一组。 num & m 为1.则为1组
        int m = 1;
        while ((m & result) == 0){
            m = m << 1;
        }
        // 1
        int x = 0; // 初始值必须为0，因为0异或其他值。等于其本身。相同为0，不同为1
        int y = 0;
        //  必然存在一组m & nums[i]
        //  1000 & 0001 == 0
        //  1000 & 0010 == 0
        //  1000 & 1000
        for (int i = 0; i < nums.length ; i++) {
            if((m & nums[i]) == 0){
                x ^= nums[i];
            }else{
                y ^= nums[i];
            }
        }
        return new int[]{x,y};
    }


    public static void main(String[] args) {
        SingleNumberInArrays solution = new SingleNumberInArrays();
        int[] results = solution.singleNumbers(new int[]{1,2,10,4,1,4,3,3});
        System.out.println(Arrays.toString(results));

    }
}
