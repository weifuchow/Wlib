package com.weifuchow.leecode.offer;


public class BinarySortTreeIsValid   {


    // 验证二叉搜索树是否合法
    // 这个需要每个节点都判断。
    public boolean isValidBST(TreeNode root) {
        return isValidBSTRecursive(root).isBst();
    }

    // 需要满足父节点大于左子树，右子树大于根节点。
    // 左子树任意节点都小于父节点
    // 右子树任意节点都大于父节点
    // 任意一根都需要满足
    // 递归，该root节点的树是否满足BST
    // [10,5,15,null,null,6,20]
    public Info isValidBSTRecursive(TreeNode root) {
        if(root == null){
            Info info = new Info();
            info.setBst(true);
            return info;
        }else if(root.left == null && root.right == null){
            Info info = new Info();
            info.setBst(true);
            info.setNodeMinSize(root.val);
            info.setNodeMaxSize(root.val);
            return info;
        }
        else{
            Info info = new Info();
            Info left = isValidBSTRecursive(root.left);
            // 先判断左
            if(left.isBst()){
                if(left.getNodeMaxSize() == null){
                    info.setNodeMinSize(root.val);
                    info.setBst(true);
                }else if(left.getNodeMaxSize() < root.val){
                    info.setNodeMinSize(left.getNodeMinSize());
                    info.setBst(true);
                }else{
                    info.setBst(false);
                    return info;
                }
            }else{
                info.setBst(false);
                return info;
            }
            Info right = isValidBSTRecursive(root.right);
            if(right.isBst()){
                if(right.getNodeMinSize() == null){
                    info.setNodeMaxSize(root.val);
                    info.setBst(true);
                }else if(right.getNodeMinSize() > root.val){
                    info.setNodeMaxSize(right.nodeMaxSize);
                    info.setBst(true);
                }else{
                    info.setBst(false);
                    return info;
                }
            }else{
                info.setBst(false);
                return info;
            }
            return info;
        }
    }

    public static class Info {
        boolean isBst;
        Integer nodeMaxSize;
        Integer nodeMinSize;

        public boolean isBst() {
            return isBst;
        }

        public void setBst(boolean bst) {
            isBst = bst;
        }

        public Integer getNodeMaxSize() {
            return nodeMaxSize;
        }

        public void setNodeMaxSize(int nodeMaxSize) {
            this.nodeMaxSize = nodeMaxSize;
        }

        public Integer getNodeMinSize() {
            return nodeMinSize;
        }

        public void setNodeMinSize(int nodeMinSize) {
            this.nodeMinSize = nodeMinSize;
        }

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
