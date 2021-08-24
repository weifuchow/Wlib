package com.weifuchow.power.recursion_dp;


//假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
//
// 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
//
// 注意：给定 n 是一个正整数。
public class ClimbStairsRecursionAndDynamicPrograming {


    // 示例 1：
    //
    // 输入： 2
    //输出： 2
    //解释： 有两种方法可以爬到楼顶。
    //1.  1 阶 + 1 阶
    //2.  2 阶
    //
    // 示例 2：
    //
    // 输入： 3
    //输出： 3
    //解释： 有三种方法可以爬到楼顶。
    //1.  1 阶 + 1 阶 + 1 阶
    //2.  1 阶 + 2 阶
    //3.  2 阶 + 1 阶

    // 4
    // 1 1 1 1
    // 2 2
    // 1 2 1
    // 2 1 1
    // 1 1 2
    public int climbStairs(int n) {
        return climbStairsDp(n);
    }

    // 定义n阶楼有多少种方法完成楼层跳跃
    public int climbStairsRecursion(int n) {
        // base case
        if (n <= 1) {
            return 1;
        }  else {
            // 普遍 可以选择一步
            // 用single 方式跳
            // 用twice  方式跳
            int single = climbStairsRecursion(n-1);
            int twice =  climbStairsRecursion(n-2);
            return single + twice;
        }
    }

    public int climbStairsDp(int n){
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n ; i++) {
            dp[i] = dp[i - 2] + dp[i - 1];
        }
        return dp[n];
    }



    // f(4) = f(10） + f(8)
    public static void main(String[] args) {
        ClimbStairsRecursionAndDynamicPrograming solution = new ClimbStairsRecursionAndDynamicPrograming();
        System.out.println(solution.climbStairsRecursion(10));
        System.out.println(solution.climbStairsDp(10));

    }
}
