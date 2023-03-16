package com.deng.study.datastru_algorithm.linear;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:队列操作：有问题，元素出队后，不能被重复利用
 * @Auther: dengyanliang
 * @Date: 2021/1/23 21:27
 */
@Slf4j
public class MyQueue {
    private int[] array;
    private int maxSize;
    // 有两个指针
    private int front = -1; // 指向队列头的前一个位置
    private int rear = -1;  // 指向队列尾
    private int size;

    public MyQueue(){
        array = new int[0];
        maxSize = 0;
    }
    public MyQueue(int size){
        array = new int[size];
        this.maxSize = size;
    }

    public int size(){
        return size;
    }

    public void add(int element){
        if(checkIsFull()){
            log.info("queue is full");
            return;
        }
        rear++;
        array[rear] = element;
        size++;
    }

    public int poll(){
        checkIsEmpty();
        front++;
        int element = array[front];
        size--;
        return element;
    }

    public int peek(){
        checkIsEmpty();
        return array[front+1]; // 注意要加1
    }

    private void checkIsEmpty(){
        if(front == rear){
            throw new RuntimeException("queue is empty");
        }
    }

    private boolean checkIsFull(){
        return rear == maxSize - 1;
    }

    public void show(){
        checkIsEmpty();
        log.info("array elements :");
        for(int i = front + 1; i <= rear; i++){ // 这里要注意指针 front 和 rear
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue(5);
        myQueue.add(9);
        myQueue.add(3);
        myQueue.add(10);
        myQueue.add(13);
        myQueue.add(12);

        myQueue.show();

        int element = myQueue.poll();
        log.info("element 1:{}",element);

        element = myQueue.poll();
        log.info("element 2:{}",element);
        myQueue.poll();
        myQueue.poll();
//        myQueue.poll();
//        myQueue.poll();

        myQueue.show();

        System.out.println(myQueue.peek());

        myQueue.add(100);
    }
}
