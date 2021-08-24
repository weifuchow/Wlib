package com.weifuchow.leecode.offer.arrays;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FindWordIn2DArray {

//给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
//
//
//
// 例如，在下面的 3×4 的矩阵中包含单词 "ABCCED"（单词中的字母已标出）。

    // word = "ABCCED"
    //输入：board = [
    // ["A","B","C","E"]
    // ["S","F","C","S"]
    // ["A","D","E","E"]
    //输出：true
    //
    public boolean exist(char[][] board, String word) {

        // 算法
        // 记过当前走过的路。如果该路无法到达则走下一步
        char[] current = new char[word.length()];
        HashSet set = new HashSet<>();
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board[i].length ; j++) {
//                 if(findPath(board,current,word,set,j,i)){
//                     return true;
//                 }
                if(findPathV2(board,word,j,i,0)){
                    return true;
                }
            }
        }
        return false;
//        return findPath(board,new char[word.length()],word,new HashSet<>(),0,0);
    }

    public boolean findPath(char[][] board, char[] current, String word,
                            Set<String> path, int locationX, int locationY){

        int rowLength = board.length;
        int perRowLength = board[0].length;
        // base case
        if(locationX < 0 || perRowLength == locationX || rowLength == locationY || locationY < 0){
            return false;
        }
        if(path.contains(locationX+","+locationY)){
            return false;
        }
        // 判断当前值是否满足
        int lastPathSize = path.size();
        char currentChar = board[locationY][locationX];
        if(checkIsInPath(lastPathSize,currentChar,word)){
            current[lastPathSize] = currentChar;
            path.add(locationX+","+locationY);
        }else{
            return false;
//            if(path.size() != 0){
//                return false;
//            }
        }
        //
        if(path.size() == word.length()){
            return true;
        }

        // 上下左右移动
        boolean res =
                findPath(board,current,word,path,locationX+1,locationY) ||
                findPath(board,current,word,path,locationX,locationY + 1) ||
                findPath(board,current,word,path,locationX - 1,locationY) ||
                findPath(board,current,word,path,locationX,locationY - 1);

        if(res == false){
            path.remove(locationX+","+locationY);
        }
        return res;

    }


    // 状态回滚，减少不必要的参数。
    // 通过染色去控制状态回滚。最后无路时返回。
    public boolean findPathV2(char[][] board, String word, int locationX, int locationY,int walkSize){
        // base case
        if(locationX < 0 ||  locationY < 0  ||  locationY >= board.length || locationX >= board[locationY].length  ){
            return false;
        }
        if(word.charAt(walkSize) != board[locationY][locationX]){
            return false;
        }
        if(walkSize == word.length() - 1){
            return true;
        }

        // 未完成输出，结仍需要进行后续节点遍历。将节点染色
        board[locationY][locationX] = 0;
        boolean res =
                findPathV2(board,word,locationX+1,locationY,walkSize+1) ||
                findPathV2(board,word,locationX,locationY + 1,walkSize + 1) ||
                findPathV2(board,word,locationX,locationY - 1,walkSize + 1) ||
                findPathV2(board,word,locationX - 1,locationY,walkSize + 1);
        // 状态回滚
        board[locationY][locationX] = word.charAt(walkSize);
        return res;
    }


    public boolean checkIsInPath(int lastSize,char currentPath,String word){
        char[] wordArray = word.toCharArray();
        return wordArray[lastSize] == currentPath;
    }


    public static void main(String[] args) {
        FindWordIn2DArray solution = new FindWordIn2DArray();
//        char[][] board = new char[][]{
//                new char[]{'A','B','C','E'},
//                new char[]{'S','F','C','S'},
//                new char[]{'A','D','E','E'}
//        };
//        boolean result = solution.exist(board,"BCCED");
//
        char[][] board = new char[][]{
                new char[]{'A','B'}
        };
        boolean result = solution.exist(board,"BA");

        // CAA

//        char[][] board = new char[][]{
//                new char[]{'C','A','A'},
//                new char[]{'A','A','A'},
//                new char[]{'B','C','D'}
//        };
//        boolean result = solution.exist(board,"CDB");
        System.out.println(result);
    }


}
