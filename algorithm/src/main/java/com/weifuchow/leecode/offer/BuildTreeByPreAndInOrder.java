package com.weifuchow.leecode.offer;


import java.util.Arrays;

//输入某二叉树的前序遍历和中序遍历的结果，请构建该二叉树并返回其根节点。
//
// 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。 
public class BuildTreeByPreAndInOrder {


    //Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
    //Output: [3,9,20,null,null,15,7]

    //       3
    //      / \
    //        20
    //         / \
    //        15  7
    // 前序是，  输出，左 , 右   [3,20,15,7]
    // 后序是   先左，输出，再后  [3,15,20,7]
    // 构建一颗二叉树的核心是，确定节点。 先序是可以确定根节点，
    // 那么。3比为根节点。中序可得左树为9. 9后面必为右节点。问题规模缩小至 【20 15 7】 【15 20 7】、20位第一个根节点，则15位左，7为右
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeRecursiveByCount(preorder,inorder);
    }

    public TreeNode buildTreeRecursive(int[] preorder, int[] inorder) {
        // base case
        if (preorder == null && inorder == null || (preorder.length == 0 && inorder.length ==0) ) {
            return null;
        }
        // getRoot
        int rootVal = preorder[0];
        TreeNode root = new TreeNode(rootVal);
        // 确定 left tree 和 right Tree
        // 确定 leftTree 范围。
        // 找到root
        // [1 ,2 ,3]
        // [3 ,2, 1]   2是他的最左子树
        Integer inOrderLeftEndIndex = null;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootVal) {
                inOrderLeftEndIndex = i;
                break;
            }
        }
        //
        if(inOrderLeftEndIndex != 0) {
            Integer preOrderLeftEndIndex = null;
            for (int j = 1; j < preorder.length; j++) {
                if (preorder[j] == inorder[inOrderLeftEndIndex - 1]) {
                    preOrderLeftEndIndex = j;
                    break;
                }
            }
            // 开始截取
            int[] preLeft = Arrays.copyOfRange(preorder, 1, preOrderLeftEndIndex + 1);
            int[] inLeft = Arrays.copyOfRange(inorder, 0, inOrderLeftEndIndex);
            root.left = buildTreeRecursive(preLeft, inLeft);

            int[] preRight = Arrays.copyOfRange(preorder, preOrderLeftEndIndex + 1, preorder.length);
            int[] inRight = Arrays.copyOfRange(inorder, inOrderLeftEndIndex + 1, inorder.length);
            root.right = buildTreeRecursive(preRight, inRight);
        }else {
            // 只有右树
            int[] preRight = Arrays.copyOfRange(preorder,  1, preorder.length);
            int[] inRight = Arrays.copyOfRange(inorder, 1, inorder.length);
            root.right = buildTreeRecursive(preRight, inRight);
        }
        return root;
    }


    public TreeNode buildTreeRecursiveByCount(int[] preorder, int[] inorder) {
        // base case
        if (preorder == null && inorder == null || (preorder.length == 0 && inorder.length ==0) ) {
            return null;
        }
        // getRoot
        int rootVal = preorder[0];
        TreeNode root = new TreeNode(rootVal);
        // 确定 left tree 和 right Tree
        // 确定 leftTree 范围。
        // 找到root
        // [1 ,2 ,3]
        // [3 ,2, 1]   2是他的最左子树
        Integer inOrderLeftEndIndex = null;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootVal) {
                inOrderLeftEndIndex = i;
                break;
            }
        }
        //
        if(inOrderLeftEndIndex != 0) {
            // 开始截取
            int[] preLeft = Arrays.copyOfRange(preorder, 1, inOrderLeftEndIndex + 1);
            int[] inLeft = Arrays.copyOfRange(inorder, 0, inOrderLeftEndIndex);
            root.left = buildTreeRecursiveByCount(preLeft, inLeft);

            int[] preRight = Arrays.copyOfRange(preorder, inOrderLeftEndIndex + 1, preorder.length);
            int[] inRight = Arrays.copyOfRange(inorder, inOrderLeftEndIndex + 1, inorder.length);
            root.right = buildTreeRecursiveByCount(preRight, inRight);
        }else {
            // 只有右树
            int[] preRight = Arrays.copyOfRange(preorder,  1, preorder.length);
            int[] inRight = Arrays.copyOfRange(inorder, 1, inorder.length);
            root.right = buildTreeRecursiveByCount(preRight, inRight);
        }
        return root;
    }



    public static void main(String[] args) {
        //    1
        //  2
        // 3
//        int[] preOrder = new int[]{1,2,3};
//        int[] inOrder = new int[]{3,2,1};
        int[] preOrder = new int[]{3,20,15,7};
        int[] inOrder = new int[]{3,15,20,7};
        // [3,9,20,15,7], inorder = [9,3,15,20,7]
        BuildTreeByPreAndInOrder solution = new BuildTreeByPreAndInOrder();
        TreeNode root = solution.buildTree(preOrder,inOrder);
        // 更优的方案，4个指针，
        // 一个pre begin end
        // 一个in  begin end
        System.out.println(root);

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
