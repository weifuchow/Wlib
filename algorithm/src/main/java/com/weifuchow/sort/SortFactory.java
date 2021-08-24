package com.weifuchow.sort;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/6/1 16:54
 */
public class SortFactory {

    private static Map<String,ISort> sortMap = new HashMap<>();

    static {
        sortMap.put("bubble",new BubbleSort());
        sortMap.put("selection",new SelectionSort());
        sortMap.put("insert",new InsertSort());
        sortMap.put("merge",new MergeSort());
        sortMap.put("binaryInsert",new BinaryInsertSort());
        sortMap.put("bucket",new BucketSort());
        sortMap.put("counting",new CountingSort());
        sortMap.put("heap",new HeapSort());
        sortMap.put("quick",new QuickSort());
        sortMap.put("quickV1",new QuickSortV1());
        sortMap.put("quickV2",new QuickSortV2());
        sortMap.put("radix",new RadixSort());
        sortMap.put("shell",new ShellSort());
    }

    public static ISort getSort(String sortName){
        return sortMap.get(sortName);
    }

    public static List<ISort> getAllSort(){
        return new ArrayList<>(sortMap.values());
    }


}
