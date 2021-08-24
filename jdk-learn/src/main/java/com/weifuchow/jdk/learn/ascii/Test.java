package com.weifuchow.jdk.learn.ascii;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/2/25 14:55
 */
public class Test {

    static Map<String,String> map = new HashMap<>();
    static {
        map.put("\\n","\n");
        map.put("\\t","\t");
        map.put("\\|","|");
    }

    // \\n = > '\'+'n' 92,110
    // \是转义
    public static void main(String[] args) {
        char c = '\n';
        System.out.println((int)c);


        char es = '\\';  // 92
        System.out.println((int) es);

        char n = 'n'; // 110
        System.out.println((int)n);

        //
        String s = "a\\|b";

        int flag = s.indexOf("\\");
        char charFlag = s.charAt(flag+1);
        System.out.println("charflag " + charFlag);
        String cc = map.get("\\" + charFlag);

        System.out.println(s.replace("\\"+ charFlag ,cc));

        // "\\" + charflag => ascii ,定义一个映射
        String test = "\\" + "n";

    }




}
