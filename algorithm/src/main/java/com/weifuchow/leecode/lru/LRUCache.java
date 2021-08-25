package com.weifuchow.leecode.lru;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;




public class LRUCache {

    // (最近最少使用) 缓存机制
    // LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
    // int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
    // void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上
    // 限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
    private Map<Integer,Integer> cache = new HashMap<>();
    private int size = 0;
    private int capacity = 0;
    private LinkedList<Integer> queue = new LinkedList();
    // 从队尾

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    // 有一个队尾维护这个关系 put关系 a b c d e f g
    //  队列： g f e d c b a
    //  如果获取 a, a往前挪一位
    //
    public int get(int key) {
        if(cache.get(key) == null){
            return -1;
        }
        int val = cache.get(key);
        // 调整队列
        queue.remove(new Integer(key));
        queue.addFirst(key);
        return val;
    }

    public void put(int key, int value) {

        if(cache.get(key)!=null){
            cache.remove(key);
            queue.remove(new Integer(key));
            size--;
        }

        if(capacity > size) {
            cache.put(key, value);
            queue.addFirst(key);
            size++;
        }else{
            int keyv = queue.removeLast();
            cache.remove(keyv);
            size --;
            put(key,value);
        }
    }

    //["LRUCache","get","put","get","put","put","get","get"]
    //[[2],[2],[2,6],[1],[1,5],[1,2],[1],[2]]

    public static void main(String[] args) {
        LRUCache solution = new LRUCache(2);
        solution.get(2);
        solution.put(2,6);
        solution.get(1);
        solution.put(1,5);
        solution.put(1,2);
        solution.get(1);
        solution.get(2);
        System.out.println(solution.get(2));
    }


}
