package com.deng.study.algorithm.tree;

import lombok.Data;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-11-20 18:25:09
 */
@Data
public class BTNode<T> {
    T data;
    BTNode<T> lchild;
    BTNode<T> rchild;

    public void preOrder(BTNode<T> rootNode){
        System.out.print(rootNode.data + " ");
        if(rootNode.lchild != null){
            preOrder(rootNode.lchild);
        }
        if(rootNode.rchild != null){
            preOrder(rootNode.rchild);
        }
    }

    public void postOrder(BTNode<T> rootNode){
        if(rootNode.lchild != null){
            postOrder(rootNode.lchild);
        }
        if(rootNode.rchild != null){
            postOrder(rootNode.rchild);
        }
        System.out.print(rootNode.data + " ");
    }

    public void levelOrder(BTNode<T> rootNode){
        Queue<BTNode<T>> queue = new LinkedList<>();
        queue.offer(rootNode);
        BTNode<T> currentNode;
        while(!queue.isEmpty()){
            currentNode = queue.poll();
            System.out.print(currentNode.data + " ");
            if(Objects.nonNull(currentNode.lchild)){
                queue.offer(currentNode.lchild);
            }
            if(Objects.nonNull(currentNode.rchild)){
                queue.offer(currentNode.rchild);
            }
        }
    }


    public void noRecursionPreOrder(BTNode<T> rootNode){
        System.out.println("第一种使用非递归进行先序遍历开始：");
        Stack<BTNode<T>> stack = new Stack<>();
        stack.push(rootNode);   // 先将跟节点进栈

        BTNode<T> currentNode;
        while (!stack.isEmpty()){   // 栈不空
            currentNode = stack.pop();// 栈顶节点出栈
            System.out.print(currentNode.data + " ");

            if(Objects.nonNull(currentNode.rchild))    // 当前节点有右孩子时，将右孩子进栈
                stack.push(currentNode.rchild);

            if(Objects.nonNull(currentNode.lchild))    // 当前节点有左孩子时，将左孩子进栈
                stack.push(currentNode.lchild);
        }
        System.out.println();
    }

    public void noRecursionPreOrder2(BTNode<T> rootNode){
        System.out.println("第二种使用非递归进行先序遍历开始：");
        Stack<BTNode<T>> stack = new Stack<>();
        BTNode<T> currentNode = rootNode;

        while(Objects.nonNull(currentNode) || !stack.isEmpty()){
            // 先处理根节点以及左子树
            while (Objects.nonNull(currentNode)){
                System.out.print(currentNode.data + " ");
                stack.push(currentNode);
                currentNode = currentNode.lchild;
            }
            // 此时栈顶节点要么没有左子树，要么左子树已经遍历过了
            if(!stack.isEmpty()){
                currentNode = stack.pop();
                currentNode = currentNode.rchild;
            }
        }
        System.out.println();
    }

    public void noRecursionMiddleOrder(BTNode<T> rootNode){
        System.out.println("使用非递归进行中序遍历开始：");
        Stack<BTNode<T>> stack = new Stack<>();
        BTNode<T> currentNode = rootNode;
        while (!stack.isEmpty() || Objects.nonNull(currentNode)){
            while (Objects.nonNull(currentNode)){
                stack.push(currentNode);
                currentNode = currentNode.lchild;
            }
            if(!stack.isEmpty()){
                currentNode = stack.pop();
                System.out.print(currentNode.data + " ");
                currentNode = currentNode.rchild;
            }
        }
        System.out.println();
    }

    public void noRecursionPostOrder(BTNode<T> rootNode){
        System.out.println("使用非递归进行后续遍历开始：");
        Stack<BTNode<T>> stack = new Stack<>();
        BTNode<T> currentNode = rootNode;
        BTNode<T> tempNode;
        boolean flag;
        do {
            while(Objects.nonNull(currentNode)){  // currentNode及其左下节点进栈
                stack.push(currentNode);
                currentNode = currentNode.lchild;
            }
            tempNode = null;                     // 指向前一个被访问的节点或为空
            flag = true;                         // 表示在处理栈顶元素
            while(!stack.isEmpty() && flag){
                currentNode = stack.peek();      // 访问当前栈顶节点。因为上一个while循环结束的时候，currentNode为空
                if(currentNode.rchild == tempNode){   // 若currentNode节点的右子树已访问或为空
                    System.out.print(currentNode.data + " ");  // 输出当前节点
                    stack.pop();                // 被访问的节点退栈
                    tempNode = currentNode;               // 指向刚刚被访问的currentNode节点
                }else {
                    currentNode = currentNode.rchild;     // currentNode节点的右子树不为空并且没有访问
                    flag = false;               // 退出栈顶元素的处理，装箱tempNode的右子树。第一个while循环会把currentNode入栈
                }
            }
        }while (!stack.isEmpty());

        System.out.println();
    }
}
