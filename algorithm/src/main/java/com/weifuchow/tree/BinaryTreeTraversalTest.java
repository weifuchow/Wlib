package com.weifuchow.tree;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/29 17:39
 */
public class BinaryTreeTraversalTest {

    public static void main(String[] args) {
        BinaryTreeNode node = buildBinaryTreeNode();
        IBinaryTreeTraversal treeTraversal = new BinaryTreeTraversalList();
//        IBinaryTreeTraversal treeTraversal = new BinaryTreeTraversalRecursion();
        testBinaryTreeTraversal(treeTraversal,node);
    }

    public static void testBinaryTreeTraversal(IBinaryTreeTraversal treeTraversal,BinaryTreeNode node){
//        System.out.println("begin prefix");
//        treeTraversal.prefixTraversal(node);
//        System.out.println("end prefix");

//        System.out.println("begin infix");
//        treeTraversal.infixTraversal(node);
//        System.out.println("end infix");
//
        System.out.println("begin suffix");
        treeTraversal.suffixTraversal(node);
        System.out.println("end suffix");

//        System.out.println("begin level");
//        treeTraversal.levelTraversal(node);
//        System.out.println("end level");

    }

    /**
 *                      parent
     *          left                    right
*      left'son1  left'son2   right'son1            right'son2
 *                                     right'son2 'son1    right'son2 'son2
     * @return
     */
    public static BinaryTreeNode buildBinaryTreeNode() {
        BinaryTreeNode parent = new BinaryTreeNode("parent");
        BinaryTreeNode left = new BinaryTreeNode("left");
        BinaryTreeNode right = new BinaryTreeNode("right");
        parent.setLeft(left);
        parent.setRight(right);
        //
        left.setLeft(new BinaryTreeNode("<left> son_1"));
        left.setRight(new BinaryTreeNode("<left> son_2"));
        //
        right.setLeft(new BinaryTreeNode("right son_1"));
        BinaryTreeNode rightSon2 = new BinaryTreeNode("right son_2");
        right.setRight(rightSon2);
        //
        rightSon2.setLeft(new BinaryTreeNode("right son_2 's son1"));
        rightSon2.setRight(new BinaryTreeNode("right son_2 's son2"));
        return parent;
    }
}
