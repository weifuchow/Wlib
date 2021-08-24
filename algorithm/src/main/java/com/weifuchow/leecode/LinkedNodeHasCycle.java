package com.weifuchow.leecode;


import com.weifuchow.list.LinkedStack2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LinkedNodeHasCycle {

    // 1 -> 2 - > 3 -> 4 -> 5 -> 6 的next 之前遍历过的内容。比如3.
    // 放到hash表中。判断当前next指向的内容。在之前是否出现过。
    public boolean hasCycle(ListNode head) {
        return hasCycleByFastSlowPoint(head);
    }

    public boolean hasCycleByHash(ListNode head) {
        Set<ListNode> nodeSet = new HashSet<>();
        ListNode curNode = head;
        while (curNode != null){
            if(!nodeSet.add(curNode)){
                return true;
            }else{
               curNode = curNode.next;
            }
        }
        return false;
    }

    // 假设 slow 每次跳一格子，fast 每次两格
    // 假设 n 处有环，n节点指向 p 节点中。
    // slow 遍历的路径为 1 ~ n, p ~ n, p ~ n
    // fast 遍历的节点为 2 ,4,6,8 ~n,
    // 假设 n为偶数，当slow已走过到达环处 fast 需要追赶他，
    // slow 领先一格。一回合追平
    // slow 领先两格呢，两回合追平。
    public boolean hasCycleByFastSlowPoint(ListNode head){
        if(head == null || head.next == null || head.next.next == null){
            return false;
        }
        ListNode slow = head.next;
        ListNode fast = slow.next;
        while (slow != fast){
            if(slow.next == null || fast.next == null || fast.next.next == null){
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
            if(slow == null || fast == null){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
//        ListNode node6 = new ListNode(6);
//        node1.next=node2;
//        node2.next=node3;
//        node3.next=node4;
//        node4.next=node5;
//        node5.next=node6;
//        node6.next=null;

        LinkedNodeHasCycle solution = new LinkedNodeHasCycle();
        System.out.println(solution.hasCycleByHash(node1));
        System.out.println(solution.hasCycle(node1));


    }
    
    
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
