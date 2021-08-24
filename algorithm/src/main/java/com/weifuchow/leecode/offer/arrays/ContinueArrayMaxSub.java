package com.weifuchow.leecode.offer.arrays;



//输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
//
// 要求时间复杂度为O(n)。
//
//
//
// 示例1:
//
// 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
//输出: 6
//解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
//
//
//
// 提示：
//
//
// 1 <= arr.length <= 10^5
// -100 <= arr[i] <= 100
public class ContinueArrayMaxSub {


    // 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
    //输出: 6
    //解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
    public int maxSubArray(int[] nums) {
        // greed 贪心。
        // 两个指，记录上次的sum 与当前节点的值。
        //  若当前节点的值 > 0,lastSum + array[i] > array[i]. 则加入max
        // -5 -3 -2 -1 -2 -1
        int lastSum = nums[0];
        int max = lastSum;
        int i = 1;
        while (i < nums.length){
            // -8 > -5
            //   1 3。 -5 2 -6 1,2,3
            // 贪心：若相加 大于该节点。者选择这一个节点。
            //      若相加 小于該节点且之前 lastsum的值。小于该节点。以该节点进行替换。
            //      若相加 小于该节点且  且lastsum 要大于该节点。则节点后移。不处理
            // 核心：通过对比之前的大小。进行过滤大小的判断。O(n)即可完成。
            if(lastSum + nums[i] > nums[i] ){
                lastSum = lastSum + nums[i];
                max = Math.max(max,lastSum);
            }
            else if(lastSum <= nums[i]){
                lastSum = nums[i];
                max = Math.max(max,lastSum);
            }
            i++;
        }
        return max;
    }

    public static void main(String[] args) {
        ContinueArrayMaxSub solution = new ContinueArrayMaxSub();
//        int max = solution.maxSubArray(new int[]{-2,1,-3,4,-1,8,9,10,20,2,1,-5,4});
        int max = solution.maxSubArray(new int[]{-5,2,-6,1,2,3});
        System.out.println(max);
    }

}
