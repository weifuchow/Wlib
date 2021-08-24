package com.weifuchow.leecode.offer;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BinaryTreeLowestCommonAncestor {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 最近公共组件
        // 算法： 从 root 节点能不能找到其p ,q节点。
        // root 节点下开始往下查找， left , right 节点。
        return lowestCommonAncestorByParentMap(root,p,q);

    }

    // 递归，做左右子树寻找p，q。
    // 两棵树公共祖先可以分解找  从左子树找他的公共祖先得到left ,从右子树找到的公共祖先right.
    // 若left 不为空，right 为空，则节点都在left；返回left
    // 若left 为空，right 不为空，则节点都在right；返回right
    // 若left 都不为空，则存在root中。
    // 从root节点出发找到其公共祖先。
    //  可以分解为的子问题为。从左树出发找到公共祖先，再从右树找到公共祖先。
    // 对两者找到的公共祖先进行判断。一方为null,直接返回另一方。
    public TreeNode lowestCommonAncestorRecursive(TreeNode root,TreeNode p, TreeNode q){
        // base case
        if(root == null){
            return null;
        }
        if(root == p || root == q){
            return root;
        }
        // 普遍的位置怎么找
        // 上诉条件都不满足，则需要找
        // 从左树找到公共祖先
        // 从右树找到公共祖先
        // 一方为null则返回另一方。不为null则返回根节点。
        // 遍历过程为 pq在的一侧。和另一侧所有节点。
        TreeNode left = lowestCommonAncestorRecursive(root.left,p,q);
        TreeNode right = lowestCommonAncestorRecursive(root.right,p,q);
        if(left == null) return right;
        if(right == null) return  left;
        return root;
    }

    // 算法：
    // 找到两棵树的父指针。网上遍历直到第一个相遇的点。
    // 需要定义父指针map.
    public TreeNode lowestCommonAncestorByParentMap(TreeNode root,TreeNode p, TreeNode q){
       Map<TreeNode,TreeNode> parentMap = new HashMap<>();
       buildMap(root,null,parentMap);
        // 找到父节点的过程
        //    1
        //  2   4
        //        5
        // 分情况讨论。p q 分别为  2 5
        // if p q 跟节点为本身则。另一方
        // 两边向上找，直到跟节点。
        TreeNode ps = p;
        TreeNode qs = q;
        HashSet<TreeNode> set = new HashSet();
        // 先把一个节点都放进set中
        while (ps != null){
            set.add(ps);
            ps = parentMap.get(ps);
        }

        // 另一个节点判断
        while(!set.contains(qs)){
            qs = parentMap.get(qs);
        }
        return qs;

    }

    // 定义根据root节点，parent节点，构造map
    // 可以表示为：表示两个子问题，从左树出发，从右树出发
    public void buildMap(TreeNode root,TreeNode parent,Map<TreeNode,TreeNode> map){
        // base case
        if(root == null){
            return ;
        }
        map.put(root,parent);
        // 左子树也是同样的问题
        buildMap(root.left,root,map);
        // 右子树也是
        buildMap(root.right,root,map);

    }

    public static void main(String[] args) {
        BinaryTreeLowestCommonAncestor solution = new BinaryTreeLowestCommonAncestor();
        //
        TreeNode root =new TreeNode(1);
        root.left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.right = right;
        right.left = new TreeNode(4);

        System.out.println(solution.lowestCommonAncestor(root,root.right,right.left));

    }
    





    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }
    }
}
