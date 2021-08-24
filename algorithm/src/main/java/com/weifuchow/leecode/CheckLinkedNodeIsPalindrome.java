package com.weifuchow.leecode;

import com.weifuchow.list.LinkedNode;

import java.util.Stack;

public class CheckLinkedNodeIsPalindrome {

    /**
     *  回文：正序和逆序是一样。
     *  验证链表回文，因为链表不是数组，无法获取最后一个
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        Stack<Integer> stack = new Stack();
        ListNode cur = head;
        while (cur != null){
            stack.push(cur.val);
            cur = cur.next;
        }
        // 对比
        cur = head;
        while (cur != null){
            if(cur.val != stack.pop()){
                return false;
            }
            cur = cur.next;
        }
        return true;
    }
    
    
  public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node = new ListNode(2);
        ListNode node11 = new ListNode(3);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(1);

        //
        head.next = node;
        node.next = node11;
        node11.next = node1;
        node1.next = node2;

        // 1->2->2->1
        CheckLinkedNodeIsPalindrome solution = new CheckLinkedNodeIsPalindrome();
        System.out.println(solution.isPalindrome(head));
    }

}
