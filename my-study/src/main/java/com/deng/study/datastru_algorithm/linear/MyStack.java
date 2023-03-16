package com.deng.study.datastru_algorithm.linear;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:栈操作
 * @Auther: dengyanliang
 * @Date: 2021/1/23 21:13
 */
@Slf4j
public class MyStack {
    private int[] array;
    private int top = -1; // 栈顶指针
    private int maxSize;  // 栈的最大长度
    private int size;

    public MyStack(){
        array = new int[0];
        maxSize = 0;
    }

    public MyStack(int size){
        array = new int[size];
        this.maxSize = size;
    }

    public int size(){
        return size;
    }

    public void push(int element){
        if(checkIsFull()){
            System.out.println("stack is full");
            return;
        }
        top++;
        array[top] = element;
        size++;
        log.info("push array["+top+"]=" +element+ " success");
    }

    public int pop(){
        checkIsEmpty();
        int element = array[top];
        top--;
        size--;
        return element;
    }

    // 栈是先进后出的，所以一定要从栈顶开始打印，也就是从数组末尾进行打印
    public void show(){
        checkIsEmpty();
        System.out.print("array elements :");
        for(int i = top; i >= 0; i--){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public int peek(){
        checkIsEmpty();
        return array[top];
    }

    private void checkIsEmpty(){
        if(top == -1){
            throw new RuntimeException("stack is empty");
        }
    }

    private boolean checkIsFull(){
        return top == maxSize - 1;
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack(5);
        myStack.push(3);
        myStack.push(9);
        myStack.push(5);
//        myStack.push(4);
//        myStack.push(10);
//        myStack.push(2);

        myStack.show();

        log.info("myStack.pop :{}",myStack.pop());
        log.info("myStack.pop :{}",myStack.pop());
        log.info("myStack.pop :{}",myStack.pop());


        log.info("myStack.peek :{}",myStack.peek());
        log.info("myStack.peek :{}",myStack.peek());
    }
}
