package com.weifuchow.leecode.rolingwindow;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
public class LongestNoRepeatSubString {


    // aa
    // asvbdv
    public int lengthOfLongestSubstringOptimise(String s){
        // 使用左指针进行控制
        int left = 0;
        int length = 0;
        HashMap<Character,Integer> windowMap = new HashMap<>();
        char[] arrays = s.toCharArray();

        for (int right = 0; right < arrays.length ; right++) {
            char c = arrays[right];
            // abcaeb
            // 发现重复值更新left 值
            // abbac
            // abbb // left
            if(windowMap.get(c) != null){
                // 如果当前的 left 大于之前的值，则去掉
                // 如abba
                // left 已经来到 2的位置,并指向了b
                // 如果遇到了a,则代表遇到之前重复的a.要先判断其的值是否大于当前的left. 小于left的不操作。
                // 等于left 的是什么情况呢。 abb b,left 指向2， map中 b的值为2.则需要更新
                int duplicateValIndex = windowMap.get(c);
                if(left <= duplicateValIndex){
                    left = windowMap.get(c) + 1;
                } // convert to left = Math.max(left,windowMap.get(c) + 1)
            }
            windowMap.put(c,right);
            length = Math.max(length,right - left + 1);

        }

        return length;
    }

    // 最长不重复子串大小
    public int lengthOfLongestSubstring(String s) {

        int length = 0;
        // 窗口大小
        HashMap<Character,Integer> windowMap = new HashMap<>();
        LinkedList window = new LinkedList();
        char[] arrays = s.toCharArray();
        //
        for (int i = 0; i < arrays.length;i++) {
            // 窗口左移
            // aa
            // asvbdv
            char c = arrays[i];
            // 遇到重复，窗口收缩，
            if(windowMap.get(c) != null){
                // 删除重复值之前的数据
                int counter = windowMap.get(c);
                int begin = windowMap.get(window.getFirst());
                for (int j = begin; j <= counter; j++) {
                    window.removeFirst();
                    windowMap.remove(arrays[j]);
                }
            }
            window.addLast(c);
            windowMap.put(c,i);
            length = Math.max(length,window.size());
        }
        return length;
    }


    public static void main(String[] args) {
        LongestNoRepeatSubString solution = new LongestNoRepeatSubString();
        int result1 = solution.lengthOfLongestSubstring("abcabcbb");
        System.out.println(result1);

        int result = solution.lengthOfLongestSubstringOptimise("abcabcbb");
        System.out.println(result);

    }

}
