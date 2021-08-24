package com.weifuchow.learn;

import java.net.Socket;

/**
 * 测试链表反转
 */
public class TestReversalDoubleLinked {
    public static void main(String[] args) {
        Node head = initNodeLinked();
        print(head, 0);
        
        System.out.println("正在进行反转");
        Node head1 = reversalLinked(head);
        print(head1,0);

    }



    public static Node reversalLinked(Node node){
        // 0 -> 1 -> 2 -> 3 
        // 0 -> null, 3 - > 2 - > 0 -> null
        // 0 -> 1
        // 2 -> 1->0->null,
        Node curNode = node;
        Node last = node.last;
        // 0 的next 指向空，1的 next 指向0，
        // 上一个节点的，要指向当前节点

        // 0.last = 1; 0.next = null;
        // 1.last = 2;1.next =0; 1 -> 0, 
        // 每一次循环中需要明确目的，将指针为止修改，将原来的last改为当前的next
        // 总结 当遍历利的时候要清楚知道当前该节点的前后指针。针对对当前的前后指针进行操作即可。
            // 只需要将当前节点的前驱指针，指向当前的下一个节点，即位当前的上一个元素未下一个。
            // 当前的下一个元素，即为上一个  1。next =0， 1.last = 2
        while(curNode != null){
            Node next = curNode.getNext();
            curNode.setNext(last);
            curNode.setLast(next);
            //
            last = curNode;
            curNode = next;
        }
        return last;
    
    }
    public static Node initNodeLinked(){
        Node curNode = new Node("0");
        Node head = curNode;
        Node last = null;
        // 0.last = null , 0.setNext = 1, 1.last = 0
        for (int i = 0; i < 10; i++) {
            Node node = new Node((i+1)+"");
            curNode.setNext(node);
            curNode.setLast(last);
            //
            last = curNode;
            curNode = node;
        }
        return head;
    }

    public static void print(Node node,int level){
        if(node == null) return;
        String tabLevel = "";
        for (int i = 0; i <= level; i++) {
            tabLevel += "\t";
        }
        System.out.print("last - >");
        System.out.print(node.getLast() != null ? node.getLast().value : null);
        System.out.print(" cur->" + node.value);
        System.out.print(" next -> ");
        System.out.print(node.getNext() != null ? node.getNext().value : null);
        System.out.println("");
        print(node.getNext(),level+1);
    }
}