package com.weifuchow.leecode;


import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CountPrimes {


    //统计所有小于非负整数 n 的质数的数量。
    //
    //
    //
    // 示例 1：
    //
    // 输入：n = 10
    //输出：4
    //解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
    //
    //
    // 示例 2：
    //
    // 输入：n = 0
    //输出：0
    //
    //
    // 示例 3：
    //
    // 输入：n = 1
    //输出：0
    public int countPrimes(int n) {
        if(n <= 2){
            return 0;
        }
        // 统计小于n 的质数个数。
        // 质数： 只能被 1 整除的数。
        int sum = 1;
        List<Integer> primesList = new ArrayList<>();
        primesList.add(2);
        for (int i = 3; i < n ; i++) {
            if(checkIsPrimes(i,primesList)){
                primesList.add(i);
                sum++;
            }
        }
        return sum;
    }

    // 2 3 5 7 11 13 17  19  23  29 31
    public boolean checkIsPrimes(int number,List<Integer> primesList){
        // 减少除数范围
        for (int i = 0; i < primesList.size(); i++) {
            if(number % primesList.get(i) == 0){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        CountPrimes solution = new CountPrimes();
        System.out.println(solution.countPrimes(499979));
    }
}
