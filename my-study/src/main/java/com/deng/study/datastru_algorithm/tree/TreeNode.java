package com.deng.study.datastru_algorithm.tree;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/31 15:45
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
