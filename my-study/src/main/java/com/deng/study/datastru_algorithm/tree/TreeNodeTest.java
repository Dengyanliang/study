package com.deng.study.datastru_algorithm.tree;

import org.junit.Test;

import java.util.*;

/**
 * @Desc: 力扣上的题目
 * @Auther: dengyanliang
 * @Date: 2023/7/31 15:45
 */
public class TreeNodeTest {

    @Test
    public void 验证二叉搜索树(){
        TreeNode root = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(3);

        root.left = node1;
        root.right = node2;


        boolean result = isValidBST(root,Long.MIN_VALUE,Long.MAX_VALUE);
        System.out.println(result);

    }

    private boolean isValidBST(TreeNode node, long minValue, long maxValue) {
        if(node == null){
            return true;
        }
        if(node.val <= minValue || node.val >= maxValue){
            return false;
        }
        return isValidBST(node.left,minValue,node.val) && isValidBST(node.right,node.val,maxValue);
    }


    @Test
    public void 二叉树的右视图(){
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);

        root.left = node1;
        root.right = node2;

        node1.right = node4;
        node2.right = node3;

        List<Integer> result = rightSideView(root);
        System.out.println(result);
    }

    /**
     * 这里使用层序遍历进行处理
     * @param root
     * @return
     */
    private List<Integer> rightSideView(TreeNode root) {
        // key:深度 value:每一层最后一个节点的值
        Map<Integer,Integer> rightmostValueAtDepth = new HashMap<>();
        int maxDepth = -1;


        /**
         * Deque:
         *  add() == linkLast();
         *  pop() == removeFirst();
         *
         *  push()== addFirst();
         *  peek()== getFirst();
         *
         * Queue:
         *  add() = linkLast();
         *  remove() = removeFirst();
         */
        // 节点队列
        Deque<TreeNode> nodeQueue = new LinkedList<>();
        // 深度队列，维护层数
        Deque<Integer> depthQueue = new LinkedList<>();

        nodeQueue.add(root);
        depthQueue.add(0);

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.pop(); // 从队头取元素
            int depth = depthQueue.pop();

            if(node != null){
                // 维护二叉树的最大深度
                maxDepth = Math.max(maxDepth,depth);

                /*************层序遍历 BEGIN ****************/
                // 层序遍历：由于每一层最后一个访问的节点才是我们需要的答案，因此不断更新对应深度的信息
                rightmostValueAtDepth.put(depth,node.val);

                // 如果是层序遍历，则使用队列的结构，添加到队尾
                nodeQueue.add(node.left);
                nodeQueue.add(node.right);
                depthQueue.add(depth+1);
                depthQueue.add(depth+1);
                /*************层序遍历 END ****************/

                /*************深度优先遍历 BEGIN ****************/
                // 深度优先遍历：如果不存在对应深度的节点我们才插入
//                if(!rightmostValueAtDepth.containsKey(depth)){
//                    rightmostValueAtDepth.put(depth,node.val);
//                }
//                // 如果是深度优先遍历，则使用栈的结构，添加到队头
//                nodeQueue.push(node.left); // 放到队头
//                nodeQueue.push(node.right);
//                depthQueue.push(depth+1);
//                depthQueue.push(depth+1);
                /*************深度优先遍历 END ****************/
            }
        }
        List<Integer> rightView = new ArrayList<>();
        for (int depth = 0; depth <= maxDepth; depth++) {
            rightView.add(rightmostValueAtDepth.get(depth));
        }
        return rightView;
    }

    @Test
    public void 二叉树展开为链表(){
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(5);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(6);

        root.left = node1;
        root.right = node2;

        node1.left = node3;
        node1.right = node4;
        node2.right = node5;

        do_二叉树展开为链表(root);
    }

    private void do_二叉树展开为链表(TreeNode root){
       List<TreeNode> list = new ArrayList<>();
       preOrder(root,list);

        for (int i = 1; i < list.size(); i++) {
            TreeNode prev = list.get(i-1);
            TreeNode curr = list.get(i);

            // 这里就是展开为一个链表，会破坏原始二叉树的结构
            prev.left = null;
            prev.right = curr;
        }
    }

    private void preOrder(TreeNode node,List<TreeNode> list){
        if(node == null){
            return;
        }
        list.add(node);
        preOrder(node.left,list);
        preOrder(node.right,list);
    }

    @Test
    public void 路径总和III(){
        TreeNode root = new TreeNode(10);
        TreeNode node1 = new TreeNode(5);
        TreeNode node2 = new TreeNode(-3);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(2);
        TreeNode node5 = new TreeNode(11);
        TreeNode node6 = new TreeNode(3);
        TreeNode node7 = new TreeNode(-2);
        TreeNode node8 = new TreeNode(1);

        root.left = node1;
        root.right = node2;

        node1.left = node3;
        node1.right = node4;
        node2.right = node5;

        node3.left = node6;
        node3.right = node7;
        node4.right = node8;

        int targetSum = 8;

        System.out.println(pathSum(root, targetSum));
    }

    private int pathSum(TreeNode root, int targetSum) {
        if(root == null){
            return 0;
        }
        // 先从根节点寻找
        int res = rootSum(root,targetSum);
        // 从左子树寻找
        res += pathSum(root.left,targetSum);
        // 从右子树寻找
        res += pathSum(root.right,targetSum);
        return res;
    }

    private int rootSum(TreeNode root, long targetSum){
        if(root == null){
            return 0;
        }

        int res = 0;
        int val = root.val;
        if(val == targetSum){
            res++;
        }

        res += rootSum(root.left,targetSum-val);
        res += rootSum(root.right,targetSum-val);

        return res;
    }

}
