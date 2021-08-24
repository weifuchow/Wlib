package com.weifuchow.leecode;

//给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
// 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。

import java.util.Arrays;
import java.util.HashMap;

//输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
//输出：[[7,4,1],[8,5,2],[9,6,3]]
public class RotateGraphic {


    /**
     * //输入：matrix = [
     * [1,2,3],
     * [4,5,6],
     * [7,8,9]
     * ]
     * // a[]
     * //输出：[
     * [7,4,1],
     * [8,5,2],
     * [9,6,3]
     * ]
     */
    public void rotate(int[][] matrix) {
        // 顺时针旋转90度。相当于
        // a[0][0] = a[2][0]
        // a[0][1] = a[1][0]
        // a[0][2] = a[0][0]

        // a[1][0] = a[2][1]
        // a[1][1] = a[1][1]
        // a[1][2] = a[0][1]

        //a[2][0] = a[2][2]
        //a[2][1] = a[1][2]
        //a[2][2] = a[0][2]
        int[][] copyMatrix = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copyMatrix[i] = new int[matrix[0].length];
            for (int j = 0; j < matrix[0].length; j++) {
                copyMatrix[i][j] = matrix[i][j];
            }
        }
        //
        for (int i = 0; i < matrix.length; i++) {
            int length = matrix[i].length;
            for (int j = 0; j < length; j++) {
                matrix[i][j] = copyMatrix[length - 1 - j][i];
            }
        }
    }


    public static void main(String[] args) {

        int[][] board = new int[][]{
                new int[]{1,2,3},
                new int[]{4,5,6},
                new int[]{7,8,9},
        };

        RotateGraphic solution = new RotateGraphic();
        solution.rotate(board);
        for (int i = 0; i < board.length ; i++) {
            System.out.println(Arrays.toString(board[i]));
        }

    }

}
