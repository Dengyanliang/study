package com.deng.study.algorithm.linear;

import lombok.Data;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/1/23 21:38
 */
public class Node {
    private int data;  // 节点内容
    private Node next; // 下一个节点

    public Node(){

    }

    public Node(int data){
        this.data = data;
    }

    /**
     * 头插法
     * @param element
     * @return
     */
    public Node addFirst(int element){
        Node newNode = new Node(element);
        newNode.next = this;

        return newNode;
    }

    /**
     * 尾插法
     * @param element
     */
    public Node addLast(int element){
        // 当前节点
        Node current = this;
//        while(true){
//            // 当前节点的下一个节点
//            Node nextNode = current.next;
//            // 下一个节点为空，则结束循环
//            if(nextNode == null){
//                break;
//            }
//            // 把下一个节点赋值给当前节点
//            current = nextNode;
//        }
        // 下一个节点不为空
        while(current.next != null){
            // 把下一个节点赋值给当前节点
            current = current.next;
        }
        // 构造新的节点
        Node newNode = new Node(element);
        // 当前节点的下一个指针指向新的节点
        current.next = newNode;
        return this;
    }

    /**
     * 删除元素 TODO
     * @param element
     */
    public void delete(int element){
        Node current = this;
        Node preNode = this;
        while(current != null){
            if(current.data == element){
                preNode.next = current.next;
                current = current.next;;
            }else{
                preNode = current;
                current = current.next;
            }
        }
    }

    public Node next(){
        return this.next;
    }

    public int getData(){
        return this.data;
    }

    public boolean isLast(){
        return next == null;
    }

    public void show(){
        Node current = this;
        while(current != null){
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}
