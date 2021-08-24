package com.weifuchow.power.recursion_dp;


import java.util.HashMap;
import java.util.Map;

public class MaxProfitRecursionAndDynamicPrograming {


    //输入：[7,1,5,3,6,4]
    //输出：5
    //解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
    // 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。

    public int maxProfit(int[] prices) {
        return maxProfitRecursion(prices, 0, 0);
    }

    // 递归定义，从当前index 下标买入的股票
    // 获取一段时间最高收益
    // 当天有几种操作，持有 买入 或者 卖出
    //  持有的话，今天买或者不买，今天卖和不卖的最大值。
    // 只能买一次
    public int maxProfitRecursion(int[] prices, int selectIndex, int buyIndex) {
        // base case 最后一天必须卖出进行结算
        // 表示超过长度。没有办法可选、
        if (selectIndex >= prices.length) {
            return 0;
        } else {
            // 表示之前没有买
            if (buyIndex == 0) {
                // 买或者观望
                int buyToday = maxProfitRecursion(prices, selectIndex + 1, selectIndex + 1);
                int holdToday = maxProfitRecursion(prices, selectIndex + 1, buyIndex);
                return Math.max(buyToday, holdToday);
            } else {
                // 买了，
                // 或者观望
                int saleToday = prices[selectIndex] - prices[buyIndex-1];
                int holdToday = maxProfitRecursion(prices, selectIndex + 1, buyIndex);
                return Math.max(saleToday, holdToday);
            }
        }

    }

    public int maxProfit1(int[] prices){
        // [7,1,5,-4,6,4]
        //
        int max = 0;
        int balance = 0;
        int p = 0 ;
        while (p < prices.length){
            // 找到连续递增的数
            if(p == prices.length){
                p++;
                continue;
            }
            if(p+1 < prices.length &&  prices[p] > prices[p+1]){
                p++;
                // 不买
            }else{
                int buyPrice = prices[p++];
                int curMax = buyPrice;
                // 记录这一波峰值。
                while (p < prices.length && prices[p] > buyPrice){
                    curMax = Math.max(curMax,prices[p]);
                    p++;
                }
                balance = curMax - buyPrice;
                max = Math.max(max,balance);
            }
        }
        return max;
    }


    public int maxProfitDp(int[] prices) {
        // base case 最后一天必须卖出进行结算
        // 表示超过长度。没有办法可选.
        // [ 3 4 5 6 7] = > f[4][5]
        // f[4][4] = 起始位 sales
        int[][] dp = new int[prices.length + 1][prices.length + 1];
        for (int selectIndex = prices.length - 1; selectIndex >= 0; selectIndex--) {
            for (int buyIndex = prices.length; buyIndex >= 0; buyIndex--) {
                if(buyIndex > selectIndex) continue;
                if (buyIndex == 0) {
                    // 买或者观望
                    int buyToday = dp[selectIndex + 1][selectIndex+1];
                    int holdToday = dp[selectIndex + 1][buyIndex];
                    dp[selectIndex][buyIndex] =  Math.max(buyToday, holdToday);
                } else {
                    // 买了，
                    // 或者观望
                    int saleToday = prices[selectIndex] - prices[buyIndex - 1];
                    int holdToday = dp[selectIndex+1][buyIndex];
                    dp[selectIndex][buyIndex] = Math.max(saleToday, holdToday);
                }
            }
        }
        return  dp[0][0];
    }


    public int maxProfitRecursionHashMap(int[] prices, int selectIndex, int buyIndex, Map<String, Integer> maps) {
        // base case 最后一天必须卖出进行结算
        // 表示超过长度。没有办法可选、
        if (maps.get(selectIndex + "," + buyIndex) != null) {
            return maps.get(selectIndex + "," + buyIndex);
        }
        if (selectIndex >= prices.length) {
            maps.put(selectIndex + "," + buyIndex, 0);
            return 0;
        } else {
            // 表示之前有买
            if (buyIndex == 0) {
                // 买或者观望
                int buyToday = maxProfitRecursionHashMap(prices, selectIndex + 1, selectIndex + 1, maps);
                int holdToday = maxProfitRecursionHashMap(prices, selectIndex + 1, buyIndex, maps);
                int result = Math.max(buyToday, holdToday);
                maps.put(selectIndex + "," + buyIndex, result);
                return result;
            } else {
                // 买了，
                // 或者观望
                int saleToday = prices[selectIndex] - prices[buyIndex - 1];
                int holdToday = maxProfitRecursionHashMap(prices, selectIndex + 1, buyIndex, maps);
                int result = Math.max(saleToday, holdToday);
                maps.put(selectIndex + "," + buyIndex, result);
                return result;
            }
        }
    }


    public static void main(String[] args) {
//        [3,2,6,5,0,3]
        int[] arrays = new int[]{3,2,6,5,0,100};
        MaxProfitRecursionAndDynamicPrograming solution = new MaxProfitRecursionAndDynamicPrograming();
        System.out.println(solution.maxProfit(arrays));

        System.out.println(solution.maxProfitRecursionHashMap(arrays, 0, 0, new HashMap<>()));

        System.out.println(solution.maxProfitDp(arrays));

        System.out.println(solution.maxProfit1(arrays));

    }
}
