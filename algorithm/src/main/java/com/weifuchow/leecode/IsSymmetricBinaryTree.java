package com.weifuchow.leecode;


import java.util.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class IsSymmetricBinaryTree {


    public boolean isSymmetricRecursion(TreeNode root) {
        return isTwiceTreeSymmetric(root.left, root.right);
    }

    // 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
    //
    //        1
    //     /      \
    //   2          2
    //  / \       / \
    // 3    4    4    3
    //4  5  6 7  7 6  5 4
    // 每一颗左右子树相等 值相等。且左跟的左子树与右子树的右跟的右子树 && 左跟的右子树和右跟左子树 同时相等。
    // base case. 遍历完成直到为空， 遇到不相同的节点。v
    public boolean isTwiceTreeSymmetric(TreeNode leftRoot, TreeNode rightRoot) {
        if (leftRoot == null && rightRoot == null) {
            return true;
        }
        // 上述证明，两个不同时为空， 两边有一个为空，且值不对
        if (leftRoot == null || rightRoot == null || leftRoot.val != rightRoot.val) {
            return false;
        }

        return isTwiceTreeSymmetric(leftRoot.left, rightRoot.right) && isTwiceTreeSymmetric(leftRoot.right, rightRoot.left);
    }


    public boolean isSymmetric(TreeNode root) {
        // 层序遍历 判断。
        if (root == null) {
            return false;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 排空这一层
            List<String> level = new ArrayList<>();
            List<TreeNode> newLevel = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node == null) {
                    level.add(null);
                } else {
                    level.add(node.val + "");
                    newLevel.add(node);
                }
            }
            // check queue is Symmetric
            if (!checkLevelIsSymmetric(level)) {
                return false;
            }
            // 下一层依次入队列
            //    1
            //   / \
            //  2   2
            //   \   \
            //   3    3
            for (int i = 0; i < newLevel.size(); i++) {
                queue.add(newLevel.get(i).left == null ? null : newLevel.get(i).left);
                queue.add(newLevel.get(i).right == null ? null : newLevel.get(i).right);
                // 检测是否符合回文结构
                // 0 - 1
            }
        }
        return true;
    }


    public boolean checkLevelIsSymmetric(List<String> ls) {
        int left = 0;
        int right = ls.size() - 1;
        while (left < right) {
            if (ls.get(left) == null && ls.get(right) == null) {
                left++;
                right--;
                continue;
            }
            if (ls.get(left) != null && ls.get(right) == null) {
                return false;
            }
            if (ls.get(left) == null && ls.get(right) != null) {
                return false;
            }
            if (!ls.get(left).equals(ls.get(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
//        TreeNode right = new TreeNode(2);
        root.left = left;
//        root.right = right;
//        //
//        left.left = new TreeNode(3);
////        //
//        right.right = new TreeNode(3);
        IsSymmetricBinaryTree solution = new IsSymmetricBinaryTree();
        System.out.println(solution.isSymmetric(root));

//        Queue queue = new LinkedList();
//        queue.add(null);
//        System.out.println(queue.isEmpty());
//        queue.poll();
//        System.out.println(queue.isEmpty());
//
//        List<String> ls = new ArrayList<>();
//        ls.add(null);
//        ls.get(0);
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
