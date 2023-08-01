package com.deng.study.datastru_algorithm.graph;

/**
 * @Desc: 孤岛问题
 * 11000
 * 01001
 * 00100
 * 00010
 * 求上面的孤岛的个数（0代表水，1代表陆地，当1上下左右全为0时，1成为孤岛，
 * 如果几个1连在一起，四周全是0，也是孤岛，上面的数字的边界默认为0）
 * @Auther: dengyanliang
 * @Date: 2023/1/31 16:22
 */
public class GuDao {

    private static final int[][] MY_ARRAY = new int[][]{
            {1, 1, 0, 0, 0},
            {0, 1, 0, 0, 1},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0}
    };

    public static void main(String[] args) {
        int count = find(MY_ARRAY);
        System.out.println("孤岛个数：" + count);
    }


    private static int find(int[][] grid) {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 1) {
                    System.out.println("当前坐标：" + r + "," + c);
                    doFind(grid, r, c);
                    count++;
                }
            }
        }
        return count;
    }

    private static void doFind(int[][] grid, int r, int c) {
        if(!isArea(grid,r,c)){
            return;
        }

        if(grid[r][c] != 1){ // 表示已经走过或者是水域，无法走
            return;
        }

//        System.out.println("   走的路线：" + r + "," + c);

        grid[r][c] = 2;        // 走过的标记成2，然后向四周走
        doFind(grid, r, c + 1);
        doFind(grid, r, c - 1);
        doFind(grid, r + 1, c);
        doFind(grid, r - 1, c);
    }

    /**
     * 判断当前坐标是否在网格中的通用写法
     *
     * @param grid 网格
     * @param r 横坐标
     * @param c 纵坐标
     * @return
     */
    private static boolean isArea(int[][] grid, int r, int c){
        return  r >= 0 && r < grid.length &&
                c >= 0 && c < grid[0].length;
    }
}
