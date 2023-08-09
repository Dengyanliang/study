package com.deng.study.datastru_algorithm.graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Desc: 腐烂的橘子
 * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
 *      值 0 代表空单元格；
 *      值 1 代表新鲜橘子；
 *      值 2 代表腐烂的橘子。
 * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
 * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
 *
 * @Auther: dengyanliang
 * @Date: 2023/7/8 21:55
 */
public class OrangesRotting {

    /**
     * {2,1,1},
     * {1,1,0},
     * {0,1,1}
     */
    private static final int[][] MY_ARRAY = new int[][]{
            {2,1,1},
            {0,1,1},
            {1,0,1}
    };

    public static void main(String[] args) {
        int round = orangesRotting(MY_ARRAY);
        System.out.println(round);
    }

    /**
     * 1、计算出所有新鲜的橘子(值为1)。并找出所有腐烂的橘子(值为2)，将它们放入队列，作为第0层的结点
     * 2、进行BFS遍历，上下左右四个方向进行处理
     * 3、每次遍历到一个橘子，就将新鲜橘子的数量减1。
     * 4、遍历结束后，如果新鲜橘子的数量还大于0，就说明存在无法污染的橘子
     *
     * @param grid
     * @return
     */
    public static int orangesRotting(int[][] grid){
        int rowLength = grid.length;
        int colLength = grid[0].length;

        // 存放腐烂橘子的队列
        Queue<int[]> queue = new LinkedList<>(); // 数组长度是2，第一个值是行，第二个值是列
        int count = 0; // 新鲜橘子的数量

        for (int r = 0; r < rowLength; r++) {
            for (int c = 0; c < colLength; c++) {
                if(grid[r][c] == 1){
                    count++;
                }
                if(grid[r][c] == 2){
                    queue.add(new int[]{r,c});
                }
            }
        }

        int round = 0; // 表示腐烂的分钟数，获取腐烂的轮数
        while(count > 0 && !queue.isEmpty()){
            round++;
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                int[] orange = queue.poll();
                int r = orange[0];
                int c = orange[1];

                if(r-1 >= 0 && grid[r-1][c] == 1){
                    grid[r-1][c] = 2;
                    count--;
                    queue.add(new int[]{r-1,c});
                }
                if(r+1<rowLength && grid[r+1][c] == 1){
                    grid[r+1][c] = 2;
                    count--;
                    queue.add(new int[]{r+1,c});
                }
                if(c-1 >= 0 && grid[r][c-1] == 1){
                    grid[r][c-1] = 2;
                    count--;
                    queue.add(new int[]{r,c-1});
                }
                if(c+1 < colLength && grid[r][c+1] == 1){
                    grid[r][c+1] = 2;
                    count--;
                    queue.add(new int[]{r,c+1});
                }
            }
        }
        if(count > 0){
            return -1;
        }else{
            return round;
        }
    }

}
