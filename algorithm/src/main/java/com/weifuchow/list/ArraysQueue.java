package com.weifuchow.list;

/**
 * 用数组实现队列
 * @author: weifuchow
 * @date: 2021/6/3 14:44
 */
public class ArraysQueue implements IQueue {


    int capacity = 0;
    int size = 0;
    int head = 0;
    int next = 0;
    private Object[] objects;

    public ArraysQueue(int capacity) {
        this.capacity = capacity;
        this.objects = new Object[capacity];
    }


    @Override
    // x 2 3 4 5 =>
    public void push(Object obj) {
        if(size >= capacity){
            System.out.println("超过最大长度");
//            throw new RuntimeException("超过最大长度");
        }else{
            objects[(next++ % capacity)] = obj;
            size ++;
        }
    }

    @Override
    // 避免数组移动，从对头开始取。
    // 1 2 3 4 5
    // 1
    public Object pop() {
        if(size == 0) {
            System.out.println("没有数据");
            return null;
        }else{
            Object obj = objects[head++ % capacity ];
            size --;
            return obj;
        }
    }
}
