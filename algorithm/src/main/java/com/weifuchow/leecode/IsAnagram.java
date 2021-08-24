package com.weifuchow.leecode;


import java.util.HashMap;

//给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
//
//
//
// 示例 1:
//
//
//输入: s = "anagram", t = "nagaram"
//输出: true
//
//
// 示例 2:
//
//
//输入: s = "rat", t = "car"
//输出: false
//
//
//
// 提示:
public class IsAnagram {

    public boolean isAnagram(String s, String t) {
        if(s.length() != t.length()){
            return false;
        }
        char[] arrays = s.toCharArray();
        HashMap<Character,Integer>  characterCounterMap = new HashMap<>();
        for (int i = 0; i < arrays.length; i++) {
            if(characterCounterMap.containsKey(arrays[i])){
                characterCounterMap.put(arrays[i],characterCounterMap.get(arrays[i]) + 1);
            }else{
                characterCounterMap.put(arrays[i],1);
            }
        }
        char[] arrays1 = t.toCharArray();
        for (int i = 0; i < arrays1.length; i++) {
            Integer val = characterCounterMap.get(arrays1[i]);
            if(val == null){
                return false;
            }else{
                if(val == 1){
                    characterCounterMap.remove(arrays1[i]);
                }else{
                    characterCounterMap.put(arrays1[i],val - 1);
                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        IsAnagram solution = new IsAnagram();
        String s = "a";
        String t = "ab";
        System.out.println(solution.isAnagram(s,t));
    }
}
