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

    public Josephus(){

    }


    /**
     * 尾插法：需要借助一个尾指针变量来实现
     * @param element
     */
    public void addLast(int element){
        Node tail = head;     // 从头开始遍历
        if(head == null){
            Node newNode = new Node(element);
            newNode.next = head;
            head = newNode;
        }else{
            while(tail.next != null && tail.next != head){
                tail = tail.next; // 遍历到结尾
            }
            Node newNode = new Node(element);
            tail.next = newNode;
            newNode.next = head;
        }
        size++;
    }

    /**
     * 注意与deleteAllByValue的区别  这里只删除找到的第一个元素
     */
    public void delete1(){
        // 删除第i个元素，那么需要找到i-1个元素
        Node current = head; // 指向第一个节点
        int i = 1;

//        Node tail = head;
//        while(tail.next != null && tail.next != head){
//            tail = tail.next; // 遍历到结尾
//        }
//        Node pre = tail;
        Node pre = head;
        while(size > 1){
            if(i % 3 == 0){
                System.out.println("delete currentNode by linkedlist:" + current.data);
                pre.next = current.next;
                current = current.next;
                size--;
            }else{
                pre = current;
                current = current.next;
            }
            i++;
        }
    }

    /**
     * 显示节点数据
     */
    public void show(){
        Node current = head;
        while(current.next != head){
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.print(current.data);
        System.out.println();
    }

    public LinkedList add(int number){
        LinkedList<Integer> linkedList = new LinkedList<>();
        for(int i =0; i <number; i++){
            linkedList.add(i+1);
        }
        return linkedList;
    }


    /**
     * 有问题 TODO
     * @param linkedList
     */
    public void delete(LinkedList linkedList){
        if(CollectionUtils.isEmpty(linkedList)){
            return;
        }

        int i = 0;
        while(linkedList.size() > 1){
            for(int j = 1; j < 3; j++){
                if(i == linkedList.size() -1){
                    i = 0;
                }else{
                    i++;
                }
            }
//            if (i == linkedList.size()) {
//                System.out.println("begin  :");
//                i = 0;
//            }
//            System.out.println("i: "+i +",linkedList.size():"+linkedList.size());
            System.out.println("delete currentNode: "+linkedList.get(i));
            linkedList.remove(i);
        }
        System.out.println("delete currentNode: " + linkedList.get(0));
    }


    /**
     * 第二种办法，使用数组模拟
     * @param total
     * @param keyNumber
     * @return
     */
    private static void compute(Integer total, Integer keyNumber) {
        //开始时设置一个长度为总人数的数组，并将元素都设为true
        Boolean[] people = new Boolean[total];
        for(int i = 0; i < total; i++){
            people[i] = true;
        }

        int count = 0; // 计数器
        int index = 0; // 索引，用于记录被删除的人
        int peopleLeft = total; // 剩余的人数，刚开始等于总数
        while(peopleLeft > 1){
            if(people[index]){ //当前人没有被删除
                count++;
                if(count == keyNumber){ // 计数等于关键数字时
                    people[index] = false; // 删除当前数字
                    count = 0;    // 从头开始
                    peopleLeft--; // 总数减1
                    System.out.println("delete current people:" + (index+1));
                }
            }
            index++;  // 记录整体的数字

            if(index == total){ // 遍历到数组尾部，从头开始
                index = 0;
            }
        }
        for(int j = 0; j < total; j++){
            if(people[j]){ // 删除剩余的那个人
                System.out.println("delete current people:" + (j+1));
            }
        }
    }


    public static void main(String[] args) {
        Josephus josephus = new Josephus();

        josephus.addLast(1);
        josephus.addLast(2);
        josephus.addLast(3);
        josephus.addLast(4);
        josephus.addLast(5);
        josephus.addLast(6);
        josephus.addLast(7);

        josephus.show();

        josephus.delete1();
//        cycleSingleLinkedList.show();
//
//        LinkedList linkedList = josephus.add(6); // 有问题
//        System.out.println(" " + linkedList);
//        josephus.delete(linkedList);

        compute(6,3);
    }






}
