package com.weifuchow.leecode;


public class ReverseString {


    //编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
    //
    // 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
    //
    // 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
    //
    //
    //
    // 示例 1：
    //
    // 输入：["h","e","l","l","o"]
    //输出：["o","l","l","e","h"]
    //
    //
    // 示例 2：
    //
    // 输入：["H","a","n","n","a","h"]
    //输出：["h","a","n","n","a","H"]


    // a,b,c,d,e  0,4 1,3 2,2
    // a,b,c,d,e,f   0,5 ,1,4,2,3
    public void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right){
            swap(s,left++,right--);
        }
    }

    public void swap(char[] str,int i,int j){
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }

    public static void main(String[] args) {
        char[] arrays = new char[]{'H','a','n','e','n','a','h'};
        ReverseString solution = new ReverseString();
        solution.reverseString(arrays);
        System.out.println(arrays);
    }

}
