package com.weifuchow.leecode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class TwoArryasIntersect {

    // 给定两个数组，编写一个函数来计算它们的交集。
    // 输入：nums1 = [1,2,2,1], nums2 = [2,2]
    // 输出：[2,2]
    // 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
    // 输出：[4,9]
    // 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
    // 我们可以不考虑输出结果的顺序。
    public int[] intersect(int[] nums1, int[] nums2) {
        return null;
    }

    public int[] intersectBySort(int[] nums1, int[] nums2) {
        // 输入：nums1 = [1,2,2,1,3,4,4,5], nums2 = [2,2,3,4]
        // 输出：[2,2]
        // 先将其排好序。 1,1,2,2,3,4,4,5  ; 2  3 4
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        // 找到最小开头数组
        int[] minArrays = nums1[0] > nums2[0] ? nums2 : nums1;
        int[] maxArrays = minArrays == nums1 ? nums2 : nums1;
        ArrayList<Integer> results = new ArrayList<>();
//        int index =0;
        int j = 0;
        int i = 0;
        // 数组两边开始遍历。
        while (i < minArrays.length && j < maxArrays.length) {
            // 4,5,10,10,20,25    8,10,10,12,13,15,16,17,30
            // 从第一个开始找到其与第二个数组相同的值，直到比其他。
            // 10 = 8,
            // 从右边开始，要找到第一个大值。直到相等。 10 10，
            while (i < minArrays.length && maxArrays[j] >= minArrays[i]) {
                if (maxArrays[j] == minArrays[i]) {
                    results.add(maxArrays[j]);
                    i++;
                    j++;
                    break;
                }
                i++;
            }
            if(i == minArrays.length){
                continue;
            }
            // 找到右边比左边大的值
            while( j < maxArrays.length && maxArrays[j] <= minArrays[i]){
                // 如果相等。则
                if(maxArrays[j] == minArrays[i]){
                    results.add(maxArrays[j]);
                    j++;
                    break;
                }
                j++;
            }
            i++;
            // 1,1,2,2,3,4,4,5  ; 2 2 3 4
            // 从小数组中找到第一个于其相等的值。
        }
        return results.stream().mapToInt(intVal -> intVal).toArray();
    }

    //
    public int[] intersectByPower(int[] nums1, int[] nums2) {
        // 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
        // 输出：[4,9]
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set.add(nums1[i]);
        }
        for (int i = 0; i < nums2.length; i++) {
            set2.add(nums2[i]);
        }
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            Integer val = iterator.next();
            if (!set2.contains(val)) {
                iterator.remove();
            }
        }
        return set.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        // [1,2,2,1], nums2 = [2,2]
        // [4,9,5], nums2 = [9,4,9,8,4]
        int[] arryas = new int[]{1};
        int[] arryas1 = new int[]{1,2};

        TwoArryasIntersect solution = new TwoArryasIntersect();
        int[] intersect = solution.intersectBySort(arryas, arryas1);
        System.out.println(Arrays.toString(intersect));
    }
}
