package com.deng.study.algorithm.sort;

import java.util.Arrays;

/**
 * @Desc: 快速排序
 * 通过一趟排序将要排序的数据分割为两部分，其中一部分的数据比另一部分的数据都小，然后进行递归操作
 * 这里采用三平均分区法，即不是选择第一个元素作为中轴，而是选用待排序的最左边的、中间的、最右边的这三个元素中的中间值作为中轴。
 * 这样可以避免，本来这个序列是有序的，按照相反的顺序进行排序时，效率最低这种情况。并且防止数组越界。
 *
 * @Auther: dengyanliang
 * @Date: 2021/1/27 10:21
 */
public class QuickSort {


    public static void sort(int[] arr){
//        sort(arr,0,arr.length-1);
        sort2(arr,0,arr.length-1);
    }


    /**
     * 第二种方法，使用第一个数字作为标杆
     * @param arr
     * @param start
     * @param end
     */
    private static void sort2(int[] arr,int start,int end){
        if(start >= end){
            return;
        }
        // 第0个位置的元素作为标准数
        int stard = arr[start];
        int low = start;
        int high = end;
        while(low < high){
            // 如果右边的数字比标准数大
            while(low < high && stard <= arr[high]){
                high--;
            }
            // 右边的数字比标准数小，则使用右边的数字替换左边的数字
            arr[low] = arr[high];
            // 如果左边的数字比标准数小
            while(low < high && stard >= arr[low]){
                low++;
            }
            // 左边的数字比标准数大，则使用左边的数字替换右边的数字
            arr[high] = arr[low];
        }
        // 把标准数赋值给低所在的位置的元素
        arr[low] = stard;
        // 处理所有小的数字
        sort2(arr,start,low);
        // 处理所有大的数字
        sort2(arr,low+1,end);
    }

    /**
     * 第一种方式：最左边的、中间的、最右边的这三个元素中的中间值作为标杆
     * @param arr
     * @param left
     * @param right
     */
    private static void sort(int[] arr,int left,int right){
        if(left >= right){
            return;
        }
//        System.out.println("left:"+left+",right:"+right);
        int pivot = middle(arr,left,right); // 待比较的数字，也就是标杆
        int i = left, j = right - 1;
//        System.out.println("begin i:"+i+",begin j:"+j);
        if(i != j){
            while(true){
                while(arr[++i] < pivot){ }
                while(arr[--j] > pivot){ }

//                System.out.println("i:"+i+",j:"+j);
                if(i < j){
//                    System.out.println("before swap:"+Arrays.toString(arr));
                    swap(arr,i,j);
//                    System.out.println("after swap:"+Arrays.toString(arr));
                }
                else
                    break;
            }
//            System.out.println("-******-"+Arrays.toString(arr));
            swap(arr,i,right-1);
//            System.out.println("-******-"+Arrays.toString(arr));
//            System.out.println();

            sort(arr,left,i-1);
            sort(arr,i+1,right);
        }
    }

    /**
     * 找出中间值，放在倒数第二个位置上，因为a[middle] <= a[right]
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private static int middle(int[] arr,int left,int right){
        int middle = left + (right-left)/2 ;
//        System.out.println("arr[left]:"+arr[left]+",arr[middle]:"+arr[middle]+",arr[right]:"+arr[right]);
        if(arr[middle] < arr[left]){
            swap(arr,middle,left);
        }
        if(arr[right] < arr[middle]){
            swap(arr,right,middle);
        }

        if(arr[right] < arr[left]){
            swap(arr,right,left);
        }
        swap(arr,middle,right-1);
//        System.out.println("&&&&&-"+Arrays.toString(arr));
        return arr[right-1];
    }


    private static void swap(int[] arr,int a,int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }



    public static void main(String[] args) {
        int[] arr = {2,1,9,8,10,5,7,9,8,6,9};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
