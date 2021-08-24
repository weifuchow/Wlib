package com.weifuchow.leecode;

public class BinaryTreeMaxDepth {

    // 二叉树最大深度。
    //   1
    // 2
    // root => 1 , 1 + f(left) + f(right)
    public int maxDepth(TreeNode root) {
       if(root == null){
           return 0;
       }
       // 该树的最大深度 =  1(当前层数) + Max（左子树最大层数 与 右子树最大层数的最大值）
       return 1 + Math.max(maxDepth(root.left),maxDepth(root.right));
    }


    public static void main(String[] args) {
        BinaryTreeMaxDepth solution = new BinaryTreeMaxDepth();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.right = right;
        right.left = new TreeNode(4);
        System.out.println(solution.maxDepth(root));
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
