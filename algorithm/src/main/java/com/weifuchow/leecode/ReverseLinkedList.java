package com.weifuchow.leecode;


public class ReverseLinkedList {


    //输入：head = [1,2,3,4,5]
    //输出：[5,4,3,2,1]

    //(head) 1 -> 2 -> 3 -> 4 -> 5
    // null < -1 <- 2 -< 3 -< 4 -<5
    // last 的值，curNode 当前节点的值。curNode 指向 last,就是反转了。last初始值为null
    public ListNode reverseList(ListNode head) {
        //
        ListNode curNode = head;
        ListNode last = null;
        while (curNode != null){
            ListNode node = curNode.next;
            curNode.next = last;
            last = curNode;
            curNode = node;
        }
        return last;
    }


    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        ReverseLinkedList solution = new ReverseLinkedList();
        ListNode newNode = solution.reverseList(node1);

        while (newNode != null){
            System.out.println(newNode.val);
            newNode = newNode.next;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}
