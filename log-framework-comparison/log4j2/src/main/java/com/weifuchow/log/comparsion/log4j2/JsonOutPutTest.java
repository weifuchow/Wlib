package com.weifuchow.log.comparsion.log4j2;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/3 14:50
 */
public class JsonOutPutTest {

    private final static Logger logger = LoggerFactory.getLogger(JsonOutPutTest.class);


    public JsonOutPutTest(){
    }

    private Map initMap() {
        Map data = new HashMap();
        data.put("test1", "Hello 你好！" + "\n" + RandomUtil.randomString(200));
        data.put("test8", "Hello 你好1！" + "\n" + RandomUtil.randomString(200));
        data.put("test9", "Hello 你好2！" + "\n" + RandomUtil.randomString(200));
        data.put("test7", "Hello 你好3！" + "\n" + RandomUtil.randomString(200));
        return data;
    }

    public void print(int n){
        for (int i = 0; i < n ; i++) {
            log();
        }
    }

    public void log(){
        logger.info(JSONUtil.toJsonStr(initMap()));
    }

}
