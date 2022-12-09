package com.deng.study.algorithm.tree;

import lombok.Data;

import java.util.Objects;

/**
 * @Desc: 线索二叉树的实现
 * @Author: dengyanliang
 * @Date: 2022-12-03 11:17:49
 */
@Data
public class ThreadClass {
    BTNode root;    // 二叉树的跟结点
    BTNode head;    // 线索二叉树的头结点，左孩子指向二叉树的跟结点，右孩子执行最后一个结点
    BTNode pre;     // 前驱结点

    public void createThread(){
        head = new BTNode();        // 创建头结点
        head.ltag = 0;              // 头结点的左标识为0，右标识为1。
        head.rtag = 1;

        if(root == null){          // 空树
            head.lchild = head;
            head.rchild = null;
        }else{                    // 树不空
            head.lchild = root;   // 左孩子指向二叉树的跟结点，
            pre = head;           // pre是当前结点的前驱结点，用于线索化，初始时指向根节点
            doMiddleThread(root);
            pre.rchild = head;   // 最后一个结点的右孩子指向头结点----- 形成一个环
            head.rchild = pre;   // 头结点的右孩子指向遍历的最后一个结点
        }
    }

    /**
     * 中序线索二叉树
     * @param currentNode
     */
    public void doMiddleThread(BTNode currentNode) {
        if (Objects.nonNull(currentNode)) {
            doMiddleThread(currentNode.lchild);      // 处理左孩子
            if (currentNode.lchild == null) {  // 结点的左指针为空
                currentNode.lchild = pre;      // 给当前结点添加前驱线索
                currentNode.ltag = 1;
            } else {
                currentNode.ltag = 0;
            }

            if (pre.rchild == null) {       // 结点pre的右指针为空
                pre.rchild = currentNode;   // 给结点pre添加后继线索
                pre.rtag = 1;
            } else {
                pre.rtag = 0;
            }
            pre = currentNode;              // 置当前结点为下一个访问结点的前驱结点
            doMiddleThread(currentNode.rchild);   // 处理右孩子
        }
    }

    /**
     * 遍历中序线索二叉树
     */
    public void middleThreadOrder(){
        BTNode currentNode = head.lchild;     // 指向跟结点
        while (Objects.nonNull(currentNode) && currentNode != head) {
            while (currentNode != head && currentNode.ltag == 0) {  // 找中序开始结点
                currentNode = currentNode.lchild;
            }
            System.out.print(currentNode.data + " "); // 访问当前节点
            while (currentNode.rchild != head && currentNode.rtag == 1) {
                currentNode = currentNode.rchild;           // 如果是线索，一直访问下去
                System.out.print(currentNode.data);         // 访问当前结点
            }
            currentNode = currentNode.rchild;               // 如果不再是线索，转向其右子树
        }
    }
}
