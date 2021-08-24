package com.weifuchow.leecode.offer.binary;


public class NumberOfOneBits {

    public int hammingWeight(int n) {
        //输入：n = 11 (控制台输入 00000000000000000000000000001011)
        //输出：3
        //解释：输入的二进制串 00000000000000000000000000001011 中，共有三位为 '1'。

        //   1011      1011
        // & 0001    & 0000  1011
        //   0001
        // 怎么截取最高位数。
        // 异或： 小结运算原则，就是相同得0，不同得1
        // 或：  有1得1.没一得0
        // 与：  两个为1则为1。
        int sum = 0;
        int time = 1;
        for (int i = 0; i < 32; i++) {
            int t = n & (time << i);
            if(t != 0){
                sum++;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int time = 1 << 31;
        System.out.println(time);
        // 11111111111111111111111111111111
        // 00000000000000000000000000000011
        NumberOfOneBits solution = new NumberOfOneBits();
        int n = solution.hammingWeight(-1);
        System.out.println(n);
    }

}
