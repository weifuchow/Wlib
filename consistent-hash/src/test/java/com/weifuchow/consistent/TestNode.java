package com.weifuchow.consistent;

import com.weifuchow.consistent.hash.Node;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Copyright © 2016-2021 Stander robots RIOT platform
 *
 * @Desc TestNode
 * @Author zhouweifu
 * @Date 2022/3/24
 */
public class TestNode implements Node {
    private Integer i;

    public TestNode(int i) {
        this.i = i;
    }

    @Override
    public String getKey() {
        return i + "";
    }

    public static Collection<TestNode> buildNos(int counter){
        List<TestNode> list = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
           list.add(new TestNode(i));
        }
        return list;
    }

}
