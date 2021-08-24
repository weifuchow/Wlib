package com.weifuchow.leecode.offer.arrays;

import java.util.Arrays;

//输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
//
//
//
// 示例 1：
//
// 输入：matrix = [
//  [1,2,3]
// ,[4,5,6],
//  [7,8,9]
// ]
//输出：[1,2,3,6,9,8,7,4,5]
public class SpiralOrderMatrix {

    public int[] spiralOrder(int[][] matrix) {
        // 先 右=> 下 => 左 => 上
        if(matrix == null) return null;
        if(matrix.length == 0) return new int[0];
        int[] res = new int[matrix[0].length * matrix.length];
        int size = 0;
        int roundNumber = 0;
        while (size < res.length) {
            size = roundOrder(matrix, res, size, roundNumber);
            roundNumber++;
        }
        return res;
    }

    public int roundOrder(int[][] matrix, int[] nums, int index, int roundNumber) {
        int i = roundNumber;
        int j = roundNumber;
        // 左 -》 右
        for (; i < matrix[roundNumber].length - roundNumber && nums.length > index; i++) {
            nums[index++] = matrix[j][i];
        }
        // 上 - 》 下
        for (i = i - 1, j++; j < matrix.length - roundNumber && nums.length > index; j++) {
            nums[index++] = matrix[j][i];
        }
        // 右 往左
        for (j = j - 1, i--; i >= roundNumber && nums.length > index; i--) {
            nums[index++] = matrix[j][i];
        }
        // 下往上
        for (i = i + 1, j--; j > roundNumber && nums.length > index; j--) {
            nums[index++] = matrix[j][i];
        }
        return index;
    }

    public static void main(String[] args) {
        SpiralOrderMatrix solution = new SpiralOrderMatrix();
//        int[][] matrix = new int[][]{
//                new int[]{1, 2, 3, 10, 11},
//                new int[]{4, 5, 6, 20, 21},
//                new int[]{7, 8, 9, 30, 31},
//                new int[]{17, 18, 19, 40, 41},
//                new int[]{27, 28, 29, 50, 51},
//        };

        int[][] matrix = new int[][]{
                new int[]{1, 2, 3, 4},
                new int[]{5, 6, 7, 8},
                new int[]{9, 10, 11, 12}
        };
        // [1,2,3,4,8,12,11,10,9,5,6,7]

        int[] res = solution.spiralOrder(matrix);
        System.out.println(Arrays.toString(res));
    }

}
