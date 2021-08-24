package com.weifuchow.tree;

import java.util.Queue;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/29 10:00
 */
public interface IBinaryTreeTraversal {


    void prefixTraversal(BinaryTreeNode node);

    void infixTraversal(BinaryTreeNode node);

    void suffixTraversal(BinaryTreeNode node);

    void levelTraversal(BinaryTreeNode node);


}
