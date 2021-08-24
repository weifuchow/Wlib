package com.weifuchow.tree;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/29 10:01
 */
public class BinaryTreeNode {

    private Object val;
    private BinaryTreeNode left;
    private BinaryTreeNode right;

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode left) {
        this.left = left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode right) {
        this.right = right;
    }

    public BinaryTreeNode(Object val) {
        this.val = val;
    }

    public BinaryTreeNode(){
        
    }

}
