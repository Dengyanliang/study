package com.deng.study.datastru_algorithm.tree;

import java.util.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/31 15:45
 */
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


    public static void levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        if(Objects.nonNull(root)){
            queue.add(root);
        }

        while(!queue.isEmpty()){
            int n = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                if (Objects.nonNull(node)) {
                    list.add(node.val);
                    if (Objects.nonNull(node.left)) {
                        queue.add(node.left);
                    }
                    if (Objects.nonNull(node.right)) {
                        queue.add(node.right);
                    }
                }
            }
            result.add(list);
        }

        for (List<Integer> list : result) {
            System.out.println(list);
        }

//        return result;
    }
}
