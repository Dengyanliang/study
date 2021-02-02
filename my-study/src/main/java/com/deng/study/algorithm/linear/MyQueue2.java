package com.deng.study.algorithm.linear;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:队列操作
 * @Auther: dengyanliang
 * @Date: 2021/1/23 21:27
 */
@Slf4j
public class MyQueue2 {
    private int[] array;
    private int maxSize;
    // 有两个指针
    private int front = 0; // 指向队列头的前一个位置
    private int rear = 0;  // 指向队列尾
    private int size;

    public MyQueue2(){
        array = new int[0];
        maxSize = 0;
    }
    public MyQueue2(int size){
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
        array[rear] = element;
        log.info("push array["+rear+"]=" +element+ " success");

        rear = (rear + 1) % maxSize;
        size++;
    }

    public int poll(){
        checkIsEmpty();
        int element = array[front];
        front = (front + 1) % maxSize;
        size--;
        return element;
    }

    public int peek(){
        checkIsEmpty();
        return array[front]; // 注意要加1
    }

    public int getSize(){
        return (rear + maxSize - front) % maxSize;
    }

    private void checkIsEmpty(){
        if(front == rear){
            throw new RuntimeException("queue is empty");
        }
    }

    private boolean checkIsFull(){
        return (rear + 1 ) % maxSize == front;
    }

    public void show(){
        checkIsEmpty();
        log.info("array elements :");
        for(int i = front; i < front + getSize(); i++){ // 这里要注意指针 front 和 rear
            System.out.print("array["+i % maxSize+"] = "+ array[i % maxSize] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyQueue2 myQueue = new MyQueue2(5);
        myQueue.add(9);
        myQueue.add(3);
        myQueue.add(10);
        myQueue.add(13);
//        myQueue.add(12);

        myQueue.show();

        int element = myQueue.poll();
        log.info("element 1:{}",element);

//        element = myQueue.poll();
//        log.info("element 2:{}",element);
//
//        element = myQueue.poll();
//        log.info("element 3:{}",element);

        element = myQueue.poll();
        log.info("element 4:{}",element);

//        element = myQueue.poll();
//        log.info("element 5:{}",element);

        myQueue.show();

//        System.out.println(myQueue.peek());

        myQueue.add(100);
        myQueue.add(200);
        myQueue.add(300);

        myQueue.show();
    }
}
