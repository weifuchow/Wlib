package com.weifuchow.sort;

import java.util.*;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/6/1 16:09
 */
public class Main {
    public static void main(String[] args) {
        List<int[]> samples = SortHelper.generateArrays();
//        List<ISort> sorts = SortFactory.getAllSort();
//        for (ISort sort : sorts) {
//            SortHelper.compare(sort, samples);
//        }
        ISort sort = SortFactory.getSort("quickV2");
        SortHelper.compare(sort, samples);
    }
}
