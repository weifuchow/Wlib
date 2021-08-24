package com.weifuchow.leecode;


public class StringIndexOf {

    // 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。如
    //果不存在，则返回 -1 。

    public int strStr(String haystack, String needle) {
        //输入：haystack = "helxllo", needle = "ll"
        //输出：2
        //输入：haystack = "aaaaa", needle = "bba"
        //输出：-1
        if(needle.length() == 0){
            return 0;
        }
        if(needle.length() > haystack.length()){
            return -1;
        }else{
            char[] arrays = haystack.toCharArray();
            char[] others = needle.toCharArray();
            int result = 0;
            for (int i = 0,j = 0; i < arrays.length; i++) {
                // 命中第一个匹配的记录
                if(arrays[i] == others[j]){
                    // 比较后面记录是否相等。
                    result = i;
                    int k = 0;
                    for (; k < others.length ; k++) {
                        if(i+k == arrays.length){
                            return  -1;
                        }
                        // 如果不想等。i++.重复
                        if(arrays[i+k] != others[j+k]){
                            break;
                        }
                    }
                    if(k == others.length){
                        return result;
                    }
                }

            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        String s = "我你";
//        for (int i = 0; i < s.toCharArray().length ; i++) {
//            System.out.println(s.toCharArray()[i] - 0);
//            // char
//        }
//        System.out.println(Character.MAX_VALUE - 0);
//        // java 中char 最大的16bit,2个字节，采用unionCode 编码
        System.out.println("a".indexOf(""));

        StringIndexOf solution = new StringIndexOf();
        int result = solution.strStr("a","");
        System.out.println(result);

    }
}
