package com.deng.study.datastru_algorithm.linear;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Desc:数组操作
 * @Auther: dengyanliang
 * @Date: 2021/1/23 19:45
 */
@Slf4j
public class MyArray {
    private int[] array;
    public MyArray(){
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

    public void addElement(int index, int element){
        checkValid(index);
        // 创建一个新数组，数组长度是原来数组长度+1
        int[] newArr = new int[array.length+1];
        // 将原来数组的内容拷贝到新数组中
        for(int i = 0; i < array.length; i++){
            if(i < index){   // 元素的左边，一次拷贝到新数组
                newArr[i] = array[i];
            }else {          // 指定位置右边的元素，向右移动一位，添加到新数组中，指定位置留空
                newArr[i+1] = array[i];
            }
        }
        newArr[index] = element; // 在新数组指定的位置添加上对应元素
        array = newArr;
    }

    public void delete(int index){
        checkValid(index);
        int[] newArr = new int[array.length-1];
        for(int i = 0 ; i < array.length-1; i++){
            if(i < index){ // 删除元素的左边，一次拷贝到新数组
                newArr[i] = array[i];
            }else{         // 删除元素的右边，向左移动一位，添加到新数组中
                newArr[i] = array[i+1];
            }
        }
        array = newArr;
    }

    public void set(int index,int element){
        checkValid(index);
        array[index] = element;
    }

    public int getElement(int index){
        checkValid(index);
        return array[index];
    }

    private void checkValid(int index){
        if(index < 0 || index > array.length - 1){
            throw new RuntimeException("数组越界");
        }
    }


    public void show(){
        log.info("array elements :{}", Arrays.toString(array));
    }


    public int search(int element){
        int index = -1;
        for(int i= 0; i < array.length; i++){
            if(array[i] == element){
                index = i;
                break;
            }
        }
        return index;
    }

    public static void main(String[] args) {

        MyArray myArray = new MyArray();

        myArray.show();

        myArray.add(1);
        myArray.add(2);
        myArray.add(10);
        myArray.add(8);

        int size = myArray.size();
        log.info("new size: {}",size);

        myArray.show();

        myArray.delete(2);
        myArray.show();

        myArray.addElement(2,5);
        myArray.show();

        int element = myArray.getElement(3);
        log.info("get element: {}",element);

        myArray.set(3,100);
        myArray.show();

        int searchIndex = myArray.search(1);
        log.info("search index: {}",searchIndex);
    }
}
