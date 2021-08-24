package com.weifuchow.leecode;


import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class StrToInteger {

    public int myAtoi(String s) {
        Long sum = 0L;
        long productFlag = 1l;
        int isNegative = 1;
        boolean isBeginning = false;
        Stack<Integer> stack = new Stack<>();
        char[] arrays = s.toCharArray();
        for (int i = 0; i < arrays.length; i++) {
            if(48 <= arrays[i] && 57>= arrays[i]){
                int val = arrays[i] - 48;
                isBeginning = true;
                if(val == 0 && stack.isEmpty()){
                    continue;
                }else{
                    stack.push(val);
                }
            }
            else if(arrays[i] == ' ' && !isBeginning ){
                continue;
            }
            else if(arrays[i] == '-' && !isBeginning){
                isNegative = -1;
                isBeginning = true;
            }else if(arrays[i] == '+' && !isBeginning ){
                isNegative = 1;
                isBeginning = true;
            }else{
                break;
            }

        }


        while (!stack.isEmpty()){
            // 100000000000000000000000000000 这个怎么处理呢。
            // 000000000000000000000000000001
            if(productFlag >= Integer.MAX_VALUE){
                return isNegative == 1 ? Integer.MAX_VALUE :Integer.MIN_VALUE;
            }
            sum += stack.pop() * productFlag;
            if(sum * isNegative >= Integer.MAX_VALUE ){
                return Integer.MAX_VALUE;
            }
            if(sum * isNegative <= Integer.MIN_VALUE){
                return Integer.MIN_VALUE;
            }
            productFlag = 10 * productFlag;


        }
        sum *= isNegative;
        return sum.intValue();
    }

    public static void main(String[] args) {
        StrToInteger solution = new StrToInteger();
//        String s = "10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000522545459";
//        String s = "00000000000001 0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000111111111";
//        String s= "00000 + -42a1234";
        String s= "2147483646";
        int res = solution.myAtoi(s);
        System.out.println(res);
    }
}
