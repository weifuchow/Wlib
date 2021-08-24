package com.weifuchow.sort;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/6/1 17:12
 */
public class InsertSort implements  ISort{
    @Override
    public int[] sort(int[] arrays) {
        // 先局部到整体
            // 将数据分为有序部分，待排序部门
            // 假定前5个数有序。第6个待插入到里面中。 1,3,5,7,9 6
            // 如何插入到正确的位置,通过与最后面两两交换。找到要插入的位置。
            // 如何
            for (int sortedIndex = 1; sortedIndex < arrays.length;sortedIndex++) {
                for (int i = sortedIndex; i > 0; i--) {
                    // 若待插入的数大于已排序列中的最大一个。完成该队列排序
                    if (arrays[i] > arrays[i - 1]) {
                        break;
                    }
                    // 若待插入的数 小于 已排序列的最后一个。交换。直到找到被他打。
                    if (arrays[i] < arrays[i - 1]) {
                        SortHelper.swap(arrays, i, i - 1);
                    }
                }
            }



        return arrays;
    }
}
