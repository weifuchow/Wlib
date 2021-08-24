package com.weifuchow.leecode.offer;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

//输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
//
// B是A的子结构， 即 A中有出现和B相同的结构和节点值。
public class BinaryTreeIsSubStructure {


    //     3
    //    / \
    //   4  5
    //  / \
    // 1   2
    //给定的树 B：
    //
    //    4
    //   /
    // 1
    //返回 true，因为 B 与 A 的一个子树拥有相同的结构和节点值。
    // 递归，直到找到他的根点B.
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        // 怎么样才算子结构。就是在A树中含有B
        // 判断B树的子树中值内容是否等于A树中
        // b.left  = a.get(b) 维护一个 parentMap. 比较值、父节点
        Map<Integer, List<TreeNode>> maps = new HashMap<>();
        buildMap(A,maps);
        //

        if(B == null){
            return false;
        }
        // 说明为跟节点判断,与A进行比较
        List<TreeNode> matchs = maps.get(B.val);
        if(matchs == null || matchs.isEmpty() ) return false;
        for (TreeNode node : matchs) {
            if(checkIsSubStructure(B,node)){
                return true;
            }
        }
        return false;
    }

    public void buildMap(TreeNode root,Map<Integer, List<TreeNode>> maps){
        if(root == null){
            return ;
        }
        if(maps.containsKey(root.val)){
            List<TreeNode> list = maps.get(root.val);
            list.add(root);
            maps.put(root.val,list);
        }else{
            List<TreeNode> list = new ArrayList<>();
            list.add(root);
            maps.put(root.val,list);
        }
        buildMap(root.left,maps);
        buildMap(root.right,maps);
    }

    public boolean checkIsSubStructure(TreeNode root,TreeNode compare){
        //
        if(root == null){
            return true;
        }
        else{
            if(root != null && compare == null){
                return false;
            }
            // 比较值 和父节点是否相等。
            if(root.val == compare.val){
                return checkIsSubStructure(root.left,compare.left) && checkIsSubStructure(root.right,compare.right);
            }else {
                return false;
            }

        }

    }


    public static void main(String[] args) {
        BinaryTreeIsSubStructure solution = new BinaryTreeIsSubStructure();
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
