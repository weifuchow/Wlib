package com.weifuchow.list;

import sun.awt.image.ImageWatched;

/**
 * 用链表实现栈
 * @author: weifuchow
 * @date: 2021/6/3 14:44
 */
public class LinkedStack2 implements IStack {

    int capacity;
    int size;
    LinkedNode tail;


    public LinkedStack2(int capacity) {
        this.capacity = capacity;
        tail = new LinkedNode(null);
    }

    @Override
    public void push(Object obj) {
        if (size >= capacity) {
//            throw new RuntimeException("超过最大长度: " + size);
            System.out.println("超过最大长度: " + size);
        } else {
            // 队列直接插入到头指针后
            LinkedNode cur = new LinkedNode(obj);
            LinkedNode originPrev = tail.getPrev();
            tail.setPrev(cur);
            cur.setPrev(originPrev);
            size++;
        }
    }

    @Override
    public Object pop() {
        if (size <= 0) {
            System.out.println("没有数据");
            return null;
        } else {
            size--;
            LinkedNode node = tail.getPrev();
            tail.setPrev(node.getPrev());
            return node.getData();
        }
    }


    public static class LinkedNode {
        Object data;
        LinkedNode prev;

        public LinkedNode(Object data) {
            this.data = data;
        }


        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }


        public LinkedNode getPrev() {
            return prev;
        }

        public void setPrev(LinkedNode prev) {
            this.prev = prev;
        }
    }

}
