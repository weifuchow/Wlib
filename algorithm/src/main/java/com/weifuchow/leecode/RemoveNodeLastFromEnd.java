package com.weifuchow.leecode;


import java.util.*;

public class RemoveNodeLastFromEnd {

    // 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
    //输入：head = [1,2,3,4,5], n = 2
    //输出：[1,2,3,5]
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 算法
        // 核心。直到遍历完成倒数第几个是哪一个位置。用一个数组来记录。
        List<ListNode> arrays = new ArrayList<>();
        ListNode curNode = head;
        while (curNode != null){
            arrays.add(curNode);
            curNode = curNode.next;
        }
        int deleteIndex = arrays.size() - n;
        int nextIndex = deleteIndex + 1;
        int lastIndex = deleteIndex - 1;
        // 删除的是头部
        if(deleteIndex == 0){
            arrays.get(deleteIndex).next = null;
            return arrays.size() == 1 ? null : arrays.get(nextIndex);
        }
        // 删除未节点
        else if(deleteIndex == arrays.size() - 1) {
            arrays.get(lastIndex).next = null;
            return head;
        }
        else {
            arrays.get(lastIndex).next = arrays.get(nextIndex);
            return head;
        }

    }

    public static void main(String[] args) {
        RemoveNodeLastFromEnd solution = new RemoveNodeLastFromEnd();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);

        node1.next = node2;
        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;

        ListNode curNode = solution.removeNthFromEnd(node1,3);

        while (curNode != null){
            System.out.println(curNode.val);
            curNode = curNode.next;
        }

    }
    
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
