package com.deng.study.datastru_algorithm.sort;


import com.deng.common.util.MyArrayUtil;

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
//        sort2(arr,0,arr.length-1);
        sort3(arr,0,arr.length-1);
    }

    /**
     * 第二种方法，使用第一个数字作为标杆
     * @param arr
     * @param begin
     * @param end
     */
    private static void sort2(int[] arr,int begin,int end){
        if(begin >= end){
            return;
        }
        // 第0个位置的元素作为标准数
        int stard = arr[begin];
        int low = begin;    // 低位指针
        int high = end;     // 高位指针
        while(low < high){
            // 如果右边的数字比标准数大
            while(low < high && stard <= arr[high]){
                high--;     // 高位指针左移
            }
            // 右边的数字比标准数小，则使用右边的数字替换左边的数字
            arr[low] = arr[high];

            // 如果左边的数字比标准数小
            while(low < high && stard >= arr[low]){
                low++;      // 低位指针右移
            }
            // 左边的数字比标准数大，则使用左边的数字替换右边的数字
            arr[high] = arr[low];
        }
        // 把标准数赋值给低位所在位置的元素
        arr[low] = stard;
        // 处理所有小的数字
        sort2(arr,begin,low);   // 上面循环结束后，low的位置已经右移，所以就从begin~~low递归调用即可
        // 处理所有大的数字
        sort2(arr,low+1,end);
    }

    private static void sort3(int[] arr,int begin,int end) {
        if (begin >= end) {
            return;
        }
        int partition = getPartition2(arr, begin, end); // 得到分区
        sort3(arr, begin, partition - 1);
        sort3(arr, partition + 1, end);
    }

    /**
     * 单边循环：
     *  1、选择最右边元素作为基准点
     *  2、j指针负责找到比基准点小的元素，一旦找到则与i进行交换
     *  3、i指针维护小于基准点元素的边界，也是每次交换的目标索引
     *  4、最后基准点与i交换，i即为分区位置
     *
     * @param arr
     * @param begin
     * @param end
     * @return
     */
    private static int getPartition2(int[] arr, int begin,int end){
        int stard = arr[end];   // 基准点
        int i = begin;
        for (int j = i; j < end; j++) {
            if(arr[j] < stard){ // 找到了比基准点小的元素
                System.out.println(Arrays.toString(arr) + ",比较前 i=" + i + ",j=" + j);
                if(i != j){
                    System.out.println(Arrays.toString(arr) + ",交换i和j 开始 i=" + i + ",j=" + j);
                    // 将i，j进行交换，这样小的元素就交换到左边
                    MyArrayUtil.swap(arr,i,j);
                    System.out.println(Arrays.toString(arr) + ",交换i和j 结束 i=" + i + ",j=" + j);
                }
                i++;

                System.out.println(Arrays.toString(arr) + ",比较后 i=" + i + ",j=" + j);
                System.out.println();

            }
        }
        if(i != end){
            System.out.println(Arrays.toString(arr) + ",交换i和end 开始 i=" + i + ",end=" + end);
            MyArrayUtil.swap(arr,i,end);
            System.out.println(Arrays.toString(arr) + ",交换i和end 结束 i=" + i + ",end=" + end);
        }
        return i;
    }

    /**
     * 双边循环：
     *  1、以最左边元素为基准点
     *  2、high指针负责从右往左找比基准点小的元素，low指针负责从左往右找比基准点大的元素，一旦找到，二者交换，直到low==high为止
     *  3、最后将基准点与low/high交换，low/high即为分区位置（此时low与high相等）
     *  4、有三个注意点：
     *      4.1 内层循环 low < high 不能少
     *      4.2
     * @param arr
     * @param begin
     * @param end
     */
    private static int getPartition(int[] arr, int begin,int end){
        int stard = arr[begin];  // 基准点
        System.out.println("begin:" + begin + ",end:" + end + ",stard:" + arr[begin]);
        int low = begin; // 从左往右查找比基准点大的元素
        int high = end;  // 从右往左查找比基准点小的元素
        while(low < high){
            // 为何必须先执行右边的，再执行左边的？？  -- 为了避免MyArrayUtil.swap(arr,begin,low) 时将小的值交换到右边
            while(low < high && arr[high] >= stard){
                high--;
            }
            while(low < high && arr[low] <= stard){
                low++;
            }
            // 将低位索引和高位索引交换
            MyArrayUtil.swap(arr,low,high);
        }

        // 将基准点元素和边界值交换
        MyArrayUtil.swap(arr,begin,low);

        System.out.println(Arrays.toString(arr) + ",low=" + low + ",high=" + high);
        return high;
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
                    MyArrayUtil.swap(arr,i,j);
//                    System.out.println("after swap:"+Arrays.toString(arr));
                } else{
                    break;
                }
            }
//            System.out.println("-******-"+Arrays.toString(arr));
            MyArrayUtil.swap(arr,i,right-1);
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
            MyArrayUtil.swap(arr,middle,left);
        }
        if(arr[right] < arr[middle]){
            MyArrayUtil.swap(arr,right,middle);
        }

        if(arr[right] < arr[left]){
            MyArrayUtil.swap(arr,right,left);
        }
        MyArrayUtil.swap(arr,middle,right-1);
//        System.out.println("&&&&&-"+Arrays.toString(arr));
        return arr[right-1];
    }



    public static void main(String[] args) {
        int[] arr = {5, 2, 7, 3, 1, 6, 9, 8};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
