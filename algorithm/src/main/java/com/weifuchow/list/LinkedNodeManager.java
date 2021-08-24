package com.weifuchow.list;

/**
 * weifuchow only.
 *
 * @author: weifuchow
 * @date: 2021/6/22 18:01
 */
public class LinkedNodeManager {

    LinkedNode head = new LinkedNode("head");

    /**
     * 从尾部插入,next节点最后执行null.
     *
     * @param obj
     */
    public void pushForTail(Object obj) {
        LinkedNode originNext = head.getNext();
        LinkedNode originPrev = head.getPrev();
        LinkedNode curNode = new LinkedNode(obj);
        // head 节点
        head.setPrev(curNode);
        if (originNext == null) {
            head.setNext(curNode);
        }
        // curNode
        if (originPrev == null) {
            curNode.setPrev(head);
        } else {
            curNode.setPrev(originPrev);
        }
        curNode.setNext(null);
        // originPrev
        if (originPrev != null) {
            originPrev.setNext(curNode);
        }
        // dont update originPrev

    }


    /**
     * 最后一个节点不循环到最后
     *
     * @param obj
     */
    public void pushForHead(Object obj) {
        LinkedNode originNext = head.getNext();
        LinkedNode originPrev = head.getPrev();
        LinkedNode curNode = new LinkedNode(obj);
        // head 节点
        head.setNext(curNode);
        // head 没有后驱
        if (originPrev == null) {
            head.setPrev(curNode);
        }
        // curNode 节点
        curNode.setPrev(head);
        curNode.setNext(originNext);
        // originNext 节点
        if (originNext != null) {
            originNext.setPrev(curNode);
        }
    }

    public Object popForHead() {
        LinkedNode originHead = head.getNext();
        LinkedNode originTail = head.getPrev();
        //
        if (originHead == null) {
            System.out.println("没有数据");
            return null;
        } else {
            Object data = originHead.getData();
            LinkedNode newHead = originHead.getNext();
            // 只有一个节点,head
            head.setNext(newHead);
            if(originHead == originTail){
                head.setPrev(null);
            }
            // new Head
            if(newHead != null){
                newHead.setPrev(head);
            }
            return data;
        }
    }

    public Object popForTail() {
        LinkedNode originHead = head.getNext();
        LinkedNode originTail = head.getPrev();
        //
        if (originTail == null) {
            System.out.println("没有数据");
            return null;
        } else {
            Object data = originTail.getData();
            LinkedNode newTail = originTail.getPrev();
            // head 指针
            head.setPrev(newTail);
            if(originHead == originTail){
                head.setNext(null);
            }
            // new tail
            if(newTail != null){
                newTail.setNext(null);
            }
            return data;

        }
    }


    public static void main(String[] args) {
        LinkedNodeManager manager = new LinkedNodeManager();
        manager.pushForHead("fuck");
        manager.pushForHead("fuck2");
        manager.pushForHead("fuck3");

        manager.pushForTail("fuck4");
        manager.pushForTail("fuck5");
        //
        manager.traversal();
        System.out.println("pop head = >" + manager.popForHead());
        System.out.println("pop tail = >" +manager.popForTail());
        System.out.println("pop head = >" +manager.popForHead());
        System.out.println("pop tail = >" +manager.popForTail());
        System.out.println("pop head = >" +manager.popForHead());
        manager.traversal();
        manager.pushForHead("fuck6");
        manager.pushForHead("fuck7");
        manager.pushForTail("fuck8");
        manager.traversal();
        System.out.println("pop tail = >" + manager.popForTail());
        System.out.println("pop head = >" +manager.popForHead());
        System.out.println("pop head = >" +manager.popForHead());
        //

        manager.pushForTail("fuck7");
        manager.pushForTail("fuck8");
        manager.pushForTail("fuck9");
        manager.pushForTail("fuck10");
        manager.pushForTail("fuck11");
        manager.traversal();
        manager.pushForHead("fuck1");
        manager.pushForHead("fuck2");
        manager.pushForHead("fuck3");
        manager.pushForHead("fuck4");
        manager.pushForHead("fuck5");
        manager.traversal();

        for (int i = 0; i < 10 ; i++) {
            System.out.println("pop head = >" + manager.popForHead());
        }
        manager.traversal();
        //
        System.out.println("queue implements push");
        manager.pushForTail("fuck1");
        manager.pushForTail("fuck2");
        manager.pushForTail("fuck3");
        manager.pushForTail("fuck4");
        manager.pushForTail("fuck5");
        manager.traversal();

        System.out.println("queue implements pop");
        System.out.println("pop head = >" + manager.popForHead());
        System.out.println("pop head = >" + manager.popForHead());
        System.out.println("pop head = >" + manager.popForHead());
        System.out.println("pop head = >" + manager.popForHead());
        System.out.println("pop head = >" + manager.popForHead());
        System.out.println("ending queue");


        System.out.println("stack implements push");
        manager.pushForTail("fuck1");
        manager.pushForTail("fuck2");
        manager.pushForTail("fuck3");
        manager.pushForTail("fuck4");
        manager.pushForTail("fuck5");
        manager.traversal();

        System.out.println("stack implements pop");
        System.out.println("tail head = >" + manager.popForTail());
        System.out.println("tail head = >" + manager.popForTail());
        System.out.println("tail head = >" + manager.popForTail());
        System.out.println("tail head = >" + manager.popForTail());
        System.out.println("tail head = >" + manager.popForTail());
        System.out.println("ending stack");

    }


    @Override
    public String toString() {
        return "LinkedNodeManager{" +
                "head=" + head +
                '}';
    }

    public void traversal(){
        recursionHead(head);
        System.out.println("");
    }

    public void recursionHead(LinkedNode node){
        if(node == null){
            return ;
        }else{
            System.out.print("->  " + node.getData() + " ");
            recursionHead(node.getNext());
        }
    }


}
