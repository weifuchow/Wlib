package com.weifuchow.learn;
/**
 * 测试链表反转
 */
public class TestReversalLinked {
    public static void main(String[] args) {
        Node head = initNodeLinked();
        print(head, 0);
        // System.out.println(head);
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
        Node last = null;
        // 0 的next 指向空，1的 next 指向0，
        // 上一个节点的，要指向当前节点

        // 0->null
        // 每一次循环中需要明确目的，将指针为止修改，将原来的last改为当前的next
        while(curNode != null){
            Node next = curNode.getNext();
            curNode.setNext(last);
            //
            last = curNode;
            curNode = next;
        }
        return last;
    
    }
    public static Node initNodeLinked(){
        Node curNode = new Node("0");
        Node head = curNode;
        for (int i = 0; i < 10; i++) {
            Node node = new Node((i+1)+"");
            curNode.setNext(node);
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
        System.out.println(tabLevel +  node.getValue());
        print(node.getNext(),level+1);
    }
}