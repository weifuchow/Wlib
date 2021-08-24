package com.weifuchow.leecode.offer;

class BinarySortTreeLowestCommonAncestor {
    // 示例 1:
    //
    // 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
    //输出: 6
    //解释: 节点 2 和节点 8 的最近公共祖先是 6。

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode bigNode = p.val > q.val ? p : q;
        TreeNode smallNode = bigNode == p ? q : p;
        return lowestCommonAncestorRecursive(root,bigNode,smallNode);
    }
    //    6
    // 2    8
    //  4
    //    5
    public TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode big, TreeNode small) {
        // base case
        if(root == null){
            return null;
        }
        // 如果 root.val >= small.val
        if(root.val >= small.val && root.val <= big.val){
            return root;
        }
        else if(root.val > small.val && root.val > big.val){
            return lowestCommonAncestorRecursive(root.left,big,small);
        }else {
            return lowestCommonAncestorRecursive(root.right,big,small);
        }
    }

    public static void main(String[] args) {

        BinarySortTreeLowestCommonAncestor solution = new BinarySortTreeLowestCommonAncestor();

        TreeNode root =new TreeNode(5);
        root.left = new TreeNode(4);
        TreeNode right = new TreeNode(8);
        root.right = right;
        right.left = new TreeNode(6);

        System.out.println(solution.lowestCommonAncestor(root,right.left,root.right));


    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }
    }
}