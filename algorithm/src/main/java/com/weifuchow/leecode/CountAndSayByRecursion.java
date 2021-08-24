package com.weifuchow.leecode;


public class CountAndSayByRecursion {

    // countAndSay(1) = "1"
    // countAndSay(n) 是对 countAndSay(n-1) 的描述，然后转换成另一个数字字符串。
    //
    //
    // 前五项如下：
    //
    //
    //1.     1
    //2.     11
    //3.     21
    //4.     1211
    //5.     111221
    //第一项是数字 1
    //描述前一项，这个数是 1 即 “ 一 个 1 ”，记作 "11"
    //描述前一项，这个数是 11 即 “ 二 个 1 ” ，记作 "21"
    //描述前一项，这个数是 21 即 “ 一 个 2 + 一 个 1 ” ，记作 "1211" , 1 1 1 2 2 1
    //描述前一项，这个数是 1211 即 “ 一 个 1 + 一 个 2 + 二 个 1 ” ，记作 "111221"
    public String countAndSay(int n) {
        if(n == 1){
            return "1";
        }else{
            return say(countAndSay(n-1));
        }
    }


    // 描述 1211
    public String say(String number){
        StringBuilder sb = new StringBuilder();
        char[] arrays = number.toCharArray();
        for (int i = 0; i < arrays.length; ) {
            int num = arrays[i++] - 48;
            // 检查后续，有多少与该数字相同
            int counter = 1;
            while (i < arrays.length && num == (arrays[i] - 48) ){
                counter++;
                i++;
            }
            sb.append(counter + "" + num);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        CountAndSayByRecursion solution = new CountAndSayByRecursion();
        System.out.println(solution.say("3322251"));
        System.out.println(solution.countAndSay(4));

    }


}
