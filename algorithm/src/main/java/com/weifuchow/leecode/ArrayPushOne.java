package com.weifuchow.leecode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ArrayPushOne {
    // 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
    //输入：digits = [0]
    //输出：[1]
    // [digits] = [9,9,9]
    // [digits] = [1,0,0,0]
    // 你可以假设除了整数 0 之外，这个整数不会以零开头。
    public int[] plusOne(int[] digits) {
        Stack<Integer> stack = new Stack<>();
        int plusFlag = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int val = digits[i] + plusFlag;
            if(val == 10){
                digits[i] = 0;
                plusFlag = 1;
                stack.push(0);
            }else{
                stack.push(val);
                plusFlag = 0;
            }
        }
        if(plusFlag == 1){
            stack.push(1);
        }
        int[] res = new int[stack.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = stack.pop();
        }
        return res;
    }


    public static void main(String[] args) {
        int[] arrays = new int[]{4,3,2,1};
        ArrayPushOne solution = new ArrayPushOne();
        int[] val = solution.plusOne(arrays);
        System.out.println(Arrays.toString(val));
    }

}
