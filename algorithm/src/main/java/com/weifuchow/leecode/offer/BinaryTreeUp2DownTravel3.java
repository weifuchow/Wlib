package com.weifuchow.leecode.offer;


import java.util.*;

public class BinaryTreeUp2DownTravel3 {



    //请实现一个函数按照之字形顺序打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右到左的顺序打印，第三行再按照从左到右的顺序打印，其他行以此类推。
    // 例如:
    //给定二叉树: [3,9,20,null,null,15,7],
    //
    //     3
    //    / \
    //   9  20
    //  /  /  \
    // 7  15   7
    //   /    / \
    //  2    3  4
    // 返回其层次遍历结果：
    //
    // [
    //  [3],
    //  [20,9],
    //  [15,7]
    //]
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ls = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        if(root != null) {
            queue.add(root);
        }
        int i = 0;
        while (!queue.isEmpty()){
            List<Integer> levelLs = new ArrayList<>();
            ls.add(levelLs);
            int lastLevelSize = queue.size();
            // left 从对头弹出。一边弹出一边入栈。记录上一层大小
            if(i % 2 == 0){
                for (int j = 0; j < lastLevelSize ; j++) {
                    TreeNode node = queue.removeFirst();
                    levelLs.add(node.val);
                    // 将其 左右 入队
                    if(node.left != null){
                        queue.addLast(node.left);
                    }
                    if(node.right != null){
                        queue.addLast(node.right);
                    }
                }
            }
            // right 从队尾弹出
            else{
                for (int j = 0; j < lastLevelSize ; j++) {
                    TreeNode node = queue.removeLast();
                    levelLs.add(node.val);
                    // 将其 左右 入队
                    if(node.right != null){
                        queue.addFirst(node.right);
                    }
                    if(node.left != null){
                        queue.addFirst(node.left);
                    }
                }
            }
            i++;
        }
        return ls;
    }

    public static void main(String[] args) {
        BinaryTreeUp2DownTravel3 solution = new BinaryTreeUp2DownTravel3();
        TreeNode root =new TreeNode(1);
        root.left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.right = right;
        //
        TreeNode rightLeft = new TreeNode(4);
        //
        right.left = rightLeft;
        rightLeft.left = new TreeNode(55);
        rightLeft.right = new TreeNode(33);

        System.out.println(solution.levelOrder(null));
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
