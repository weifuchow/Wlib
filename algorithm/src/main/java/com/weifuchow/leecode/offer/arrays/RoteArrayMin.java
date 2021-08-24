package com.weifuchow.leecode.offer.arrays;



// 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
// 输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
// 例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
public class RoteArrayMin {

    // 示例 1：
    //
    // 输入：[3,4,5,1,2]
    // 输出：1
    //
    //
    // 示例 2：
    //
    // 输入：[2,2,2,0,1]
    // 输出：0
    public int minArray(int[] numbers) {
        // 递增数据的旋转。 [3 4 5 1 2] 为一个递增。时间复杂度小于O(n)
        // 暴力求解： O(n)
        // 非暴力求解： 还原递增数组。递增数组第一位。
        // 找到开始第一个递减的数。
        // 0 ~ n 位置，递增（K ~ K + n) n~ length ( 1 ~ K)
        // 缩小范围查找
        // [3 4 5 1 2] size = 5  begin = 0  end = 4
        // 确定在哪个区， midIndex = 2, mid = 5;   说明肯定在区间 【 5 1 2】中。
        // 1 2 中， 3 4  mid = 3;剩两个值
        // [ 6 7 1 2 3 4 5], 找最大值。最大值下一个必为
        // [ 1 2 3 4 5 6 7]  1
        // [ 2 3 4 5 6 7 1]
        // [ 4 5 6 7 1 2 3]
        // 0 ~ n 位置单调递增， n ~ n-1 递减。然后右增。且满足任何0 ~n 区间任何值都大于n-1
        // 如何找到递增
        // [ 3 4 5 1 2 ]
        // 第一种情况
        int left = 0;
        int right = numbers.length - 1;
        while (left < right){
            int mid = (left + right) / 2;
            // mid > right. 说明最小值肯定是在这个区域内。
            // 最小值一定不是mid。因为最小值到最后是满足地址的。
            if(numbers[mid] > numbers[right]){
                left = mid + 1;
            }
            // 说明在前半区。
            // mid 递增。说明前面肯定有递减。mid有可能为最小值
            else if(numbers[mid] < numbers[right]){
                right = mid;
            }
            else {
                right --;
            }
        }
        return numbers[left];
    }

    public static void main(String[] args) {
        RoteArrayMin solution = new RoteArrayMin();
        System.out.println(solution.minArray(new int[]{2,2,2,0,1}));
        System.out.println(solution.minArray(new int[]{4,5,1,2,3}));
        System.out.println(solution.minArray(new int[]{1,1,1,1,5}));

    }
}
