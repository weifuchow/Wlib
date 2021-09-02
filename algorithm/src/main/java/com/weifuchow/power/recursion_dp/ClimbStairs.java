package com.weifuchow.power.recursion_dp;

//假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
//
// 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
public class ClimbStairs {


    // 只剩一层楼梯 只有一种方法
    // 剩2层时，可以爬多少级
    // 2层时可以几种方法 【1 1】 【2】
    // 3层时， 【1，1,1】 【1,2,】 【2,1】
    // 【1，1,1,1】 [1,2,1] [2,1,1] [2,2] [1,1,2]
    public int climbStairsRecursive(int n){
        if(n < 0){
            return 0;
        }
        if(n == 0){
            return 1;
        }
        return climbStairsRecursive(n-1) + climbStairsRecursive(n-2);
    }

    public int climibStairsDp(int n){
        int[] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i -1 ] + dp[i - 2];
        }
        return dp[n];
    }



    public static void main(String[] args) {
        ClimbStairs solution = new ClimbStairs();
        System.out.println(solution.climbStairsRecursive(10)); // 89
        System.out.println(solution.climibStairsDp(10)); // 89
    }

}
