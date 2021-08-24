package com.weifuchow.leecode;


public class StrIgnoreNoLetterNumberPalindrome {

    //给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
    //
    // 说明：本题中，我们将空字符串定义为有效的回文串。
    //
    // 输入: "A man, a plan, a canal: Panama"
    // 输出: true
    //
    // 输入: "race a car"
    // 输出: false

    // A-Z 65 - 90
    // a-z 97 - 122
    // 0-9 48 - 57
    public boolean isPalindrome(String s) {
        char[] arrays = s.toCharArray();
        int left = 0;
        int right = arrays.length - 1;
        boolean res = true;
        while (left < right) {
            // 找到第一个是数字或字符开头的进行相比
            while (isNotLetterNumberChar(arrays[left]) && left < right) {
                left++;
            }
            while (isNotLetterNumberChar(arrays[right]) && left < right) {
                right--;
            }
            if (!equalIgnoreCaseByChar(arrays[left++],arrays[right--])){
                return false;
            }


        }
        return true;
    }

    public boolean isNotLetterNumberChar(char s) {
        return !((s >= 48 && s <= 57) ||
                (s >= 65 && s <= 90) ||
                (s >= 97 && s <= 122));
    }

    public boolean equalIgnoreCaseByChar(char s, char s1) {
        // 数字直接比较
        // a A
        // A a
        // a a
        if(s < 65){
            return s == s1;
        }else {
            return s == s1 || s == s1 + 32 || s == s1 - 32;
        }
    }


    public boolean isPalindrome4Regex(String s) {
        char[] arrays = s.replaceAll("[^A-Za-z0-9]", "").toCharArray();
        int left = 0;
        int right = arrays.length - 1;
        boolean res = true;
        // 1. 将非字符数字使用正则替换成 空，然后回文
        while (left < right) {
            String leftStr = new String(new char[]{arrays[left]});
            String rightStr = new String(new char[]{arrays[right]});
            if (!leftStr.equalsIgnoreCase(rightStr)) {
                return false;
            }
            left++;
            right--;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println('a');
        StrIgnoreNoLetterNumberPalindrome solution = new StrIgnoreNoLetterNumberPalindrome();
        System.out.println(solution.isNotLetterNumberChar('_'));
        System.out.println(solution.isPalindrome("0P"));
    }
}
