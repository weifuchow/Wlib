package com.weifuchow.leecode;

import java.util.HashMap;
import java.util.HashSet;

// 数字 1-9 在每一行只能出现一次。
// 数字 1-9 在每一列只能出现一次。
// 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
public class IsValidSudoku {

    //[
    // ["8","3",".",".","7",".",".",".","."]
    //new char[]{"6",".",".","1","9","5",".",".","."]
    //new char[]{".","9","8",".",".",".",".","6","."]
    //new char[]{"8",".",".",".","6",".",".",".","3"]
    //new char[]{"4",".",".","8",".","3",".",".","1"]
    //new char[]{"7",".",".",".","2",".",".",".","6"]
    //new char[]{".","6",".",".",".",".","2","8","."]
    //new char[]{".",".",".","4","1","9",".",".","5"]
    //new char[]{".",".",".",".","8",".",".","7","9"]
    // ]
    //输出：false
    //解释：除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。 但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无
    //效的。

    public boolean isValidSudoku(char[][] board) {
        // 遍历所有节点是否存在重复的
        // 算法：
        // 遍历每一个节点是否满足需求。（行 ，列， 3*3）
        // 1.是否满足行列的值是可以被缓存起来。怎么表示为行。可以表示为row、column,3*3 的位置如何被表示。
        // 每一块3 * 3的值。也可以被缓存起来。

        // 2. 接下来怎么将行列存储起来。 key 为 第几行，第几列。第几块。 value 为是否重复 boolean。
        // 3. 这些数据都存起来。怎么验证是否为
        // map 为 行数，列数，块数，value 为set.
        int rows = board.length;
        HashMap<Integer, HashSet<Character>> rowMap = new HashMap<>();
        HashMap<Integer, HashSet<Character>> columnMap = new HashMap<>();
        HashMap<Integer, HashSet<Character>> blockMap = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            int columns = board[i].length;
            for (int j = 0; j < columns; j++) {
                if (!addNumber(rowMap, columnMap, blockMap, i, j, board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean addNumber(HashMap<Integer, HashSet<Character>> rowMap,
                             HashMap<Integer, HashSet<Character>> columnMap,
                             HashMap<Integer, HashSet<Character>> blockMap,
                             int rowNumber,
                             int columnNumber,
                             char number
    ) {
        // 行处理
        return addNumberByRow(rowMap, rowNumber, number)
                && addNumberByColumn(columnMap, columnNumber, number)
                && addNumberByBlock(blockMap, rowNumber, columnNumber, number);

    }

    // 行处理
    public boolean addNumberByRow(HashMap<Integer, HashSet<Character>> rowMap, int rowNumber, char number) {
        return addNumber(rowMap, rowNumber, number);
    }

    // 列处理
    public boolean addNumberByColumn(HashMap<Integer, HashSet<Character>> columnMap, int columnNumber, char number) {
        return addNumber(columnMap, columnNumber, number);
    }


    // 块处理
    public boolean addNumberByBlock(HashMap<Integer, HashSet<Character>> blockMap, int rowNumber, int columnNumber, char number) {
        int block = getBlock(rowNumber, columnNumber);
        return addNumber(blockMap, block, number);
    }

    public int getBlock(int rowNumber, int columnNumber) {
        // block 计算规则，从左 -》 右，再从上下
        // 0 -2 3-5 6 -8
        int rowBlock = rowNumber / 3;
        int columnBlock = columnNumber / 3;
        return columnBlock * 3 + rowBlock;
    }

    private boolean addNumber(HashMap<Integer, HashSet<Character>> columnMap, int columnNumber, char number) {
        HashSet<Character> columnSet = columnMap.get(columnNumber);
        // 初始化
        if (columnSet == null) {
            columnSet = new HashSet<>();
            columnMap.put(columnNumber, columnSet);
        }
        // 重复时会返回false
        if (number == '.') {
            return true;
        }
        return columnSet.add(number);
    }


    public static void main(String[] args) {
        char[][] board = new char[][]{
                new char[]{'8', '3', '.', '.', '7', '.', '.', '.', '.'},
                new char[]{'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                new char[]{'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                new char[]{'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                new char[]{'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                new char[]{'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                new char[]{'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                new char[]{'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                new char[]{'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
//        char[][] board = new char[][]{
//                new char[]{'5', '3', '.', '.', '7', '.', '.', '.', '.'},
//                new char[]{'6', '.', '.', '1', '9', '5', '.', '.', '.'},
//                new char[]{'.', '9', '8', '.', '.', '.', '.', '6', '.'},
//                new char[]{'8', '.', '.', '.', '6', '.', '.', '.', '3'},
//                new char[]{'4', '.', '.', '8', '.', '3', '.', '.', '1'},
//                new char[]{'7', '.', '.', '.', '2', '.', '.', '.', '6'},
//                new char[]{'.', '6', '.', '.', '.', '.', '2', '8', '.'},
//                new char[]{'.', '.', '.', '4', '1', '9', '.', '.', '5'},
//                new char[]{'.', '.', '.', '.', '8', '.', '.', '7', '9'}
//        };

        IsValidSudoku solution = new IsValidSudoku();
        boolean res = solution.isValidSudoku(board);
        System.out.println(res);
    }

}
