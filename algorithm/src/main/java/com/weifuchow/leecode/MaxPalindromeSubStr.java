package com.weifuchow.leecode;

/**
 * weifuchow only.
 *
 * @author: weifuchow
 * @date: 2021/7/2 10:48
 */
public class MaxPalindromeSubStr {

    public String longestPalindrome(String s){
        int max = 0;
        String maxPalindrome ="";
        int right = s.length();
        for (int i = 0; i < right; i++) {
            for (int j = i + 1; j <= right; j++) {
                if(j - i + 1 <= max){
                    continue;
                }
                int cur = checkIsPalindrome(s.substring(i,j));
                if(cur > max){
                    max = cur;
                    maxPalindrome = s.substring(i,j);
                }
            }
        }
        return maxPalindrome;
    }

    public static void main(String[] args) {
        MaxPalindromeSubStr solution = new MaxPalindromeSubStr();

        System.out.println(solution.checkIsPalindrome("aaabbzbbaaa"));
        String str = "a";
//        System.out.println(str.substring(0,2));
        System.out.println(solution.longestPalindrome(str));
    }

    public  int checkIsPalindrome(String s){
        // aaab   baaa 8 (0-3) (4-7)
        // aaab x baaa
        int size = s.length();
        int left = 0;
        int right = size -1;
        while(left < right){
            if(s.charAt(left) != s.charAt(right)){
                return -1;
            }
            left++;
            right--;
        }
        return size;
    }


}
