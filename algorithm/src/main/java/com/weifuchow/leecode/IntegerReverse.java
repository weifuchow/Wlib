package com.weifuchow.leecode;

import java.util.Stack;

//给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
// 如果反转后整数超过 32 位的有符号整数的范围 [−231, 231 − 1] ，就返回 0。
// 假设环境不允许存储 64 位整数（有符号或无符号）。
public class IntegerReverse {

    //输入：x = 123
    //输出：321
    public int reverse(int x) {
        int result = x;
        // 流程： x = 123,x %10, 3入栈, x= x/10,循环直到x < 10;栈中 1 2 3.
        // 循环栈。弹出。每次弹出*10+sum
        // 输入：x = -123
        // 输出：-321
        int negativeFlag = x < 0 ? -1 : 1 ;
        x = Math.abs(x);
        Stack<Long> stack = new Stack<>();
        while (x > 0){
            stack.push(x % 10l);
            x = x/10;
        }
        //
        Long sum = 0l;
        int product = 1;
        while (!stack.isEmpty()){
            sum += product * stack.pop();
            if(sum > Integer.MAX_VALUE){
                return  0;
            }
            product *= 10;
        }
        int sumInt = sum.intValue();
        return sumInt * negativeFlag;
    }

    public static void main(String[] args) {
        IntegerReverse solution = new IntegerReverse();
        int nums = 1534236469;
        // 2147483647
        // 1534236469
        if(9646324351l > Integer.MAX_VALUE){
            System.out.println("max ");
        }

        int result = solution.reverse(nums);
        System.out.println(result);
    }
}
