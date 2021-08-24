package com.weifuchow.power.recursion_dp;


public class FibonacciPowerRecursionAndDynamicPrograming {


    public int fibonacciRecursion(int n){
        // base case
        if(n == 1 || n == 2){
            return 1;
        }
        return fibonacciRecursion(n-1) + fibonacciRecursion(n -2);
    }

    public int fibonacciDp(int counter){
        // base case
        int[] dpTable = new int[ counter + 1];
        // base case
        dpTable[1] = 1;
        dpTable[2] = 1;
        for (int n = 3; n <= counter; n++) {
            dpTable[n] = dpTable[n - 1] + dpTable[n -2];
        }
       return dpTable[counter];
    }

    public static void main(String[] args) {
        FibonacciPowerRecursionAndDynamicPrograming solution = new FibonacciPowerRecursionAndDynamicPrograming();
        long now = System.currentTimeMillis();
        int result = solution.fibonacciRecursion(48);
        System.out.println("recursion use time = " + (System.currentTimeMillis() - now)  + " -- " +  result);

        now = System.currentTimeMillis();
        int result1= solution.fibonacciDp(48);
        System.out.println("recursion use time = " + (System.currentTimeMillis() - now)  + " -- " +  result1);
    }
}
