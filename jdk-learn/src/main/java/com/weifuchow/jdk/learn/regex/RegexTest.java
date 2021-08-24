package com.weifuchow.jdk.learn.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/10 17:09
 */
public class RegexTest {
    public static void main(String[] args) {
        String sql = "insert into table(id,value,name,sdds) values (@i@,@fuck@,@name@,@sdds@)";
//        Map<String,Object> map = new HashMap<>();
//        map.put("@int@",1);
//        map.put("@string@",2);
//
//        for (String key : map.keySet()) {
//            set(map.get(key),builder.get(key).get())
//        };

        // index 1
        System.out.println(sql.replaceAll("@(.*?)@", "?").toString());
    }
}
