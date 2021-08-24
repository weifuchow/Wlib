package com.weifuchow.commons;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/25 15:37
 */
public enum SourceType {
    A("aaaa version "),
    B("bbbb version "),
    C("cccc version "),
    D("dddd version "),
    E("eeee version "),
    F("ffff version ")
    ;
    private String name;

    SourceType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SourceType{" +
                "name='" + name + '\'' +
                '}';
    }
}
