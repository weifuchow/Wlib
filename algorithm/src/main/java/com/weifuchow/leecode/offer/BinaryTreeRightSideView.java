package com.weifuchow.leecode.offer;


import java.util.*;

public class BinaryTreeRightSideView {

    // 每一层的最右节点。
    // 层序遍历
    public List<Integer> rightSideView(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList();
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        queue.push(root);
        while (!queue.isEmpty()) {
            //
            int levelSize = queue.size();
            for (int i = 1; i <= levelSize; i++) {
                // 排空记录最后一个
                TreeNode node = queue.pollFirst();
                if (i == levelSize) {
                    result.add(node.val);
                }
                if (node.left != null) {
                    queue.addLast(node.left);
                }
                if (node.right != null){
                    queue.addLast(node.right);
                }
            }
        }
        return result;
    }

    public class TreeNode {
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
