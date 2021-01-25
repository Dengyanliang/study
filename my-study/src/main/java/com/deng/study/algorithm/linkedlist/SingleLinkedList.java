package com.deng.study.algorithm.linkedlist;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/1/23 22:11
 */
@Slf4j
public class SingleLinkedList {

    private Node head;
    int size;

    // https://blog.csdn.net/m0_37572458/article/details/78199507
    public SingleLinkedList(){
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
        newNode.next = head.next; // 添加第一个节点时，next域为空，刚好符合
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
        size++;
    }

    public int size(){
        return size;
    }

    /**
     * 按索引查找节点数据
     * @param index
     * @return
     */
    public Node findByIndex(int index){
        if(index < 0 || index > size){
            throw new RuntimeException("查询数据超限");
        }
        if(index == 0){
            return head;
        }

        int j = 1;
        Node p = head.next;
        while(p != null && j < index){
            p = p.next;
            j++;
        }
        return p;
    }

    /**
     * 按值查找数据
     * @param data
     * @return
     */
    public Node findByValue(int data){
        Node p = head.next;
        while(p != null && p.data != data){
            p = p.next;
        }
        if(p == null) // 遍历到最后一个节点还没有查询到
            return null;
        return p;
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

    /**
     * 在第几个位置插入数据，如果超过最大位置，则插入到最后
     * @param index
     * @param element
     */
    public void insert(int index, int element){
        if(index < 1){
            throw new RuntimeException("插入位置超限");
        }
        Node p = head.next;
        int j = 1;
        while(p != null && j < index-1){
            p = p.next;
            j++;
        }
        Node newNode = new Node(element);
        newNode.next = p.next;
        p.next = newNode;
    }

    public static void main(String[] args) {
//        Node n1 = new Node(3);
////        n1.append(4).append(5);
//
//        n1 = n1.addFirst(6);
//        n1 = n1.addFirst(7);
//        n1.show();
//        log.info("node append:{}",n1.next().next().getData());
//        log.info("node isLast:{}",n1.isLast());
//        n1.show();
//        n1.delete(7);
//
//        n1.show();

        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addFirst(1);
        singleLinkedList.addFirst(2);
        singleLinkedList.addFirst(3);

        singleLinkedList.addLast(4);
        singleLinkedList.addLast(5);
        singleLinkedList.addLast(6);

        singleLinkedList.show();

        int count = singleLinkedList.size();
        System.out.println("count:"+count);

        Node node = singleLinkedList.findByIndex(4);
        System.out.println(node.data);

        node = singleLinkedList.findByValue(1);
        System.out.println(Objects.nonNull(node) ? node.data : null);

        singleLinkedList.insert(3,100);
        singleLinkedList.show();
    }
}
