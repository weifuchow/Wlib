package com.weifuchow.sort;

import java.util.*;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @desc: 提供
 * @author: weifuchow
 * @date: 2021/6/1 10:20
 */
public class SortHelper {

    public static List<int[]> generateArrays() {
        return generateArrays(100000, 100, 1000, -500, 1000);
    }

    public static List<int[]> generateArrays(int totalSize, int minArraySize, int maxArraySize, int min, int max) {
        List<int[]> results = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < totalSize; i++) {
            int arraysize = random.nextInt(maxArraySize - minArraySize) + minArraySize;
            int[] array = new int[arraysize];
            for (int j = 0; j < arraysize; j++) {
                array[j] = random.nextInt(max - min) + min;
            }
            results.add(array);
        }
        return results;
    }

    public static void swap(int[] arrays, int i, int j) {
        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }


    public static void compare(ISort sort, List<int[]> samples) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < samples.size(); i++) {
            int[] orginal = samples.get(i);
            int[] jdkSortArrays = Arrays.copyOf(orginal, orginal.length);
            Arrays.sort(jdkSortArrays);
            //
            int[] weifuchowSortArrays = sort.sort(Arrays.copyOf(orginal, orginal.length));
            //
            boolean compare = Arrays.equals(weifuchowSortArrays, jdkSortArrays);
            if (!compare) {
                System.err.println("Array sort  fail");
                System.err.println("orginal Arrays : " + Arrays.toString(orginal));
                System.err.println("weifuchow Sort result  : " + Arrays.toString(weifuchowSortArrays));
                System.err.println("JDK Sort result  : " + Arrays.toString(jdkSortArrays));
                throw  new RuntimeException("array sort compare errof");
            }
        }
        System.out.println(sort.getClass().getSimpleName() +  " arrays success,use time " +  (System.currentTimeMillis() - begin)  + " ms");
    }


}
