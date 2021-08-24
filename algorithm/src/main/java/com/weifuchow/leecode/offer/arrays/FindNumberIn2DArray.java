package com.weifuchow.leecode.offer.arrays;

//在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
// 请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
public class FindNumberIn2DArray {


    // [
    //  [1,   4,  7, 11, 15],
    //  [2,   5,  8, 12, 19],
    //  [3,   6,  9, 16, 22],
    //  [10, 13, 14, 17, 24],
    //  [18, 21, 23, 26, 30]
    //]
    // 一行都按照从左到右递增的顺序排序，
    // 每一列都按照从上到下递增的顺序排序。
    // 找 20
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        // 找 20
        // 从 第一行向 右找。当找到比其大的值，退一步往下找。找到
        if(matrix.length == 0) return false;
        int n = matrix[0].length;
        int m = matrix.length;
        int i = 0;
        int j = 0;
        while (j > -1 && j < n) {
            if (target == matrix[i][j])
                return true;
            if (target > matrix[i][j]) {
                if (j == n - 1) {
                    break;
                }
                j++;
            } else {
                j--;
                i++;
                break;
            }
        }
        if (j == -1 || j == n) return false;
        // 先确定j的位置
        // 回溯.当找到节点比其大的时候，j-- 因为上一个节点已经比较过，上一个节点的左节点更比他小.所以j-- 即可
        // 比其小i++
        while ( (i > -1 && i < m) && (j > -1 && j < n) ) {
            if (target == matrix[i][j])
                return true;
            if (target > matrix[i][j]) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        FindNumberIn2DArray solution = new FindNumberIn2DArray();
        int[][] matrix = new int[][]{
                new int[]{1,   4,  7, 11, 15},
                new int[]{2,   5,  8, 12, 19},
                new int[]{3,   6,  9, 16, 22},
                new int[]{10, 13, 14, 17, 24},
                new int[]{18, 21, 23, 26, 30},
        };
        System.out.println(solution.findNumberIn2DArray(matrix,20));
    }
}
