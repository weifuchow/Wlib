package com.weifuchow.tree;

/**
 * @desc : 是否为二叉搜索树
 * @author: weifuchow
 * @date: 2021/7/2 18:32
 */
public class VerifyBinarySearchTree {

    /**
     *  需要满足二叉搜索树特点。
     *  1.若任意结点的左子树不空，则左子树上所有结点的值均不大于它的根结点的值。
     *  2.若任意结点的右子树不空，则右子树上所有结点的值均不小于它的根结点的值。
     *  3.任意结点的左、右子树也分别为二叉搜索树
     *  分析：利用递归实现：
     *        左树必然为满足二叉搜索树，右树也必然满足二叉搜索树
     *        且当前节点的值，要大于左子树的任一节点且小于右子树的任一节点。左子树需要一个最大值，右子树需要一个最小值。
     * @param node
     * @return
     */
    public Info verrifyBinarySearchTree(BinaryTreeNode node){
        if(node == null){
            return null;
        }else{
            Info leftInfo = verrifyBinarySearchTree(node.getLeft());
            Info rightInfo = verrifyBinarySearchTree(node.getRight());
            //
            boolean res = false;
            int cur = Integer.parseInt(node.getVal().toString());

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


    public static void main(String[] args) {
        VerifyBinarySearchTree solution = new VerifyBinarySearchTree();
        BinaryTreeNode node = new BinaryTreeNode("3");
        BinaryTreeNode left = new BinaryTreeNode("1");
        BinaryTreeNode right = new BinaryTreeNode("5");
        BinaryTreeNode right1 = new BinaryTreeNode("4");
        BinaryTreeNode right2 = new BinaryTreeNode("6");
        //
        node.setLeft(left);
        node.setRight(right);
        right.setLeft(right1);
        right.setRight(right2);
        //
        System.out.println(solution.verrifyBinarySearchTree(node));
    }
}
