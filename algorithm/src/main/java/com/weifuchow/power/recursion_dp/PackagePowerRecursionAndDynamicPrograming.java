package com.weifuchow.power.recursion_dp;

/**
 *
 一个背包有一定的承重cap，有N件物品，每件都有自己的价值，记录在数组v中，也都有自己的重量，记录在数组w中，每件物品只能选择要装入背包还是不装入背包，要求在不超过背包承重的前提下，选出物品的总价值最大。

 给定物品的重量w价值v及物品数n和承重cap。请返回最大总价值。
 测试样例：

 [1,2,3],
 [1,2,3],
 3,
 6

 返回：6
 */
public class PackagePowerRecursionAndDynamicPrograming {

    public int getMaxValue(int[] weights,int[] values,int totalWeight){
        return getMaxValueFormIndex(weights,values,0,totalWeight);
    }

    public int getMaxValueFormIndex(int[] weights,int[] values,int index,int remainWeight){
        // 表示没有物品可拿了 (物品被拿光，或者 剩余重量 == 0)
        System.out.println(index + "," + remainWeight);
        if(index == weights.length || remainWeight == 0){
            return 0;
        }
        else{
            // 普遍情况下，
                // 选择当前物品下，后面剩余的最大值。
                // 不选择当前物品下，剩余的最大值。
            // 先判断是否能拿的下当前货物，否则只能从后拿
            int curProductWeight = weights[index];
            int curProductValue = values[index];
            if(remainWeight - curProductWeight < 0){
                return getMaxValueFormIndex(weights,values,index+1,remainWeight);
            }else{
                return Math.max(
                        curProductValue + getMaxValueFormIndex(weights,values,index+1,remainWeight-curProductWeight),
                        getMaxValueFormIndex(weights,values,index+1,remainWeight)
                );
            }
        }
    }

    public int getMaxValueByDp(int[] weights,int[] values,int totalWeight){
        int[][] dp = new int[weights.length+1][totalWeight+1];
        // 第0列
        for (int index = weights.length; index >= 0 ; index--) {
            for (int remainWeight = 0; remainWeight < totalWeight+1 ; remainWeight++) {
                if(index == weights.length || remainWeight == 0){
                    dp[index][remainWeight] = 0;
                    continue;
                }
                int curProductWeight = weights[index];
                int curProductValue = values[index];
                if(remainWeight - curProductWeight < 0){
                   dp[index][remainWeight] = dp[index + 1][remainWeight];
                }else{
                     dp[index][remainWeight] = Math.max(
                            curProductValue + dp[index + 1][remainWeight-curProductWeight],
                            dp[index + 1][remainWeight]
                    );
                }
            }
        }
        return dp[0][totalWeight];
    }


    public static void main(String[] args) {
        PackagePowerRecursionAndDynamicPrograming solution = new PackagePowerRecursionAndDynamicPrograming();
        int[] weights = new int[]{1,2,6,5,10,20,50,40,30,20,70,80,30};
        int[] value =   new int[]{1,5,4,5,25, 30,30,10,20,50,30,40,30};
        int totalWeights = 20;
//        int[] weights = new int[]{1,2,6,5};
//        int[] value =   new int[]{1,5,4,5};
//        int totalWeights = 10;
        long now = System.currentTimeMillis();
        int result = solution.getMaxValue(weights,value,totalWeights);
        System.out.println("recursion use time = " + (System.currentTimeMillis() - now)  + " -- " +  result);
        // 动态规划
        now = System.currentTimeMillis();
        // convert to n*n 的表,
        int result1 = solution.getMaxValueByDp(weights,value,totalWeights);
        System.out.println("dp use time = " + (System.currentTimeMillis() - now)  + " -- " +  result1);

    }

}
