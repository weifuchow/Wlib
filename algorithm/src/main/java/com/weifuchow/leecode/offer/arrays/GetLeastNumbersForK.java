package com.weifuchow.leecode.offer.arrays;


//输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。
//
//
//
// 示例 1：
//
// 输入：arr = [3,2,1], k = 2
//输出：[1,2] 或者 [2,1]

// 示例 2：
//
// 输入：arr = [0,1,2,1], k = 1
//输出：[0]

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class GetLeastNumbersForK {

    public int[] getLeastNumbersByHeap(int[] arr, int k) {
        // 求数组中最小的K个数，快排求一批。
        // 将数组分为两批。 一半 小于他 一半大于ta.
        // 用两种方式实现一下。

        // 1.堆排
        // 2.维护一个大小大顶堆。下一个元素插入且堆满时。当前最大值与他比较。peek.如果他比最大值大不插入否者插入。
        if (k == 0) return new int[0];
        PriorityQueue<Integer> queue = new PriorityQueue(k, (o1, o2) -> {
            Integer i1 = (Integer) o1;
            Integer i2 = (Integer) o2;
            return i2.compareTo(i1);
        });

        // 设置一个堆。 size = 4.当 1 2 3 4 0.大顶堆。1 2 3 4 ;
        for (int i = 0; i < arr.length; i++) {
            //
            if (queue.size() < k) {
                queue.add(arr[i]);
            } else {
                int max = queue.peek();
                if (arr[i] < max) {
                    queue.poll();
                    queue.add(arr[i]);
                }
            }
        }
        return queue.stream().mapToInt(Integer::valueOf).toArray();
    }

    public int[] getLeastNumbers(int[] arr, int k) {
        // 求数组中最小的K个数，快排求一批。
        // 将数组分为两批。 一半 小于他 一半大于ta.
        // 用两种方式实现一下。

        // 1.堆排
        // 2.维护一个大小大顶堆。下一个元素插入且堆满时。当前最大值与他比较。peek.如果他比最大值大不插入否者插入。
        if (k == 0) return new int[0];
        return quick(arr, k - 1, 0, arr.length - 1);
    }

    public int[] quick(int[] array, int k, int begin, int end) {
        int mid = splitArrays(array, begin, end);
        if ( mid  == k) {
            return Arrays.copyOfRange(array, 0, k + 1);
        }
        else if ( mid  > k) {
            return quick(array, k, begin, mid - 1);
        } else {
            return quick(array, k, mid + 1, end);
        }
    }


    public int splitArrays(int[] arrays, int begin, int end) {
        // 9 ( 0 - 8)
//        System.out.println("this arrays = > " + Arrays.toString(Arrays.copyOfRange(arrays,begin,end + 1)));
        int random = new Random().nextInt(end - begin + 1) + begin;
        swap(arrays, begin, random);
        int position = begin;
        int positionVal = arrays[position];
//        int originBegin = begin;
//        int originEnd = end;
        // 快速排序从后面开始比较。直到找到小于其前面的值。
        // 5 4 3 2 1 5 7 8 9
        while (begin < end) {
            // 从后面直到找到第一个position 比
            for (; arrays[end] > positionVal && end > begin; end--) {
            }
            //
            swap(arrays, begin, end);
            // 从左往右。找到
            for (; arrays[begin] <= positionVal && end > begin; begin++) {
            }
            swap(arrays, begin, end);
        }
//        System.out.println("left = >" + Arrays.toString(Arrays.copyOfRange(arrays,originBegin,begin)));
//        System.out.println("mid = > [" + arrays[begin] + "] ,position = > " + begin);
//        System.out.println("right = >" + Arrays.toString(Arrays.copyOfRange(arrays,begin + 1,originEnd + 1)));
//        System.out.println(" ---- ");
        return begin;
    }

    public void swap(int[] arrays, int i, int j) {
        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
    }


    public static void main(String[] args) {
        GetLeastNumbersForK solution = new GetLeastNumbersForK();
        int[] arrays = new int[]{3, 2, 1,6,8,9,10,15,80,20};
//        solution.splitArrays(new int[]{1,2,3},1,2);
        System.out.println(Arrays.toString(solution.getLeastNumbers(arrays, 2)));
    }
}
