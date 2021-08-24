package com.weifuchow.leecode;


public class LongestCommonPrefix {
    //编写一个函数来查找字符串数组中的最长公共前缀。
    // 如果不存在公共前缀，返回空字符串 ""。
    //输入：strs = ["flower","flowwer" "flow","flight"]
    //输出："fl"

    public String longestCommonPrefix(String[] strs) {
        // 算法 最长公共前缀。
        if(strs.length == 0){
            return "";
        }else if(strs.length == 1){
            return strs[0];
        }else {
            // 前比较头两个
            String prefix = getPrefix(strs[0],strs[1]);
            // prefix
            for (int i = 2; i < strs.length; i++) {
                prefix = getPrefix(prefix,strs[i]);
            }
            return prefix;
        }
    }

    public String getPrefix(String str0,String str1){
        StringBuilder prefix = new StringBuilder();
        int i =0;
        char[] arrays = str0.toCharArray();
        char[] arrays1 = str1.toCharArray();
        while ((i < arrays.length && i < arrays1.length) && arrays[i] == arrays1[i]){
            prefix.append(arrays[i]);
            i++;
        }
        return prefix.toString();
    }

    public static void main(String[] args) {
        LongestCommonPrefix solution = new LongestCommonPrefix();
//        System.out.println(solution.getPrefix("flow","flight"));
        System.out.println(solution.longestCommonPrefix(new String[]{"dog","racecar","car"}));
    }

}
