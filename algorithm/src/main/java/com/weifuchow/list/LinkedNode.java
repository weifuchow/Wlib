package com.weifuchow.list;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/3 14:42
 */
public class LinkedNode {

    private Object data;
    private LinkedNode prev;
    private LinkedNode next;


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

    public LinkedNode getNext() {
        return next;
    }

    public void setNext(LinkedNode next) {
        this.next = next;
    }


}
