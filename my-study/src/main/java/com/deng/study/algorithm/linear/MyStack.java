package com.deng.study.algorithm.linear;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Desc:栈操作
 * @Auther: dengyanliang
 * @Date: 2021/1/23 21:13
 */
@Slf4j
public class MyStack {
    private int[] array;
    public MyStack(){
        array = new int[0];
    }

    public int size(){
        return array.length;
    }

    public void push(int element){
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

    public int pop(){
        checkIsEmpty();
        int element = array[array.length-1];

        int[] newArr = new int[array.length-1];
        for(int i = 0 ; i < array.length-1; i++){
            newArr[i] = array[i];
        }
        array = newArr;
        return element;
    }

    public void show(){
        log.info("array elements :{}", Arrays.toString(array));
    }

    public int peek(){
        checkIsEmpty();
        return array[size()-1];
    }

    private void checkIsEmpty(){
        if(size() == 0){
            throw new RuntimeException("stack is empty");
        }
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(3);
        myStack.push(9);
        myStack.push(5);

        myStack.show();

        log.info("myStack.pop :{}",myStack.pop());
//        log.info("myStack.pop :{}",myStack.pop());
//        log.info("myStack.pop :{}",myStack.pop());


        log.info("myStack.peek :{}",myStack.peek());
        log.info("myStack.peek :{}",myStack.peek());
    }
}
