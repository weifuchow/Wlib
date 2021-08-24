package com.weifuchow.leecode.offer;

//请完成一个函数，输入一个二叉树，该函数输出它的镜像。 
//
// 例如输入： 
//
//       4
//      / \
//     2   7
//    / \ / \
//   1  3 6 9
//镜像输出： 
//
//       4
//      / \
//     7   2
//    / \ / \
//   9 6 3  1
public class BinaryTreeMirror {

    public TreeNode mirrorTree(TreeNode root) {
        // mirrorTree
        // 左右子树反转
        mirrorTreeRecursive(root);
        return root;
    }

    public void mirrorTreeRecursive(TreeNode root){
        if(root == null){
            return ;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        mirrorTree(root.left);
        mirrorTree(root.right);
    }


    public static void main(String[] args) {
        BinaryTreeMirror solution = new BinaryTreeMirror();

        TreeNode root = new TreeNode(4);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(7);
        root.left = left;
        root.right = right;
        left.left = new TreeNode(1);
        left.right = new TreeNode(3);

        right.left = new TreeNode(6);
        right.right = new TreeNode(9);

        TreeNode root1 = solution.mirrorTree(root);
        System.out.println(root1);
    }


    public static  class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
