package com.deng.study.datastru_algorithm.tree;

import com.deng.common.enums.ThreadClassEnum;
import lombok.Data;

import java.util.Objects;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-12-10 11:18:51
 */
@Data
public class ThreadClass {
    BTNode head;    // 线索二叉树的头结点，左孩子指向二叉树的跟结点，右子树指向遍历的最后一个结点
    BTNode root;    // 二叉树的跟结点
    BTNode pre;     // 前驱结点
    ThreadClassEnum threadClassType;

    public void createThread(){
        head = new BTNode();    // 创建头结点
        head.ltag = 0;          // 头结点的左标识为0，右标识为1
        head.rtag = 1;

        if(Objects.isNull(root)){   // 二叉树是个空树
            head.lchild = head;
            head.rchild = null;
        }else{                      // 树不空
            head.lchild = root;     // 头结点的左孩子指向二叉树的跟结点
            pre = head;             // pre是当前节点的前驱结点，用于线索化，初始时指向头结点

            if (threadClassType == ThreadClassEnum.PRE) {
                doPreThread(root);    // 先序
            } else if (threadClassType == ThreadClassEnum.MIDDLE) {
                doMiddleThread(root); // 中序
            } else {
                doPostThread(root);   // 后序
            }

            pre.rchild = head;      // 最后一个结点的右孩子指向头结点-----形成一个环
            head.rchild = pre;      // 头结点的右孩子指向最后一个结点
        }
    }

    public void threadOrder(){
        if(threadClassType == ThreadClassEnum.PRE){
            preThreadOrder();    // 先序
        }else if(threadClassType == ThreadClassEnum.MIDDLE){
            middleThreadOrder(); // 中序
        }else{
            postThreadOrder();   // 后序
        }
    }

    private void doPreThread(BTNode currentNode){
        if(Objects.nonNull(currentNode)){
            if(Objects.isNull(currentNode.lchild)){
                currentNode.lchild = pre;
                currentNode.ltag = 1;
            }else{
                currentNode.ltag = 0;
            }
            if(Objects.isNull(pre.rchild) && pre.lchild != currentNode){
                pre.rchild = currentNode;
                pre.rtag = 1;
            }else{
                pre.rtag = 0;
            }
            pre = currentNode;
            if(currentNode.ltag == 0){
                doPreThread(currentNode.lchild);
            }
            doPreThread(currentNode.rchild);
        }
    }

    private void doMiddleThread(BTNode currentNode){
        if(Objects.isNull(currentNode)) {
            return;
        }
        doMiddleThread(currentNode.lchild);         // 左子树线索化
        if(Objects.isNull(currentNode.lchild)){     // 当前节点的左孩子为空
            currentNode.lchild = pre;               // 左孩子指向前驱结点
            currentNode.ltag = 1;                   // 左标识指向前驱结点的线索
        }else{
            currentNode.ltag = 0;                   // 左标识指向左孩子结点
        }

        // 第一次调用时pre的lchild在方法调用前已经处理过了，所以不再处理
        if(Objects.isNull(pre.rchild)){             // 前驱结点右孩子为空
            pre.rchild = currentNode;               // 前驱结点添加后继线索
            pre.rtag = 1;
        }else{
            pre.rtag = 0;
        }
        pre = currentNode;                          // 置当前节点为下一次要访问结点的前驱结点
        doMiddleThread(currentNode.rchild);         // 右子树线索化
    }

    private void doPostThread(BTNode currentNode){
        if(Objects.isNull(currentNode)) {
            return;
        }
        doPostThread(currentNode.lchild);         // 左子树线索化
        doPostThread(currentNode.rchild);         // 右子树线索化

        if(Objects.isNull(currentNode.lchild)){     // 当前节点的左孩子为空
            currentNode.lchild = pre;               // 左孩子指向前驱结点
            currentNode.ltag = 1;                   // 左标识指向前驱结点的线索
        }else{
            currentNode.ltag = 0;                   // 左标识指向左孩子结点
        }

        // 第一次调用时pre的lchild在方法调用前已经处理过了，所以不再处理
        if(Objects.isNull(pre.rchild)){             // 前驱结点右孩子为空
            pre.rchild = currentNode;               // 前驱结点添加后继线索
            pre.rtag = 1;
        }else{
            pre.rtag = 0;
        }
        pre = currentNode;                          // 置当前节点为下一次要访问结点的前驱结点
    }

    public void preThreadOrder(){
        BTNode currentNode = head.lchild;
        while(Objects.nonNull(currentNode) && currentNode != head){ // 当前节点不空且不等于头节点
            System.out.print(currentNode.data + " ");               // 访问跟结点
            while(currentNode != head && currentNode.ltag == 0){    // 处理左子树
                currentNode = currentNode.lchild;
                System.out.print(currentNode.data + " ");
            }

            while(currentNode.rchild != head && currentNode.rtag == 1){ // 如果当前节点的右标识是线索，一直找下去，节省空间
                currentNode = currentNode.rchild;
                System.out.print(currentNode.data + " ");
            }
            // TODO 在处理完线索后，左孩子还可能是存在的，所以要加上这个逻辑
            if(currentNode.ltag == 0){
                currentNode = currentNode.lchild;
                System.out.print(currentNode.data + " ");
            }
            currentNode = currentNode.rchild;        // 如果不再是线索，转向其右子树
        }
    }

    public void middleThreadOrder(){
        BTNode currentNode = head.lchild;       // 指向二叉树的跟结点
        while(Objects.nonNull(currentNode) && currentNode != head){ // 当前节点不空且不等于头节点
            while(currentNode != head && currentNode.ltag == 0){    // 找中序遍历开始结点
                currentNode = currentNode.lchild;
            }
            System.out.print(currentNode.data + " ");
            while(currentNode.rchild != head && currentNode.rtag == 1){ // 如果当前节点的右标识是线索，一直找下去，节省空间
                currentNode = currentNode.rchild;
                System.out.print(currentNode.data + " ");
            }
            currentNode = currentNode.rchild;        // 如果不再是线索，转向其右子树
        }
    }

    // TODO 比较难
    public void postThreadOrder(){
        BTNode currentNode = head.lchild;
        while(Objects.nonNull(currentNode) && currentNode != head){ // 当前节点不空且不等于头节点
            while(currentNode != head && currentNode.ltag == 0){    // 处理左子树
                currentNode = currentNode.lchild;
            }

            while(currentNode.rchild != head && currentNode.rtag == 1){ // 如果当前节点的右标识是线索，一直找下去，节省空间
                currentNode = currentNode.rchild;
                System.out.print(currentNode.data + " ");
            }
            currentNode = currentNode.rchild;        // 如果不再是线索，转向其右子树
            System.out.print(currentNode.data + " ");               // 访问跟结点
        }
    }

}
