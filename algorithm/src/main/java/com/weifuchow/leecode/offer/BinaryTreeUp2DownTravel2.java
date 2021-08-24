package com.weifuchow.leecode.offer;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BinaryTreeUp2DownTravel2 {


    // 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
    // 例如:
    //给定二叉树: [3,9,20,null,null,15,7],
    //
    //     3
    //   / \
    //  9  20
    //    /  \
    //   15   7
    //
    //
    // 返回其层次遍历结果：
    //
    // [
    //  [3],
    //  [9,20],
    //  [15,7]
    //]
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ls = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            List<Integer> levelLs = new ArrayList<>();
            ls.add(levelLs);
            int lastLevelSize = queue.size();
            // left 从对头弹出。一边弹出一边入栈。记录上一层大小
            for (int j = 0; j < lastLevelSize; j++) {
                TreeNode node = queue.removeFirst();
                levelLs.add(node.val);
                // 将其 左右 入队
                if (node.left != null) {
                    queue.addLast(node.left);
                }
                if (node.right != null) {
                    queue.addLast(node.right);
                }
            }
        }
        return ls;
    }

    public static void main(String[] args) {
        BinaryTreeUp2DownTravel2 solution = new BinaryTreeUp2DownTravel2();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.right = right;
        //
        TreeNode rightLeft = new TreeNode(4);
        //
        right.left = rightLeft;
        rightLeft.left = new TreeNode(55);
        rightLeft.right = new TreeNode(33);

        System.out.println(solution.levelOrder(root));
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
