package com.deng.study.algorithm.linear;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Desc:队列操作
 * @Auther: dengyanliang
 * @Date: 2021/1/23 21:27
 */
@Slf4j
public class MyQueue {
    private int[] array;
    public MyQueue(){
        array = new int[0];
    }

    public int size(){
        return array.length;
    }

    public void add(int element){
        // 创建一个新数组，数组长度是原来数组长度+1
        int[] newArr = new int[array.length+1];
        // 将原来数组的内容拷贝到新数组中
        for(int i = 0; i < array.length; i++){
            newArr[i] = array[i];
        }
        // 在新数组的末尾加入新的元素
        newArr[array.length] = element;
        array = newArr;
    }

    public int poll(){
        checkIsEmpty();
        int element = array[0]; // 第一个元素出队
        int[] newArr = new int[array.length-1];
        for(int i = 0 ; i < array.length-1; i++){
            newArr[i] = array[i+1];
        }
        array = newArr;
        return element;
    }

    private void checkIsEmpty(){
        if(size() == 0){
            throw new RuntimeException("queue is empty");
        }
    }

    public void show(){
        log.info("array elements :{}", Arrays.toString(array));
    }

    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();
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

        myQueue.show();
    }
}
