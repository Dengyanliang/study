package com.deng.study.datastru_algorithm.algorithm.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @Desc: 八皇后问题
 * @Auther: dengyanliang
 * @Date: 2023/5/16 10:25
 */
public class EightQueues {

    public static void main(String[] args) {
        EightQueues eightQueues = new EightQueues();
        List<int[]> result_baoli = eightQueues.eightQueue_baoli();
        System.out.println("暴力求解，八皇后问题共有：" + result_baoli.size() + "个解");

        List<int[]> result = eightQueues.eightQueue();
        System.out.println("八皇后问题共有：" + result.size() + "个解");

        for (int[] solution : result) {
            printSolution(solution);
            System.out.println("-----------------------------");
        }
    }

    private static void printSolution(int[] solution){
        int n = solution.length;
        for (int i = 0; i < n; i++) {
            String[] line = new String[n];
            Arrays.fill(line,"□");
            line[solution[i]] = "Q";
            for (String s : line) {
                System.out.print(s +"\t");
            }
            System.out.println();
        }

    }

    private List<int[]> eightQueue_baoli(){
        List<int[]> result = new ArrayList<>();
        int[] solution = new int[8];
        for (solution[0] = 0; solution[0] < 8; solution[0]++) {
            for (solution[1] = 0; solution[1] < 8; solution[1]++) {
                for (solution[2] = 0; solution[2] < 8; solution[2]++) {
                    for (solution[3] = 0; solution[3] < 8; solution[3]++) {
                        for (solution[4] = 0; solution[4] < 8; solution[4]++) {
                            for (solution[5] = 0; solution[5] < 8; solution[5]++) {
                                for (solution[6] = 0; solution[6] < 8; solution[6]++) {
                                    for (solution[7] = 0; solution[7] < 8; solution[7]++) {
                                        if(check(solution)){
                                            result.add(Arrays.copyOf(solution,8));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean check(int[] solution){
        for (int i = 0; i < 7; i++) { // 这里<7的原因是，在斜线上判断，外层循环solution[6]就是最后一个，内层循环才有可能达到外层循环solution[7]
            for (int j = i+1; j < 8; j++) {
                if(solution[i] == solution[j] || Math.abs(solution[i]-solution[j]) == j-i){
                    return false;
                }
            }
        }
        return true;
    }

    private List<int[]> eightQueue(){
        List<int[]> result = new ArrayList<>();

        // 定义三个HashSet，检测是否冲突
        HashSet<Integer> cols = new HashSet<>();    // 记录某一列上是否出现皇后
        HashSet<Integer> diags1 = new HashSet<>();  // 记录某一左上--右下方向的斜线上，是否出现过皇后
        HashSet<Integer> diags2 = new HashSet<>();  // 记录某一右上--左下方向的斜线上，是否出现过皇后

        int[] solution = new int[8];
        Arrays.fill(solution,-1);  // 初始填充-1，表示当前行没有填入皇后

        backtrack(result,solution,0,cols,diags1,diags2);
        return result;
    }

    /**
     * 用回溯算法递归求解过程
     *
     * @param result 存放结果的集合
     * @param solution 八皇后放入的位置
     * @param row 开始行
     */
    private void backtrack(List<int[]> result, int[] solution, int row,HashSet<Integer> cols,HashSet<Integer> diags1,HashSet<Integer> diags2) {
        // 基准情况
        if (row >= 8) {
            // 一直没有冲突的话，就找到了一组解
            int[] tempResult = Arrays.copyOf(solution, 8);
            result.add(tempResult);
        } else {
            // 对于当前行，遍历每一列的位置，看是否可以放置皇后
            for (int col = 0; col < 8; col++) {
                // 1、判断是否有冲突
                if(cols.contains(col))
                    continue;
                int diag1 = row - col;
                if(diags1.contains(diag1))
                    continue;
                int diag2 = row + col;
                if(diags2.contains(diag2))
                    continue;

                // 2、没有冲突，可以放置皇后
                solution[row] = col;

                // 更新set中的信息
                cols.add(col);
                diags1.add(diag1);
                diags2.add(diag2);

                // 3、当前行处理完毕，继续递归下一行
                backtrack(result,solution,row+1,cols,diags1,diags2);

                // 4、回溯
                solution[row] = -1;
                cols.remove(col);
                diags1.remove(diag1);
                diags2.remove(diag2);
            }
        }

    }
}
