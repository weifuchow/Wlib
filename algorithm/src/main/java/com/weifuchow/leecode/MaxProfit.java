package com.weifuchow.leecode;


public class MaxProfit {


    //给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
    //
    // 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
    //
    // 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
    //输入: prices = [7,1,5,3,6,4]
    //输出: 7
    //解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
    //     随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
    public int maxProfit(int[] prices) {
        // 暴力遍历
        // 每天买或者不买。
        return 0;
    }

    /**
     * 贪心
     * @param prices
     * @return
     */
    public int maxProfitByGreedy(int[] prices) {
        // 贪心策略。遍历数组，从i节点开始。若i比i+1大。则不买，i++,跳出策略。
        // 若 i 比 i +1大。则股票涨。需要找到最后一次下跌的时候。跳出策略。重新下一轮计算
        int balance = 0;
        int i = 0;
        while (i < prices.length){

            if(i+1 < prices.length &&  prices[i] > prices[i+1]){
                i++;
                // 不买
            }else{
                //
                int buyPrice = prices[i++];
                // 3,5,7,8,9,10
                int lastDayPrice = buyPrice;
                while(i < prices.length && lastDayPrice < prices[i]){
                    lastDayPrice = prices[i];
                    i++;
                }
                balance += prices[i-1] - buyPrice;
            }
        }
        return balance;
    }


    public int maxProfitByPower(int[] prices){
        int max = Integer.MIN_VALUE;
        // 遍历所有情况，要么买要么不买。
        // 如何表示买与不买呢。

        // i = 0 表示不买，i = 1表示买，
        int current = 0;
        for (int i = 0; i < 2; i++) {
            // 如何算收益情况。
            for (int j = 0; j < prices[i]; j++) {
            }
        }
        //
        return max;
    }


    /**
     * 转化为，不买的时候最高，买的时候最高。
     * @param prices
     * @param current
     * @return
     */
    public int maxProfitByPower(int[] prices,int current){

        return 0;
    }

    public static void main(String[] args) {
        int[] prices = new int[]{7,6,4,3,1};
        MaxProfit solution = new MaxProfit();
        System.out.println(solution.maxProfitByGreedy(prices));
    }


}
