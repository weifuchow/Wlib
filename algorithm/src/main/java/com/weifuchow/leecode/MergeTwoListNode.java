package com.weifuchow.leecode;


public class MergeTwoListNode {


    //将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
    // 示例 1：
    //
    //输入：l1 = [1,2,4], l2 = [1,3,4]
    //输出：[1,1,2,3,4,4]
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        if (l1 == null && l2 == null) {
            return null;
        } else if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        }

        ListNode smallNode = l1.val > l2.val ? l2 : l1;
        ListNode bigNode = smallNode == l1 ? l2 : l1;
        ListNode head = new ListNode(0);
        ListNode last = head;
        while (bigNode != null) {
            // 找到第一个比 bigNode 大的节点
            while (smallNode != null && smallNode.val <= bigNode.val) {
                last.next = smallNode;
                smallNode = smallNode.next;
                last = last.next;
            }
            last.next = bigNode;
            last = last.next;
            bigNode = bigNode.next;
            // small
            // 链表批量处理
            if (smallNode == null) {
                last.next = bigNode;
                break;
            }
            // bigNode
            // 链表批量处理
            if (bigNode == null) {
                last.next = smallNode;
                break;
            }
            if (smallNode.val > bigNode.val) {
                ListNode temp = smallNode;
                smallNode = bigNode;
                bigNode = temp;
            }
        }
        return head.next;
    }


    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode head = new ListNode(0);
        ListNode temp = head;
        while (p1 != null && p2 != null) {
            // l1 = [1,2,4], l2 = [1,3,4]
            // 双向指针。如果左边小，那就从左边开始+.不断的赋值。
            //
            if (p1.val <= p2.val) {
                temp.next = new ListNode(p1.val);
                p1 = p1.next;
            } else {
                temp.next = new ListNode(p2.val);
                p2 = p2.next;
            }
            temp = temp.next;
        }
        // 链表 批量处理替换。
        if (p1 == null) {
            temp.next = p2;
        } else {
            temp.next = p1;
        }
        return head.next;
    }


    public static void main(String[] args) {
        MergeTwoListNode solution = new MergeTwoListNode();
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(10);
        ListNode node3 = new ListNode(20);
        node1.next = node2;
        node2.next = node3;

        ListNode node11 = new ListNode(0);
//        ListNode node21 = new ListNode(1);
//        ListNode node31 = new ListNode(1);
//        node11.next = node21;
//        node21.next = node31;

        ListNode curNode = solution.mergeTwoLists(node1, node11);
        printNode(curNode);

    }

    private static void printNode(ListNode curNode) {
        while (curNode != null) {
            System.out.print(curNode.val + " , ");
            curNode = curNode.next;
        }
        System.out.println("");
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
