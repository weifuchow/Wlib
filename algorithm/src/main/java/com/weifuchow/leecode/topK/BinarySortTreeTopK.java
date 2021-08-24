package com.weifuchow.leecode.topK;


import java.util.concurrent.atomic.AtomicInteger;

public class BinarySortTreeTopK {

    private static int value = 0;
    public int kthLargest(TreeNode root, int k) {
        // 二叉搜索树topK.
        // 求最大。求完一次更新

        postorder(root,new AtomicInteger(k));
        return value;
    }

    public void postorder (TreeNode root, AtomicInteger k ){
        if(root == null){
            return ;
        }
        postorder(root.right,k);
        if(k.decrementAndGet() == 0){
            value = root.val;
        }

        postorder(root.left,k);
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
