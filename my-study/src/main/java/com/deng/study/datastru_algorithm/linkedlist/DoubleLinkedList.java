package com.deng.study.datastru_algorithm.linkedlist;

/**
 * @Desc:双向链表
 * @Auther: dengyanliang
 * @Date: 2021/2/2 15:16
 */
public class DoubleLinkedList {
    private Node head;
    int size;

    public DoubleLinkedList(){
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
        newNode.pre = head;
        if(head.next != null){
            head.next.pre = newNode;
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
        while(tail.next != null){
            tail = tail.next; // 遍历到结尾
        }
        Node newNode = new Node(element);
        tail.next = newNode;
        newNode.pre = tail;
        size++;
    }

    /**
     * 注意与deleteAllByValue的区别  这里只删除找到的第一个元素
     * @param element
     */
    public void deleteByValue(int element){
        // 删除第i个元素，那么需要找到i-1个元素
        Node current = head.next; // 指向第一个节点
        while(current != null && current.data != element){
            current = current.next;
        }
        if(current != null){
            current.pre.next = current.next;
            if(current.next != null){
                current.next.pre = current.pre;
            }
        }
    }

    /**
     * 显示节点数据
     */
    public void show(){
        Node current = head.next;
        while(current != null){
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.addFirst(3);
        doubleLinkedList.addFirst(2);

        doubleLinkedList.addLast(6);
        doubleLinkedList.addLast(4);
        doubleLinkedList.addLast(5);
        doubleLinkedList.addLast(1);
        doubleLinkedList.addLast(7);

        doubleLinkedList.show();

        doubleLinkedList.deleteByValue(6);
        doubleLinkedList.show();
    }
}
