package com.weifuchow.tree;

import java.util.Queue;

/**
 *
 * @author: weifuchow
 * @date: 2021/7/1 15:16
 */
public interface IBinaryTreeSerialization {

    Queue<String> prefixSerialization(BinaryTreeNode node);

    BinaryTreeNode prefixDeSerialization(Queue<String> queue);


    Queue<String> infixSerialization(BinaryTreeNode node);

    BinaryTreeNode infixDeSerialization(Queue<String> queue);


    Queue<String> suffixSerialization(BinaryTreeNode node);

    BinaryTreeNode suffixDeSerialization(Queue<String> queue);


}
