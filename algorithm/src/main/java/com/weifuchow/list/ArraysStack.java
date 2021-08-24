package com.weifuchow.list;

/**
 * 用数组实现栈
 *
 * @author: weifuchow
 * @date: 2021/6/3 14:45
 */
public class ArraysStack implements IStack {

    int capacity = 0;
    int size = 0;
    private Object[] objects;

    public ArraysStack(int capacity) {
        this.capacity = capacity;
        objects = new Object[capacity];
    }


    @Override
    // 先进后出
    public void push(Object obj) {
        if (size >= capacity) {
            System.out.println("超过最大长度：" + this.capacity);
//            throw new RuntimeException("超过最大长度：" + this.capacity);
        } else {
            objects[size++] = obj;
        }
    }

    @Override
    /**
     * 从后面去除
     */
    public Object pop() {
        if (size <= 0) {
            System.out.println("没有数据");
            return null;
        } else {
            return objects[--size];
        }
    }

}
