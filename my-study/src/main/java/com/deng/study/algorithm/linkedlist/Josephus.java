package com.deng.study.algorithm.linkedlist;

import org.springframework.util.CollectionUtils;

import java.util.LinkedList;

/**
 * @Desc:约瑟夫问题，也就是常玩的丢手绢
 * @Auther: dengyanliang
 * @Date: 2021/2/3 10:39
 */
public class Josephus {
    private Node head;
    int size;

    public Josephus() {

    }


    /**
     * 尾插法：需要借助一个尾指针变量来实现
     *
     * @param element
     */
    public void addLast(int element) {
        Node tail = head;     // 从头开始遍历
        if (head == null) {
            Node newNode = new Node(element);
            newNode.next = head;
            head = newNode;
        } else {
            while (tail.next != null && tail.next != head) {
                tail = tail.next; // 遍历到结尾
            }
            Node newNode = new Node(element);
            tail.next = newNode;
            newNode.next = head;
        }
        size++;
    }

    /**
     * 使用尾插法插入数据
     * @param nums 总的节点数
     */
    public void addElements(int nums) {
        if(nums < 1){
            System.out.println("nums的值不正确");
            return;
        }
        Node preNode = null;    // 当前节点的前一个节点。用来构成环形链表
        for (int i = 1; i <= nums; i++) {
            Node newNode = new Node(i);  // 构建新节点
            if(i == 1){
                head = newNode;
                head.next = newNode;   // 构成环
                preNode = newNode;     // preNode指向新节点，也就是最后一个节点
            }else{
                preNode.next = newNode;
                newNode.next = head;    // 指向头节点
                preNode = newNode;      // preNode指向新节点，也就是最后一个节点
            }
        }
    }

    /**
     *
     */
    public void deleteByLinkedList(int startNo,int countNum) {
        // 删除第i个元素，那么需要找到i-1个元素
        Node current = head; // 指向第一个节点
        int i = 1;  // 从第几个开始数数

        Node tail = head;
        while(tail.next != null && tail.next != head){
            tail = tail.next; // 遍历到结尾
        }
//        Node pre = tail;
//        for (int j = 0; j < startNo-1; j++) {
//            pre = pre.next;
//            current = current.next;
//        }
        Node pre = head;
        while (size >= 1) {
            if (i % 3 == 0) { // 每次数到几，就删除当前节点
                System.out.println("delete currentNode by linkedlist:" + current.data);
                pre.next = current.next;
                current = current.next;
                size--;
            } else {
                pre = current;
                current = current.next;
            }
            i++;
        }
    }


    /**
     * 约瑟夫问题
     * @param startNo 开始节点
     * @param countNum 每隔几个数就删除一个结点
     * @param nums 总的节点数
     */
    public void deleteByLinkedList(int startNo,int countNum,int nums) {
        // 删除前校验，开始报数不能<1并且不能大于总数
        if(head == null || startNo < 1 || startNo > nums){
            System.out.println("参数有误，请重新输入");
            return;
        }
        Node current = head;
        Node pre = current; // 指向当前节点的前一个节点，开始时指向最后一个结点
        while(pre.next!= null && pre.next != current){
            pre = pre.next;
        }
        for (int i = 0; i < startNo - 1; i++) {
            pre = pre.next;
            current = current.next;
        }
        while(true){
            if(pre == current){ // 只有一个结点，直接删除，并且退出循环
                System.out.println("delete currentNode by linkedlist:" + current.data);
                break;
            }
            // 让pre和current同时移动countNum-1次
            for (int i = 1; i < countNum; i++) {
                pre = pre.next;
                current = current.next;
            }
            // 此时current就是要删除的节点
            System.out.println("delete currentNode by linkedlist:" + current.data);
            // 将当前节点删除
            current = current.next;
            // 前一个节点指向当前节点，继续下一轮的循环
            pre.next = current;
        }
    }

    /**
     * 显示节点数据
     */
    public void show() {
        Node current = head;
        System.out.println("show begin..");
        while (current.next != head) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.print(current.data);
        System.out.println("\nshow end..");
    }

    public LinkedList add(int number) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            linkedList.add(i + 1);
        }
        return linkedList;
    }

    /**
     * 第二种办法，使用数组模拟
     *
     * @param total
     * @param keyNumber
     * @return
     */
    private static void deleteByArray(Integer total, Integer keyNumber) {
        //开始时设置一个长度为总人数的数组，并将元素都设为true
        Boolean[] people = new Boolean[total];
        for (int i = 0; i < total; i++) {
            people[i] = true;
        }

        int count = 0; // 计数器
        int index = 0; // 索引，用于记录被删除的人
        int peopleLeft = total; // 剩余的人数，刚开始等于总数
        while (peopleLeft >= 1) {
            if (people[index]) { //当前人没有被删除
                count++;
                if (count == keyNumber) { // 计数等于关键数字时
                    people[index] = false; // 删除当前数字
                    count = 0;    // 从头开始
                    peopleLeft--; // 总数减1
                    System.out.println("delete current people:" + (index + 1));
                }
            }
            index++;  // 记录整体的数字

            if (index == total) { // 遍历到数组尾部，从头开始
                index = 0;
            }
        }
    }


    public static void main(String[] args) {
        Josephus josephus = new Josephus();

//        josephus.addLast(1);
//        josephus.addLast(2);
//        josephus.addLast(3);
//        josephus.addLast(4);
//        josephus.addLast(5);
//        josephus.addLast(6);
//        josephus.addLast(7);

        josephus.addElements(7);

        josephus.show();

        josephus.deleteByLinkedList(1,3,7);
//        cycleSingleLinkedList.show();
//
//        LinkedList linkedList = josephus.add(6); // 有问题
//        System.out.println(" " + linkedList);
//        josephus.delete(linkedList);

        deleteByArray(7, 3);
    }


}
