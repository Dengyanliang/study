package com.deng.study.algorithm.graph;

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


    private static int find(int[][] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == 1) {
                    System.out.println("当前坐标：" + i + "," + j);
                    doFind(array, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private static void doFind(int[][] array, int i, int j) {
        if (i < 0 || i >= array.length || j < 0 || j >= array[i].length || array[i][j] != 1) {
            return;
        } else {
            System.out.println("   走的路线：" + i + "," + j);
            array[i][j] = 2;        // 走过的标记成2，然后向四周走
            doFind(array, i, j + 1);
            doFind(array, i, j - 1);
            doFind(array, i + 1, j);
            doFind(array, i - 1, j);
        }
    }
}
