package com.weifuchow.leecode.offer;


//输入一棵二叉树的根节点，判断该树是不是平衡二叉树。如果某二叉树中任意节点的左右子树的深度相差不超过1，那么它就是一棵平衡二叉树。 
//
// 
//
// 示例 1: 
//
// 给定二叉树 [3,9,20,null,null,15,7] 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7 
//
// 返回 true 。 
public class BinaryTreeIsBalanced {

    public boolean isBalanced(TreeNode root) {
        // 找出两个左右子树最大深度
        return isBalancedRecursive(root).isBalanced();
    }


    // 构造返回值，不一定是根据所要求。这里所隐含信息是左右的树的高度信息。每一颗树的最大高度
    public Info isBalancedRecursive(TreeNode root) {
        //
        if (root == null) {
            Info info = new Info();
            info.setBalanced(true);
            return info;
        }
        // 构造返回值。
        else {
            Info leftInfo = isBalancedRecursive(root.left);
            Info rightInfo = isBalancedRecursive(root.right);
            Info curInfo = new Info();
            if (leftInfo.isBalanced() && rightInfo.isBalanced() && Math.abs(leftInfo.getMaxDepth() - rightInfo.getMaxDepth()) <= 1) {
                curInfo.setLeftDepth(leftInfo.getMaxDepth() + 1);
                curInfo.setRightDepth(rightInfo.getMaxDepth()  + 1);
                curInfo.setMaxDepth(1 + Math.max(leftInfo.getMaxDepth(), rightInfo.getMaxDepth()));
                curInfo.setBalanced(true);
            } else {
                curInfo.setBalanced(false);
            }
            return curInfo;
        }

    }

    public class Info {
        int leftDepth;
        int rightDepth;
        int maxDepth;
        boolean isBalanced;

        @Override
        public String toString() {
            return "Info{" +
                    "leftDepth=" + leftDepth +
                    ", rightDepth=" + rightDepth +
                    ", maxDepth=" + maxDepth +
                    ", isBalanced=" + isBalanced +
                    '}';
        }

        public int getLeftDepth() {
            return leftDepth;
        }

        public void setLeftDepth(int leftDepth) {
            this.leftDepth = leftDepth;
        }

        public int getRightDepth() {
            return rightDepth;
        }

        public void setRightDepth(int rightDepth) {
            this.rightDepth = rightDepth;
        }

        public boolean isBalanced() {
            return isBalanced;
        }

        public void setBalanced(boolean balanced) {
            isBalanced = balanced;
        }

        public int getMaxDepth() {
            return maxDepth;
        }

        public void setMaxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
        }
    }

    public static void main(String[] args) {

        //
        BinaryTreeIsBalanced solution = new BinaryTreeIsBalanced();
        //
        TreeNode root =new TreeNode(1);
        root.left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.right = right;
        //
        TreeNode rightLeft = new TreeNode(4);

        right.left = rightLeft;
        rightLeft.left = new TreeNode(55);
        //
        System.out.println(solution.isBalancedRecursive(root));
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
