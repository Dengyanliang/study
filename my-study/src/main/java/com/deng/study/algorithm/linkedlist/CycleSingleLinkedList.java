package com.deng.study.algorithm.linkedlist;

/**
 * @Desc:循环单链表
 * @Auther: dengyanliang
 * @Date: 2021/2/2 17:13
 */
public class CycleSingleLinkedList {
    private Node head;
    int size;

    public CycleSingleLinkedList(){
        Node current = new Node();
        current.next = null;
        head = current;
    }

    /**
     * 头插法
     * @param element
     * @return
     */
    public void addFirst(int element){
        Node newNode = new Node(element);
        newNode.next = head.next; // 第一次添加时，head.next为null。刚好符合
        if(newNode.next == null){
            newNode.next = head;
        }
        head.next = newNode;
        size++;
    }

    /**
     * 尾插法：需要借助一个尾指针变量来实现
     * @param element
     */
    public void addLast(int element){
        Node tail = head;     // 从头开始遍历
        while(tail.next != head){
            tail = tail.next; // 遍历到结尾
        }
        Node newNode = new Node(element);
        tail.next = newNode;
        newNode.next = head;
        size++;
    }

    /**
     * 注意与deleteAllByValue的区别  这里只删除找到的第一个元素
     * @param element
     */
    public void deleteByValue(int element){
        // 删除第i个元素，那么需要找到i-1个元素
        Node current = head.next; // 指向第一个节点
        Node pre = head;
        while(current != head && current.data != element){
            pre = current;
            current = current.next;
        }
        if(current != head){
            pre.next = current.next;
        }
    }

    /**
     * 显示节点数据
     */
    public void show(){
        Node current = head.next;
        while(current != head){
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        CycleSingleLinkedList cycleSingleLinkedList = new CycleSingleLinkedList();
        cycleSingleLinkedList.addFirst(3);
        cycleSingleLinkedList.addFirst(2);

        cycleSingleLinkedList.addLast(6);
        cycleSingleLinkedList.addLast(4);
        cycleSingleLinkedList.addLast(5);
        cycleSingleLinkedList.addLast(1);
        cycleSingleLinkedList.addLast(7);

        cycleSingleLinkedList.show();

        cycleSingleLinkedList.deleteByValue(7);
        cycleSingleLinkedList.show();
    }

}
