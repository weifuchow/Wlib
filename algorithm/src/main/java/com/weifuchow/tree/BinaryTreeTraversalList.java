package com.weifuchow.tree;

import com.sun.jmx.remote.internal.ArrayQueue;
import com.weifuchow.list.ArraysStack;

import java.util.*;

/**
 * @desc: 利用线性表
 * @author: weifuchow
 * @date: 2021/6/29 9:53
 */
public class BinaryTreeTraversalList implements IBinaryTreeTraversal {
    @Override
    // 节点入栈
    // 1。从栈中获取输出节点信息
    // 2. 将右 左 入栈。
    // 3. 再从栈中获取节点数据，重复 1 ， 2
    public void prefixTraversal(BinaryTreeNode node) {
        // 前缀遍历。利用栈
        Stack<BinaryTreeNode> stack = new Stack<>();
        // 头结点进栈
        stack.push(node);
        // stack
        while (!stack.empty()) {
            BinaryTreeNode popNode = stack.pop();
            System.out.println("val => " + popNode.getVal());
            // 左右子树进栈
            // 前将右子树进栈，因为其要后执行
            if (popNode.getRight() != null) {
                stack.push(popNode.getRight());
            }
            // 再将左子树进栈
            if (popNode.getLeft() != null) {
                stack.push(popNode.getLeft());
            }
        }


    }

    @Override
    // 遍历利用节点指针及栈实现 模仿递归过程
    // 1.先将所有左节点入栈。
    // 2.从栈中取出节点，然后输出
    // 3.判断右节点是否存在，有则入栈，改变节点指针。重复上诉过程。
    public void infixTraversal(BinaryTreeNode node) {
        // 中序遍历
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty() && node != null) {
            if (node.getLeft() != null) {
                stack.push(node.getLeft());
                node = node.getLeft();
            } else {
                BinaryTreeNode popNode = stack.pop();
                System.out.println("val => " + popNode.getVal());
                if (popNode.getRight() != null) {
                    stack.push(popNode.getRight());
                    node = popNode.getRight();
                }
            }
        }

    }

    @Override
    /**
     * 后序遍历
     *     1
     *   2   3
     *  4 5 6 7 =》 4 5 2 3 7 1
     *  //
     *  
     */
    public void suffixTraversal(BinaryTreeNode node) {
        // 后序遍历 找到左右结点
        Stack<BinaryTreeNode> stack = new Stack<>();
        stack.push(node);
        BinaryTreeNode cur = node;
        BinaryTreeNode last = node;
        while (!stack.isEmpty()){
          // node
            cur = stack.peek();
            // 右先入栈
            if((cur.getRight() == null && cur.getLeft() == null) || cur.getRight() == last){
                BinaryTreeNode pop = stack.pop();
                last = pop;
                System.out.println("val => " + pop.getVal());
                continue;
            }
            if(cur.getRight() != null){
                stack.push(cur.getRight());
            }
            // 左先入栈
            if(cur.getLeft() != null){
                stack.push(cur.getLeft());
            }
        }

    }
    
    
    
    public static Queue<String>  datas = new LinkedList<>();
    @Override
    /**
     * 按层级遍历关系。
     * 通过队列进行实现，先进先出。先将这一层所有内容进队列。然后在统计
     */
    public void levelTraversal(BinaryTreeNode node) {
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            BinaryTreeNode cur = queue.poll();
            if(cur == null){
                System.out.println("val = >" + null);
                datas.add(null);
                continue;
            }else {
                datas.add(cur.getVal().toString());
                System.out.println("val = >" + cur.getVal());
            }
            if (cur.getLeft() != null) {
                queue.add(cur.getLeft());
            }else {
                queue.add(null);
            }
            if (cur.getRight() != null) {
                queue.add(cur.getRight());
            }else {
                queue.add(null);
            }
        }
        System.out.println(queue);
    }

    /**
     * 1 2 3 4 5 6 7 null null null null null null 8 9 = >
     *    1
     *  2   3
     * 4 5 6 7
     *      8 9
     */
    public BinaryTreeNode levelDeserialization(Queue<String> queue){
        if(queue.isEmpty()){
            return null;
        }else{
            // queue 非空，
            BinaryTreeNode head = new BinaryTreeNode(queue.poll());
            Queue<BinaryTreeNode> lastParentsQueue = new LinkedList<>();
            lastParentsQueue.add(head);
            int level = 1;
            // 根节点数量
            while (!queue.isEmpty()){
                // 先创建第二层结点
                List<BinaryTreeNode> nextLevelNodes = new ArrayList<>();
                for (int i = 0; i < level * 2 && !queue.isEmpty(); i++) {
                    nextLevelNodes.add(new BinaryTreeNode(queue.poll()));
                }
                //
                for (int i = 0; i < level * 2 && !lastParentsQueue.isEmpty(); ) {
                    BinaryTreeNode parent = lastParentsQueue.poll();
                    parent.setLeft(nextLevelNodes.get(i++));
                    parent.setRight(nextLevelNodes.get(i++));
                }
                // nextLevelNodes => parents
                for (int i = 0; i < nextLevelNodes.size(); i++) {
                    if(nextLevelNodes.get(i).getVal() != null) {
                        lastParentsQueue.add(nextLevelNodes.get(i));
                    }
                }
                level = level << 1;
            }
            return head;
        }
    }



    /**
     * 求出树中最大的宽度。
     *
     * @param node
     * @return
     */
    public int findBinaryTreeMaxWidth(BinaryTreeNode node) {
        int max = 0;
        int curLeverNodes = 0;
        int curLevel = 1;
        Queue<BinaryTreeNode> queue = new ArrayDeque<>();
        // 节点及层级
        Map<BinaryTreeNode, Integer> nodeLevelMap = new HashMap<>();
        nodeLevelMap.put(node, 1);
        queue.add(node);
        // 入队，
        while (!queue.isEmpty()) {
            BinaryTreeNode curNode = queue.poll();
            int level = nodeLevelMap.get(curNode);
            //
            if (curNode.getLeft() != null) {
                queue.add(curNode.getLeft());
                nodeLevelMap.put(curNode.getLeft(), level + 1);
            }
            if (curNode.getRight() != null) {
                queue.add(curNode.getRight());
                nodeLevelMap.put(curNode.getRight(), level + 1);
            }
            // 同级增加
            if (level == curLevel) {
                curLeverNodes++;
            } else {
                // 上一级curLevelNodes 为
                max = Math.max(max, curLeverNodes);
                curLevel++;
                curLeverNodes = 1;
            }
        }
        return max;
    }
    
    public static void main(String[] args) {
        BinaryTreeTraversalList list = new BinaryTreeTraversalList();
        BinaryTreeNode node = BinaryTreeTraversalTest.buildBinaryTreeNode();
        System.out.println("max Width=> " + list.findBinaryTreeMaxWidth(node));
        list.levelTraversal(node);
        System.out.println(datas.size());
//        while (!datas.isEmpty()){
//            System.out.println(datas.poll());
//        }
        //
        System.out.println("序列化后结果");
        BinaryTreeNode node1 = list.levelDeserialization(datas);
        System.out.println(node1);
        datas = new LinkedList<>();
        //
        list.levelTraversal(node);
        System.out.println(datas.size());


    }

}
