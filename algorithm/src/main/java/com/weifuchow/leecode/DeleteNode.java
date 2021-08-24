package com.weifuchow.leecode;

public class DeleteNode {

    // 使其可以删除某个链表中给定的（非末尾）节点。传入函数的唯一参数为 要被删除的节点 。
    // 输入：head = [4,5,1,9], node = 5
    //输出：[4,1,9]  4 1 9
    //解释：给定你链表中值为5的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
    public void deleteNode(ListNode node) {

        // 将node节点值该node.next 节点的值。最后断掉最后一个。

        ListNode curNode = node;
        while (curNode != null){
            ListNode nextNode = curNode.next;
            // 最后一个节点
            curNode.val = nextNode.val;
            if(nextNode.next == null){
                curNode.next = null;
                break;
            }
            curNode = curNode.next;
        }

    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(4);
        ListNode node2 = new ListNode(5);
        ListNode node3 = new ListNode(1);
        ListNode node4 = new ListNode(9);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        DeleteNode solution = new DeleteNode();
        solution.deleteNode(node2);

        ListNode curNode = node1;
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
