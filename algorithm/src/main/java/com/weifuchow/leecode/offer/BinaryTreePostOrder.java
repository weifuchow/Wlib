package com.weifuchow.leecode.offer;


import java.util.Stack;

public class BinaryTreePostOrder {

    // 某二叉搜索树的后序遍历 = 》
    // 观察二叉树搜索树 后序遍历的特点
    //       5
    //     /   \
    //   3      6
    //  / \
    // 1   4
    // 1 4 3 6 5

    //     5
    //    /
    //   4
    //  /
    // 2
    // 2 4 5
    //
    //    4
    //   / \
    //  2   5


    // 1 4 3 6 5

    //  1 6 3

    // 通过后序遍历能不能还原数组
    public boolean verifyPostorder(int[] postorder) {
        // 通过后序遍历还原。不行这里都省略了空。无法还原。

        // 左右 中
        // 中右 左

        //
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < postorder.length; i++) {
            // 输入: [1,6,3,2,5]
            // 5 2 3 6 1
            // 1 叶子节点
            // 6 为叶子节点
            stack.add(postorder[i]);
        }
        return true;
    }

    // 比较两个节点
    public boolean checkPostOrder(int[] postorder,int leaf,int root){
//        if(root == leaf) return true;
        return true;

    }

    // 是否二叉搜素树。两个条件
    // 每一个节点都返回
    //     10
    //    /  \
    //   5   15
    //       / \
    //      6  20
    public boolean isValidBST(TreeNode root) {
        if(root == null){
            return  true;
        }
        //
        if(root.left != null){
            if(root.left.val >= root.val) return false;
        }

        if(root.right != null){
            if(root.right.val <= root.val) return false;
        }

        return isValidBST(root.left) && isValidBST(root.right);
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
