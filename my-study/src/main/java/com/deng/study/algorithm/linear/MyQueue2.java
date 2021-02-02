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
    private int front = 0; // 指向队列头
    private int rear = 0;  // 指向队列尾
    private int count;

    public MyQueue2(){
        array = new int[0];
        maxSize = 0;
    }
    public MyQueue2(int size){
        array = new int[size];
        this.maxSize = size;
    }

    public int size(){
        return count;
    }

    public void add(int element){
        if(checkIsFull()){
            log.info("queue is full");
            return;
        }
        array[rear] = element;
        log.info("push array["+rear+"]=" +element+ " success");

        rear = (rear + 1) % maxSize;
        count++;
    }

    public int poll(){
        checkIsEmpty();
        int element = array[front];
        front = (front + 1) % maxSize;
        count--;
        return element;
    }

    public int peek(){
        checkIsEmpty();
        return array[front]; // 注意要加1
    }

    /**
     * 可用空间的元素
     * @return
     */
    public int getAvaliableSize(){
        // rear = 3 front = 1 maxSize = 5   (3+5-1) % 5 = 2
        // rear = 1 front = 4 maxSize = 5   (1+5-4) % 5 = 2

        int rear2 = (front + count) %maxSize;
        int front2 = (rear-count+maxSize)%maxSize;
        int count2 =  (rear + maxSize - front) % maxSize;

        int rear1 = (front + count) %maxSize;
        int front1 = (rear - count+maxSize)%maxSize;
        int count1 =  (rear + maxSize - front) % maxSize;
        return count;
    }

    private void checkIsEmpty(){
        boolean flag2 = rear==front;  // 能存放maxSize-1个元素时
        if(count == 0){               // 能存放maxSize个元素
            throw new RuntimeException("queue is empty");
        }
    }

    private boolean checkIsFull(){
        boolean flag = front == (rear+1)%maxSize; // 能存放maxSize-1个元素时
        return count == maxSize;  // 能存放maxSize个元素
    }

    public void show(){
        checkIsEmpty();
        log.info("array elements :");
        for(int i = front; i < front + size(); i++){ // 这里要注意指针 front 和 rear
            System.out.print("array["+i % maxSize+"] = "+ array[i % maxSize] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyQueue2 myQueue = new MyQueue2(5);
        myQueue.add(9);
        myQueue.add(3);
        myQueue.add(10);
        myQueue.poll();
        myQueue.add(13);
        myQueue.add(12);
        myQueue.add(1);
        myQueue.getAvaliableSize();
        myQueue.size();

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
