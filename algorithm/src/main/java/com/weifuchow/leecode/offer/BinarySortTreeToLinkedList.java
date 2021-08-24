package com.weifuchow.leecode.offer;


import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class BinarySortTreeToLinkedList {

    // 改变指针。变为left 改为下一个比该节点大的指针。right为
    // 树中节点的左指针需要指向前驱，树中节点的右指针需要指向后继。还需要返回链表中的第一个节点的指针。
    // 不允许创建新的新的节点。 返回第一个节点值。
    public Node treeToDoublyList(Node root) {
        // 从小到大找节点。中序遍历。
        // 输出List<Node> 然后遍历转换即可。
        // node1.left = node2
        // node1.right = node(length)

        // node2.left = node3;
        // node2.right = node1; 取余
        if(root == null) return null;
        List<Node> ls = new ArrayList<>();
        getSortListByBST(root, ls);
        for (int i = 0; i < ls.size(); i++) {
            Node node = ls.get(i);
            // set left
            node.right = ls.get( (i + 1) % ls.size());
            // size 6 , 0=> 5 1 0 2 1
            node.left = ls.get((i - 1 + ls.size()) % ls.size());
        }
        return ls.get(0);
    }

    // 中序遍历
    //    8
    //   /
    // 4
    //  \
    //   6
    public void getSortListByBST(Node root, List<Node> ls) {
        if (root == null) {
            return;
        }
        getSortListByBST(root.left, ls);
        ls.add(root);
        getSortListByBST(root.right, ls);
    }


    public static void main(String[] args) {
        BinarySortTreeToLinkedList solution = new BinarySortTreeToLinkedList();
        Node node = new Node(4);
        Node left = new Node(2);
        Node right = new Node(5);
        left.left = new Node(1);
        left.right = new Node(3);
        node.left = left;
        node.right = right;
        Node newNode = solution.treeToDoublyList(node);
        System.out.println(node);
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }


}
