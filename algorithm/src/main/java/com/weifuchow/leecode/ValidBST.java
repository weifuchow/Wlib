package com.weifuchow.leecode;


import com.weifuchow.tree.BinaryTreeNode;

public class ValidBST {

    public boolean isValidBST(TreeNode root) {
        Info info = verrifyBinarySearchTree(root);
        System.out.println(info);
        return info.isBinarySearchTree();
    }

    public Info verrifyBinarySearchTree(TreeNode node){
        if(node == null){
            return null;
        }else{
            Info leftInfo = verrifyBinarySearchTree(node.left);
            Info rightInfo = verrifyBinarySearchTree(node.right);
            //
            boolean res = false;
            int cur = node.val;

            Info info = new Info();

            //
            if(leftInfo == null && rightInfo == null){
                info.setMinNode(cur);
                info.setMaxNode(cur);
                info.setIsBinarySearchTree(true);
                return  info;
            }else if(leftInfo == null){
                // 左为空。右不为空
                if(rightInfo.isBinarySearchTree() && cur < rightInfo.getMinNode() ){
                    info.setMinNode(cur);
                    info.setMaxNode(rightInfo.maxNode);
                    info.setIsBinarySearchTree(true);
                }else{
                    info.setIsBinarySearchTree(false);
                }
                return  info;
            } else if(rightInfo == null) {
                // 右为空，左不为空
                if(leftInfo.isBinarySearchTree() && cur > leftInfo.getMaxNode() ){
                    info.setMinNode(leftInfo.minNode);
                    info.setMaxNode(cur);
                    info.setIsBinarySearchTree(true);
                }else{
                    info.setIsBinarySearchTree(false);
                }
                return  info;

            }
            // 满足左为二叉搜索树， 右子树为二叉搜索树，且当前节点的值，要大于左子树的任一节点且小于右子树的任一节点
            if( leftInfo.isBinarySearchTree() && rightInfo.isBinarySearchTree() &&
                    (cur > leftInfo.getMaxNode() && cur < rightInfo.getMinNode())
            ){
                res = true;
                info.setIsBinarySearchTree(true);
                info.setMinNode(leftInfo.minNode);
                info.setMaxNode(rightInfo.maxNode);
            }
            //
            return  info;

        }
    }


    public static class Info {
        private boolean isBinarySearchTree;
        private int minNode;
        private int maxNode;

        public boolean isBinarySearchTree() {
            return isBinarySearchTree;
        }

        public void setIsBinarySearchTree(boolean binarySearchTree) {
            isBinarySearchTree = binarySearchTree;
        }

        public int getMinNode() {
            return minNode;
        }

        public void setMinNode(int minNode) {
            this.minNode = minNode;
        }

        public int getMaxNode() {
            return maxNode;
        }

        public void setMaxNode(int maxNode) {
            this.maxNode = maxNode;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "isBinarySearchTree=" + isBinarySearchTree +
                    ", minNode=" + minNode +
                    ", maxNode=" + maxNode +
                    '}';
        }
    }


    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    
    
    public static void main(String[] args) {
        ValidBST solution = new ValidBST();
        TreeNode node = new TreeNode(3);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(5);
        //
        TreeNode right1 = new TreeNode(4);
        TreeNode right2 = new TreeNode(6);

        node.left = left;
        node.right = right;
        //
        right.left = right1;
        right.right = right2;

        solution.isValidBST(node);

    }
}
