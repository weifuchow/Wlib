package com.weifuchow.leecode.offer;

import java.util.*;

//输入一棵二叉树和一个整数，打印出二叉树中节点值的和为输入整数的所有路径。从树的根节点开始往下一直到叶节点所经过的节点形成一条路径。
//
//
//
// 示例:
//给定如下二叉树，以及目标和 target = 22，
//
//
//              5
//             / \
//            4   8
//           /   / \
//          11  13  4
//         /  \    / \
//        7    2  5   1
//
//
// 返回:
//
//
//[
//   [5,4,11,2],
//   [5,8,4,5]
//]
public class BinaryTreePathSum {


    private LinkedList<Integer> temp = new LinkedList<>();

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        List<List<Integer>> ls = new ArrayList<>();
        pathSumRecursive4(root,ls,target);
        return ls;
    }

    ////              5
    ////             / \
    ////            4   8
    ////           /   / \
    ////          11  13  4
    ////         /  \    / \
    ////        7    2  5   1
//    public List<List<Integer>> pathSumMustRoot2Leaf(TreeNode root, int target) {
//        // 利用栈
//        Stack<TreeNode> stack = new Stack<>();
//        stack.push(root);
//        List<List<Integer>> ls = new ArrayList<>();
//        while (!stack.isEmpty()){
//            // 到达叶子节点前判断一下
//        }
//    }

     // 利用对象状态恢复。下层执行完之后必须状态回滚。
    //  减少不可变对象产生如str.
    public void pathSumRecursive4(TreeNode node, List<List<Integer>> ls, int target){
        if(node == null){
            return ;
        }

        temp.add(node.val);
        if(node.left == null && node.right == null){
            if(target == node.val){
                ls.add(new ArrayList<>(temp));
            }
            temp.removeLast();
            return;
        }
        pathSumRecursive4(node.left,ls,target - node.val);
        pathSumRecursive4(node.right,ls,target - node.val);
        temp.removeLast();
    }



    public void pathSumRecursive3(TreeNode node, List<List<Integer>> ls, String path, int target){

        if(node == null){
            return ;
        }
        String curPath = node.val + ",";
        String totalPath = path + curPath;
        if(node.left == null && node.right == null){
            if(target == node.val){
                addPath(totalPath,ls);
            }
            return;
        }
        pathSumRecursive3(node.left,ls,totalPath,target - node.val);
        pathSumRecursive3(node.right,ls,totalPath,target - node.val);
    }


    public void pathSumRecursive(TreeNode node, List<List<Integer>> ls, String path, int target){

        if(node == null){
            return;
        }
        // 只需要达到叶子节点前判断
        String curPath = node.val + ",";
        String totalPath = path + curPath;
        // check is Leaf
        if(node.left == null && node.right == null){
            if(checkPath(totalPath,target) == 0){
                addPath(totalPath,ls);
            }


        }
        pathSumRecursive(node.left,ls,totalPath,target);
        pathSumRecursive(node.right,ls,totalPath,target);
    }

    // 本质穷举遍历 中  寻找节点
    // 不是求解
    // fuck 必须为根节点到叶子节点。
    public void pathSumRecursive2(TreeNode node, List<List<Integer>> ls, String path, int target){

        if(node == null){
            return;
        }
        //
        String curPath = node.val + ",";
        String totalPath = path + curPath;
        int result = checkPath(totalPath,target);
        if(result == 0){
            addPath(path+node.val,ls);
            pathSumRecursive(node.left,ls,curPath,target);
            pathSumRecursive(node.right,ls,curPath,target);
        }else if(result > 0){
            pathSumRecursive(node.left,ls,curPath,target);
            pathSumRecursive(node.right,ls,curPath,target);
        }else {
            pathSumRecursive(node.left,ls,totalPath,target);
            pathSumRecursive(node.right,ls,totalPath,target);
        }
    }


    // 0 -1小于，1大于
    public int checkPath(String path,int target){
        String[] arrays = path.split(",");
        int sum = 0;
        for (int i = 0; i < arrays.length ; i++) {
            sum += Integer.parseInt(arrays[i]);
        }
        if(sum == target) return 0;
        else if(sum > target) return 1;
        else return -1;
    }

    public void addPath(String path,List<List<Integer>> ls){
        String[] arrays = path.split(",");
        List<Integer> target = new ArrayList<>();
        ls.add(target);
        for (int i = 0; i < arrays.length; i++) {
            target.add(Integer.parseInt(arrays[i]));
        }
    }


    public static void main(String[] args) {

        ////              5
        ////             / \
        ////            4   8
        ////           /   / \
        ////          11  13  4
        ////         /  \    / \
        ////        7    2  5   1
        BinaryTreePathSum solution = new BinaryTreePathSum();
        int target = 26;
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(4);
        TreeNode right = new TreeNode(8);
        TreeNode leftleft = new TreeNode(11);
        TreeNode rightright = new TreeNode(4);
        root.left = left;
        root.right = right;
        left.left = leftleft;
        leftleft.left = new TreeNode(6);
        leftleft.right = new TreeNode(2);
        right.left = new TreeNode(13);
        right.right = rightright;
        rightright.left = new TreeNode(5);
        rightright.right = new TreeNode(1);


        List<List<Integer>> ls = solution.pathSum(root,target);
        System.out.println(ls);
//        ls.forEach(System.out::println);

    }



    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
