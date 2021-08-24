package com.weifuchow.leecode;

/**
 * weifuchow only.
 *
 * @author: weifuchow
 * @date: 2021/7/5 18:12
 */
public class SortedArrayToBST {


    public TreeNode sortedArrayToBST(int[] nums) {
        return buildBst(nums,0,nums.length - 1);
    }

    // [-10,-3,0,5,9]
    // length = 5,
    // curIndex = 0 + 4 /2

    // [-10,-3,0,5,9,11]
    // length = 6;
    // curIndex = 0 + 5 / 2 = 2

    public TreeNode buildBst(int[] nums,int begin,int end){
        if(begin > end){
            return null;
        }
        int currentIndex = (begin + end + 1) /2;
        TreeNode node = new TreeNode(nums[currentIndex]);
        node.left = buildBst(nums,begin,currentIndex-1);
        node.right = buildBst(nums,currentIndex+1,end);
        return node;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1,3};
        SortedArrayToBST solution = new SortedArrayToBST();
        TreeNode node = solution.sortedArrayToBST(nums);
        System.out.println(node);
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
