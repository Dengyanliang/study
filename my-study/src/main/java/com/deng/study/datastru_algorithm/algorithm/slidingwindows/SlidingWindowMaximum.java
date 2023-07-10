package com.deng.study.datastru_algorithm.algorithm.slidingwindows;


import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Desc: 滑动窗口
 * @Auther: dengyanliang
 * @Date: 2023/6/6 20:50
 */
public class SlidingWindowMaximum {
    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;

        SlidingWindowMaximum slidingWindowMaximum = new SlidingWindowMaximum();
        int[] result = slidingWindowMaximum.maxSlidingWindow(nums,k);
        for (int maxNum : result) {
            System.out.print(maxNum + "\t");
        }
    }

    /**
     * 1、暴力法：遍历每一个窗口，窗口中遍历每一个数，找最大
     *    时间复杂度：O(n*k)
     *    空间复杂度：O(n-k)
     *
     * @param nums 给定的数组
     * @param k 窗口的最大值
     * @return 返回每个窗口中的最大值
     */
    private int[] maxSlidingWindow1(int[] nums,int k){
        int n = nums.length;
        // 定义一个窗口，用于输出窗口最大值序列
        // 最后一个窗口的起始位置的索引下标是从n-k ~~ n-1，并且数组下标是从0开始的，所以输出值共有n-k+1个
        int[] result = new int[n-k+1];

        // 遍历所有窗口，以窗口起始位置为代表。从0开始遍历到n-k结束
        for (int i = 0; i <= n - k; i++) {  // 这里的时间复杂度是 O(n-k+1)
            // 用一个变量保存当前窗口的最大值
            int max = nums[i];
            // i的位置已经遍历过了，所以从i+1开始；结束位置到i+k-1的位置
            for (int j = i + 1; j < i + k ; j++) {  // 这里的时间复杂度是 O(k)
                if(nums[j] > max){
                    max = nums[j];
                }
            }
            result[i] = max;
        }
        return result;
    }

    /**
     * 2、使用大顶堆方式处理
     *    时间复杂度：O(nlog^k)
     *    空间复杂度：O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    private int[] maxSlidingWindow2(int[] nums,int k){
        int n = nums.length;
        // 定义一个窗口，用于输出窗口最大值序列
        // 最后一个窗口的起始位置的索引下标是从n-k ~~ n-1，并且数组下标是从0开始的，所以输出值共有n-k+1个
        int[] result = new int[n-k+1];

        // 定义
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });

        // 初始化大顶堆，将前k个元素放入堆中
        for (int i = 0; i < k; i++) {
            maxHeap.add(nums[i]);
        }

        // 获取堆中的第一个元素，就是当前第一个窗口中的最大值
        result[0] = maxHeap.peek();

        // 遍历窗口的起始元素，删除之前窗口的元素，插入新的元素，保持堆中一直有k个元素
        for (int i = 1; i <= n-k; i++) {
            // 删除堆中上一个元素
            maxHeap.remove(nums[i-1]);
            // 插入新的元素
            maxHeap.add(nums[i+k-1]);
            // 获取当前堆中的最大值
            result[i] = maxHeap.peek();
        }

        return result;
    }

    /**
     * 3、使用双端队列
     *    时间复杂度：O(nlog^k)
     *    空间复杂度：O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    private int[] maxSlidingWindow3(int[] nums,int k){
        int n = nums.length;
        // 定义一个窗口，用于输出窗口最大值序列
        // 最后一个窗口的起始位置的索引下标是从n-k ~~ n-1，并且数组下标是从0开始的，所以输出值共有n-k+1个
        int[] result = new int[n-k+1];

        // 定义双端队列
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // 初始化双向队列
        for (int i = 0; i < k; i++) {
            // 删除之前更小的元素
            while (!deque.isEmpty() && nums[i] > nums[deque.getLast()])
                deque.removeLast();
            // 当前元素入队
            deque.addLast(i);
        }

        // 队首元素就是第一个窗口最大值
        result[0] = nums[deque.getFirst()];

        for (int i = k; i < n; i++) {
            // 首先要删除上一个窗口中的元素，如果刚好是最大值，删除队首元素
            if(!deque.isEmpty() && deque.getFirst() == i-k)
                deque.removeFirst();

            // 依次插入新增元素
            // 删除之前更小的元素
            while (!deque.isEmpty() && nums[i] > nums[deque.getLast()])
                deque.removeLast();
            // 当前元素入队
            deque.addLast(i);

            // 当前队首元素就是窗口最大值
            result[i-k+1] = nums[deque.getFirst()];
        }

        return result;
    }

    /**
     * 4、使用左右扫描
     *    时间复杂度：O(n)
     *    空间复杂度：O(n)
     *
     * @param nums
     * @param k
     * @return
     */
    private int[] maxSlidingWindow(int[] nums,int k) {
        int n = nums.length;
        // 定义一个窗口，用于输出窗口最大值序列
        // 最后一个窗口的起始位置的索引下标是从n-k ~~ n-1，并且数组下标是从0开始的，所以输出值共有n-k+1个
        int[] result = new int[n - k + 1];

        // 定义两个预处理数组
        int[] left = new int[n];
        int[] right = new int[n];

        // 遍历数组，左右扫描，计算left和right的值
        for (int i = 0; i < n; i++) {
            // 1、从左往右扫描
            if(i % k == 0){
                // 块的左侧起始位置
                left[i] = nums[i];
            }else{
                // 如果是后续位置，那么跟之前的最大比较
                left[i] = Math.max(left[i-1],nums[i]);
            }

            // 2、从右往左扫描
            int j = n - i - 1; // 当i=0时，j=n-1，刚好是数组的最后一个索引位置
            // j == n-1 表示原始数组的最后一个索引
            // j % k == k-1 表示原始数组长度%k=0的位置的前一个位置索引
            if (j == n-1 || j % k == k-1) {
                // 块的右侧起始位置
                right[j] = nums[j];
            } else {
                // 如果是前续位置，那么跟之后的最大比较
                right[j] = Math.max(right[j+1],nums[j]);
            }
        }

        // 遍历所有窗口，对起始位置和结束位置取对应的left、right做比较
        for (int i = 0; i < n - k + 1; i++) {
            // 为啥根据窗口取right的开头，left的结尾？
            // right[i] 在每个窗口中的最大值在最左侧
            // left[i+k-1] 在每个窗口中的最大值在最右侧
            result[i] = Math.max(right[i],left[i+k-1]);
        }

        return result;
    }
}
