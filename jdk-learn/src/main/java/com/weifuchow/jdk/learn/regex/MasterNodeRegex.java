package com.weifuchow.jdk.learn.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/15 11:35
 */
public class MasterNodeRegex {

    public static void main(String[] args) {
        String text = "2021-05-14 16:17:40 [epollEventLoopGroup-3-5] INFO  c.d.a.e.d.m.c.masterhub.ServerImpl - log to send  is {\"componentList\":[],\"jobInfo\":{\"periodInputNum\":0,\"outputNum\":0,\"errorNum\":0,\"opState\":2,\"periodOutputNum\":0,\"currentTime\":1620980255068,\"inputNum\":0,\"instanceId\":\"7bb7460f1b0040d1be1254d5e4177fa61620980254769\",\"periodErrorNum\":0,\"startTime\":1620980254974,\"runTime\":94,\"moduleId\":\"total\",\"syncDelay\":0},\"taskInfo\":{\"jobId\":\"7bb7460f1b0040d1be1254d5e4177fa61620980254769\",\"taskType\":\"10\",\"tenantId\":\"63d606a43a5d4f9f82e64b4078df8a2e\",\"taskName\":\"dan_sql-sql-çŚťçşż-ĺťşčĄ¨\",\"taskId\":\"7bb7460f1b0040d1be1254d5e4177fa6\"},\"taskStatus\":32}";
        String parse = "%date [%thread] %level %logger - %msg";

        Matcher matcher = Pattern.compile("(?<date>.{19}) \\[(?<thread>.*?)] (?<level>.*?) {1,2}(?<class>.*?) - (?<content>[\\s\\S]*)").matcher(text);
        int matcher_start = 0;
        while (matcher.find(matcher_start)){
            System.out.println("date\t" + matcher.group("date"));
            System.out.println("thread\t" + matcher.group("thread"));
            System.out.println("level\t" + matcher.group("level"));
            System.out.println("class\t" + matcher.group("class"));
            System.out.println("content\t" + matcher.group("content"));
            matcher_start = matcher.end();
        }
    }

}
