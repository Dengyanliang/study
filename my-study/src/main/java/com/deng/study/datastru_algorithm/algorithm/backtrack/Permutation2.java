package com.deng.study.datastru_algorithm.algorithm.backtrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @Desc: 数字全排列
 * @Auther: dengyanliang
 * @Date: 2023/5/18 19:56
 */
public class Permutation2 {

    public static void main(String[] args) {
        String[] nums = {"A","B","C"};
        Permutation2 permutation = new Permutation2();
        List<List<String>> result = permutation.permute1(nums);

        for (List<String> solution : result) {
            for (String num : solution) {
                System.out.print(num + "\t");
            }
            System.out.println();
        }

    }

    /**
     * 时间复杂度：O(N*N!)
     * 空间复杂度：O(N)
     * @param nums
     * @return
     */
    private List<List<String>> permute1(String[] nums){
        // 保存结果的列表
        List<List<String>> result = new ArrayList<>();

        // 定义一个可行的排列
        List<String> solution = new ArrayList<>();

        // 定义一个辅助变量，保存已经填入的数
        HashSet<String> filledNumbers = new HashSet<>();

        // 用一个递归方法按照数组位置依次处理
        backtrack1(nums,result,solution,0,filledNumbers);

        return result;
    }

    /**
     *
     * 递归方法，考察当前的第i个位置，选没有填过的数据填入
     * @param nums 输入数组
     * @param result 返回结果列表
     * @param solution  可行的排列
     * @param i 数组开始的位置
     * @param filledNumbers  保存已经填入的数
     */
    private void backtrack1(String[] nums, List<List<String>> result, List<String> solution, int i,HashSet<String> filledNumbers) {
        int n = nums.length;
        // 基准情况，索引位置填到n就结束
        if(i >= n){
            result.add(new ArrayList<>(solution));
        }else{
            // 没填充的话，对当前i位置选择下一个可选的数
            for (int j = 0; j < n; j++) {
                if(!filledNumbers.contains(nums[j])){
                    // 1、如果没有填充过，就填入当前的数
                    solution.add(nums[j]);
                    filledNumbers.add(nums[j]);
                    System.out.println(solution + "，" + filledNumbers);

                    // 2、递归调用，继续填充后面的位置
                    backtrack1(nums,result,solution,i+1,filledNumbers);

                    // 3、回溯
                    System.out.println("回溯：" + solution+"，filledNumbers="+filledNumbers + "，i="+i);
                    solution.remove(i); // 如果remove传入的是整型，则删除对应下标的元素。
                    filledNumbers.remove(nums[j]);
                }
            }
        }
    }

    /**
     * 时间复杂度：O(N*N!)
     * 空间复杂度：O(N)
     * 代码实现更简洁
     * @param nums
     * @return
     */
    private List<List<Integer>> permute(int[] nums){
        // 保存结果的列表
        List<List<Integer>> result = new ArrayList<>();

        // 定义一个可行的排列
        List<Integer> solution = new ArrayList<>();

        for (int num : nums) {
            solution.add(num);
        }

        // 用一个递归方法按照数组位置依次处理
        backtrack(result,solution,0);

        return result;
    }

    /**
     *
     * 递归方法，考察当前的第i个位置，选没有填过的数据填入
     * @param result 返回结果列表
     * @param solution  可行的排列
     * @param i 数组开始的位置
     */
    private void backtrack(List<List<Integer>> result, List<Integer> solution, int i) {
        int n = solution.size();
        // 基准情况，索引位置填到n就结束
        if(i >= n){
            result.add(new ArrayList<>(solution));
        }else{
            // 没填充的话，对当前i位置选择下一个可选的数
            for (int j = i; j < n; j++) {
                // 1、如果没有填充过，就填入当前的数
                Collections.swap(solution,i,j);

                // 2、递归调用，继续填充后面的位置
                backtrack(result, solution, i + 1);

                // 3、回溯，考虑其他位置
                Collections.swap(solution,i,j);
            }
        }
    }
}
