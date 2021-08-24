package com.weifuchow.leecode.offer.arrays;


import java.util.Arrays;
import java.util.Random;

public class PrintNumberOrder {

    // 输入: n = 1
//输出: [1,2,3,4,5,6,7,8,9]
    public int[] printNumbers(int n) {
        if(n == 0) return new int[0];
        int maxSize = getMaxNumberByN(n);
        int[] arrays = new int[maxSize];
        for (int i = 1; i <= maxSize ; i++) {
            arrays[i -1 ] = i;
        }
        return arrays;
    }


    public int getMaxNumberByN(int n){
        long now = System.currentTimeMillis();
        int max = 9;
        int wei = 1;
        // 1 9
        // 2 99 = 90 + 9;
        // 3 999
        for (int i = 2; i <= n ; i++) {
            wei = (wei << 3) + (wei << 1);
            max +=  (wei << 3) + wei;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println((10 << 3) + (10 << 1));
        PrintNumberOrder solution = new PrintNumberOrder();
        solution.printNumbers(11);
//        System.out.println(Arrays.toString(solution.printNumbers(5)));

        solution.test1(); //16543 ms !
        solution.test2(); // 16526 ms !

    }


    public void test1(){
        long now = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int s = random.nextInt(9999999);
            int k  = s * 8;
        }
        System.out.println((System.currentTimeMillis() - now) + " ms !" );
    }

    public void test2(){
        long now = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int s = random.nextInt(9999999);
            int k  = s << 3;
        }
        System.out.println((System.currentTimeMillis() - now) + " ms !" );
    }

}


