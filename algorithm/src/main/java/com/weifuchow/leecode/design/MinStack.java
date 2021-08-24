package com.weifuchow.leecode.design;


import java.util.Arrays;
import java.util.Random;

//设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
//
//
// push(x) —— 将元素 x 推入栈中。
// pop() —— 删除栈顶的元素。
// top() —— 获取栈顶元素。
// getMin() —— 检索栈中的最小元素。
//
//
//
//
// 示例:
//
// 输入：
//["MinStack","push","push","push","getMin","pop","top","getMin"]
//[[],[-2],[0],[-3],[],[],[],[]]
class MinStack {

    /** initialize your data structure here. */

    int[] arrays = new int[10];
    int[] miniHelper = new int[10];
    int size = 0; // 记录当前长度
    int next = 0; // 下一位指针
    int threshold = 10;
    // 栈先进后出。
    public MinStack() {

    }
    
    public void push(int val) {
        if(threshold == size){
            threshold = threshold  << 1;
            arrays = Arrays.copyOf(arrays,threshold);
            miniHelper = Arrays.copyOf(miniHelper,threshold);

        }
        arrays[next] = val;
        // miniHelper,
        // 记录最小值，
        if(size == 0 || miniHelper[next -1] > val){
            // 第一个元素
            miniHelper[next] = val;
        }else{
            miniHelper[next] = miniHelper[next - 1];
        }
        next ++;
        size++;
    }
    
    public void pop() {
        if(size != 0){
            next--;
            size--;
        }
    }
    
    public int top() {
        return arrays[next- 1];
    }

    // 利用小顶堆。log2N
    public int getMin() {
        // 获取最小数。
        // push值，需要记录记录最小值。
        // pop值，需要记录
        // 需要维护一个随时获取最小数的结构.miniHelperArrays
        // 当插入时, 保持miniHelper的最后元素为最小值。若一个数比它最小值小。则插入最小值。因为删除不会影响最小值。
        // 当删除时，

        return  miniHelper[next - 1];
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());//   --> 返回 -3.
        minStack.pop();
        System.out.println(minStack.top());//      --> 返回 0.
        System.out.println(minStack.getMin()); //  --> 返回 -2.

        //
//        MinStack stack = new MinStack();
//        Random random = new Random();
//        for (int i = 0; i < 20; i++) {
//            int randInt = random.nextInt(100);
//            System.out.println(i + " => " + randInt);
//            stack.push(randInt);
//        }
//        stack.push(-2);
//        stack.push(-3);
//        System.out.println(stack.getMin());
//        stack.pop();
//        for (int i = 0; i < 5 ; i++) {
//            stack.pop();
//        }
//        System.out.println(stack.getMin());
//        System.out.println(stack.size);
        //



    }
}