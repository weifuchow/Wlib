package com.weifuchow.tree;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author: weifuchow
 * @date: 2021/6/29 9:52
 */
public class BinaryTreeTraversalRecursion implements IBinaryTreeTraversal, IBinaryTreeSerialization {
    @Override
    public void prefixTraversal(BinaryTreeNode node) {
        if (node != null) {
            System.out.println("val => " + node.getVal());
            prefixTraversal(node.getLeft());
            prefixTraversal(node.getRight());
        }
    }

    @Override
    public void infixTraversal(BinaryTreeNode node) {
        if (node != null) {
            infixTraversal(node.getLeft());
            System.out.println("val => " + node.getVal());
            infixTraversal(node.getRight());
        }
    }

    @Override
    public void suffixTraversal(BinaryTreeNode node) {
        if (node != null) {
            suffixTraversal(node.getLeft());
            suffixTraversal(node.getRight());
            System.out.println("val => " + node.getVal());
        }
    }

    @Override
    public void levelTraversal(BinaryTreeNode node) {
        throw new RuntimeException("cant not implements by recursion!");
    }

    /**
     * 前序序列化，
     * 1
     * 2   3
     * 4 5 6 7
     * =》 [ 1 2 4 null,null,5,null,null,3,6,null,null,7,null,null]
     *
     * @param node
     * @return
     */
    @Override
    public Queue<String> prefixSerialization(BinaryTreeNode node) {
        throw new RuntimeException("cant not implements");
    }


    /**
     * 前序反序列化，
     * [ 1 2 4 null,null,5,null,null,3,6,null,null,7,null,null]
     * =>
     *   1
     *  2   3
     * 4 5 6 7
     *
     * @param queue
     * @return
     */
    @Override
    public BinaryTreeNode prefixDeSerialization(Queue<String> queue) {
        if (queue.isEmpty()) {
            return null;
        } else {
            BinaryTreeNode node = new BinaryTreeNode(queue.poll());
            node.setLeft(prefixDeSerialization(queue));
            node.setRight(prefixDeSerialization(queue));
            return node;
        }
    }


    @Override
    public Queue<String> infixSerialization(BinaryTreeNode node) {
        Queue<String> queue = new ArrayDeque<>();
        infixSerializationRecursion(queue, node);
        return queue;
    }

    public void infixSerializationRecursion(Queue<String> queue, BinaryTreeNode node) {
        if (node == null) {
            queue.add(null);
        } else {
            infixSerializationRecursion(queue, node.getLeft());
            queue.add(node.getVal().toString());
            infixSerializationRecursion(queue, node.getRight());
        }
    }

    @Override
    /**
     * 中序反序列化，
     * [null,4,null,2,null,5,null,1,6,null,null,3,7,null,null]
     * =>
     *    1
     *  2   3
     * 4 5 6 7
     *
     *  */

    public BinaryTreeNode infixDeSerialization(Queue<String> queue) {
        throw new RuntimeException("cant not implemnets tree deserialization by infix ");

    }




    public static void main(String[] args) {
        BinaryTreeTraversalRecursion recursion = new BinaryTreeTraversalRecursion();
        Queue queue= new ArrayDeque();
        queue.add("4");
        queue.add(null);
        queue.add(null);
        queue.add("2");
        queue.add("5");

    }

    @Override
    public Queue<String> suffixSerialization(BinaryTreeNode node) {
        return null;
    }

    @Override
    public BinaryTreeNode suffixDeSerialization(Queue<String> queue) {
        return null;
    }
}
