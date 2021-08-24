package com.weifuchow.learn;

import java.util.Arrays;

public class TestQuick {
    public static void main(String[] args) {
        int[] arrays = new int[]{7,6,5,4,3,2,0,1,7};
        quickRerision(arrays, 0, arrays.length - 1);
        System.out.println(Arrays.toString(arrays));

    }

    public static void quickRerision(int[] arrays,int left ,int right){
        if(left >= right){
            return ;
        }else{
            int position = splitTwoPath(arrays, left, right);
            quickRerision(arrays, left, position - 1);
            quickRerision(arrays, position + 1, right);
        }
    }

    public static int splitTwoPath(int[] arrays,int left,int right){
        int postition = arrays[left];
        while(left < right){
            // 找到第一个
            for(;arrays[right] > arrays[left] && right > left ; right --){
            }
            // swap
            swap(arrays, right, left);
            // 从后面找，
            for(;arrays[left] <= arrays[right] && right > left; left++){

            }
            swap(arrays, right, left);
        }
        System.out.println(Arrays.toString(arrays));
        System.out.println("postion => " + right);
        return right;
    }

    public static void swap(int[] arrays,int i,int j){
        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }

}