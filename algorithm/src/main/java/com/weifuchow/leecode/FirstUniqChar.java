package com.weifuchow.leecode;


public class FirstUniqChar {

    // s = "leetcode"
    //返回 0
    //
    //s = "loveleetcode"
    //返回 2
    // 算法：hashmap.遍历每一个元素，存进数组中。
    public int firstUniqChar(String s) {
        // 双重循环
        char[] arrays = s.toCharArray();
        int length = arrays.length;
        for (int i = 0; i < length; i++) {
            boolean isRepeat = false;
            for (int j = 0; j < length ; j++) {
                if(i !=j && arrays[i] == arrays[j] ){
                    isRepeat = true;
                    break;
                }
            }
            if(!isRepeat){
                return i;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        FirstUniqChar solution = new FirstUniqChar();
        int res = solution.firstUniqChar("aabb");
        System.out.println(res);
    }
}
