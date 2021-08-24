package com.weifuchow.leecode;


import java.util.*;

public class BinaryTreeLevelOrder {


    //    3
    //   / \
    //  9  20
    //    /  \
    //   15   7
    public List<List<Integer>> levelOrder(TreeNode root) {
        //
        // 返回其层序遍历结果：
        //[
        //  [3],
        //  [9,20],
        //  [15,7]
        //]
        List<List<Integer>> ls = new ArrayList<>();
        if (root == null) return ls;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        //
        while (!queue.isEmpty()){
            List<TreeNode> levelNode = new ArrayList<>();
            List<Integer> levelData = new ArrayList<>();
            ls.add(levelData);
            // 弹出所有的队列
            while (!queue.isEmpty()){
                levelData.add(queue.peek().val);
                levelNode.add(queue.poll());
            }
            // 改层所有树。的左右孩子入队列
            for (int i = 0; i < levelNode.size(); i++) {
                if(levelNode.get(i).left != null){
                    queue.add(levelNode.get(i).left);
                }
                if(levelNode.get(i).right != null){
                    queue.add(levelNode.get(i).right);
                }
            }
        }
        return ls;
    }

    public List<List<Integer>> levelOrder2(TreeNode root) {
        //
        // 返回其层序遍历结果：
        //[
        //  [3],
        //  [9,20],
        //  [15,7]
        //]
        List<List<Integer>> ls = new ArrayList<>();
        if (root == null) return ls;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        //
        while (!queue.isEmpty()){
            List<Integer> levelData = new ArrayList<>();
            ls.add(levelData);
            int lastLevelSize = queue.size();
            // fucking know queue size
            //
            for (int i = 0; i < lastLevelSize; i++) {
                TreeNode node = queue.poll();
                levelData.add(node.val);
                if(node.left != null){
                    queue.add(node.left);
                }
                if(node.right != null){
                    queue.add(node.right);
                }
            }
            //

        }
        return ls;
    }


    public static void main(String[] args) {
        BinaryTreeLevelOrder solution = new BinaryTreeLevelOrder();
        TreeNode root = new TreeNode(3);
        TreeNode left = new TreeNode(9);
        TreeNode right = new TreeNode(20);
        root.left = left;
        root.right = right;
        //
//        left.left = new TreeNode(3);
        //
        right.left = new TreeNode(15);
        right.right = new TreeNode(7);
        System.out.println(solution.levelOrder2(root));
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
