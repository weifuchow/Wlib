package com.weifuchow.list;

/**
 * 用链表实现队列
 *
 * @author: weifuchow
 * @date: 2021/6/3 14:44
 */
public class LinkedQueue implements IQueue {

    int capacity;
    int size;
    LinkedNodeManager manager;

    public LinkedQueue(int capacity) {
        this.capacity = capacity;
        manager = new LinkedNodeManager();
    }

    @Override
    public void push(Object obj) {
        if (size >= capacity) {
            System.out.println("超过最大长度: " + size);
//            throw new RuntimeException("超过最大长度: " + size);
        } else {
            // 队列直接插入到头指针后
            manager.pushForTail(obj);
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
            return manager.popForHead();
        }
    }

}
