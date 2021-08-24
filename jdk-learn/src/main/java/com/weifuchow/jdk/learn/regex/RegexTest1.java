package com.weifuchow.jdk.learn.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/11 10:38
 */
public class RegexTest1 {
    public static void main(String[] args) {
        String sql = "insert into table(id,value,name,sdds) values (@i@,@fuck@,@name@,@sdds@)";
        Matcher matcher = Pattern.compile("@(?<element>.*?)@").matcher(sql);
        int matcher_start = 0;
        while (matcher.find(matcher_start)){
            System.out.println(matcher.group("element"));
            matcher_start = matcher.end();
        }

        String text = "jdbc:db2://10.201.0.12:50000/";

        System.out.println(text.substring(text.lastIndexOf("/") + 1));

        String text1 = "jdbc:sybase:Tds:10.201.0.15:5000/?EnableBatchWorkaround=true;ENABLE_BULK_LOAD=true";
        System.out.println(text1.substring(text1.lastIndexOf("/") + 1, text1.lastIndexOf("?")));

    }
}
