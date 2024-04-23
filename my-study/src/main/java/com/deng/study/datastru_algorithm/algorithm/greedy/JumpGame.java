package com.deng.study.datastru_algorithm.algorithm.greedy;

import org.junit.jupiter.api.Test;

/**
 * @Desc: 跳跃游戏--贪心算法实现
 * @Auther: dengyanliang
 * @Date: 2023/3/19 19:49
 */
public class JumpGame {

    /**
     * 是否可以跳跃到
     */
    @Test
    public void testCanJum(){
        int[] num1 = {2, 3, 1, 1, 4};
        int[] num2 = {3, 2, 1, 0, 4};
        int[] num3 = {2, 3, 1, 0, 4};

        System.out.println(canJump(num1));
        System.out.println(canJump(num2));
        System.out.println(canJump(num3));
    }

    private static boolean canJump(int[] nums) {
        int n = nums.length;
        // 当前能够跳跃的最远距离
        int farthest = 0;
        for (int i = 0; i < n; i++) {
            // 判断当前i是否在farthest可达范围内
            if(i <= farthest){
                // 如果能够跳的更远，就更新
                farthest = Math.max(farthest,i+nums[i]);
                // 如果farthest已经大于等于n，表示可以跳到最后
                if(farthest >= n){
                    return true;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    /**
     * 前提是肯定能够跳跃到最后
     * 这里求解的是看几步能够调到最后
     */
    @Test
    public void testReverseJump(){
        int[] num1 = {2, 3, 1, 1, 4, 7};
        int[] num3 = {2, 3, 1, 0, 4, 7};

        System.out.println(reverseJump(num1));
        System.out.println(reverseJump(num3));
    }

    /**
     * 从后往前跳跃
     * @param nums
     * @return
     */
    private int reverseJump(int[] nums){
        // 需要跳跃的步骤
        int step = 0;
        // 指向当前跳到的位置
        int currPosition = nums.length - 1;
        int count = 0;
        while (currPosition > 0) {
            count++;
            for (int i = 0; i <= currPosition; i++) {
                System.out.println("count = " + count + "，i + nums[i] = " + (i + nums[i]) + "，currPosition = " + currPosition);
                if (i + nums[i] >= currPosition) {
                    currPosition = i;
                    step++;
                    break;
                }
            }
        }
        return step;
    }

    /**
     * 正向跳跃，
     */
    @Test
    public void testJump1(){
        int[] num1 = {2, 3, 1, 1, 4, 8};
        int[] num2 = {2, 3, 1, 0, 4, 8};
        int[] num3 = {2};

        System.out.println(jump1(num1));
        System.out.println(jump1(num2));
        System.out.println(jump1(num3));
    }

//    private int jump1(int[] nums){
//        int steps = 0;
//        int currPosition = 0;
//        int farthest = 0;
//        int nextFarthest = farthest;
//        while () {
//
//        }
//
//    }




    private int jump1(int[] nums){
        int steps = 0;

        int currPosition = 0;

        // 定义双指针，指向当前一步能跳到的最远位置，和下一步能跳到的最远位置
        int farthest = 0;
        int nextFarthest = farthest; // 初始就是farthest，肯定比第一步的最远位置要远
        int count = 0;

        // 不停的寻找下一步位置，
        while (farthest < nums.length - 1) { // 这里小于，不是等于，是因为当数组只有1个元素的时候，不需要跳跃。
            count++;
            // 遍历从当前位置到第一步最远位置的所有索引，不停的判断第二步的最远位置
            for (int i = currPosition; i <= farthest ; i++) {
                System.out.println("count = " + count + "，currPosition = " + currPosition + "，i + nums[i] = " + (i + nums[i]) + "，nextFarthest=" + nextFarthest);
                if(i + nums[i] > nextFarthest){
                    // 更新第二步的最远位置
                    nextFarthest = i + nums[i];
                    currPosition = i;   // 当前一步最优选择就是i
                }
            }
            // 循环结束，得到的currPosition就是第一步最优的选择
            steps++;
            farthest = nextFarthest;
        }

        return steps;
    }

    @Test
    public void testJump2(){
        int[] num1 = {2, 3, 1, 1, 4, 8};
        int[] num2 = {2, 3, 1, 0, 4, 8};
        int[] num3 = {2};

        System.out.println(jump2(num1));
        System.out.println(jump2(num2));
        System.out.println(jump2(num3));
    }

    private int jump2(int[] nums){
        int steps = 0;

        // 定义双指针，指向当前一步能跳到的最远位置，和下一步能跳到的最远位置
        int farthest = 0;
        int nextFarthest = farthest;

        // TODO 这里还是不太明白为啥需要减1
        for (int i = 0; i < nums.length - 1; i++) {
            if (i + nums[i] > nextFarthest) {
                // 更新第二步的最远位置
                nextFarthest = i + nums[i];
            }
            // 这里确定了起点，就会对step加1
            if (i == farthest) {
                steps++;
                farthest = nextFarthest;
            }
        }

        return steps;
    }

}
