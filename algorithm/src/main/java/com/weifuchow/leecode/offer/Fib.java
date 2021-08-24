package com.weifuchow.leecode.offer;


public class Fib {

    public int fib(int n) {
        if(n == 0) return 0;
        Long[] dp = new Long[n + 1];
        dp[0] = 0L;
        dp[1] = 1L;
        for(int i=2;i<=n;i++){
            dp[i] = (dp[i -1] + dp[i - 2]) % 1000000007;
        }
        return dp[n].intValue();
    }

    public static void main(String[] args) {
        Fib solution = new Fib();
        System.out.println(solution.fib(55));
    }

}
