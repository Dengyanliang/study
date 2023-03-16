package com.deng.study.datastru_algorithm.recursion;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:迷宫问题
 * @Auther: dengyanliang
 * @Date: 2021/2/5 11:26
 */
public class MiGong {

    private int mg[][]; // 定义迷宫的数组
    private int count;  // 路径条数

    private void setMg(int row, int column) {
        mg = new int[row][column];
        System.out.println("---构造上下墙---");
        for (int i = 0; i < column; i++) {  // 控制列
            mg[0][i] = 1;                   // 第一行
            mg[row - 1][i] = 1;             // 最后一行
        }
        show();

        System.out.println("---构造左右墙---");
        for (int i = 0; i < row; i++) { // 控制列
            mg[i][0] = 1;               // 第一列
            mg[i][column - 1] = 1;      // 最后一列
        }
        show();

        System.out.println("---构造障碍物---");
        mg[3][1] = 6;
        mg[3][2] = 6;

        show();
    }

    public void show() {
        for (int i = 0; i < mg.length; i++) {
            for (int j = 0; j < mg[i].length; j++) {
                System.out.print(mg[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void getPath(int xi, int yi, int xe, int ye, List<Box> paths) {
        int di;                     // 记录下一个方向
        int i = 0, j = 0;           // 记录符合条件的方块的坐标
        Box box = new Box(xi, yi);  // 构造当前方块
        paths.add(box);             // 把当前方块放入到路径中
        mg[xi][yi] = -1;            // 当前方块设置为不可走
        if(xi == xe && yi == ye){   // 找到出口
            System.out.print("出口路径" + ++count + "：");
            for (Box pathBox : paths) {
                System.out.print("(" + pathBox.i + "," + pathBox.j + ")");
            }
            System.out.println();
        }else{                      // 没有找到出口
            di = 0;
            while(di < 4){          // 当前方块的相邻四个方向，一般最多只有三个方向可以走，因为有个方向已经走过了
                switch (di){
                    case 0:
                        i = xi-1; j = yi;  // 上
                        break;
                    case 1:
                        i = xi+1; j = yi; // 下
                        break;
                    case 2:
                        i = xi; j = yi-1; // 左
                        break;
                    case 3:
                        i = xi; j = yi+1; // 右
                        break;
                }
                if(mg[i][j] == 0){    // 方块（i,j）可以走时
                    getPath(i, j, xe, ye, paths); // 从（i,j）继续寻找迷宫路径
                }
                di++;                 // 继续处理（xi,yi）的下一个相邻方块
            }
        }
        paths.remove(paths.size()-1); // 删除最后一个方块，重新寻找路径
        mg[xi][yi] = 0;                 // 当前方块设置为可走
    }

    /**
     * 思路：
     * 1、i和j 定位出从哪个位置开始找
     * 2、如果找到mg[xe][ye]这个点表示能够找到
     * 3、map[i][j]为0 表示该点没有找过，为1表示墙，为2表示此路能够走通，为3表示已经找过但是走不通,为6表示障碍物
     * 4、约定：走的路线为下->右—>上—>左
     *
     * @param
     * @param i
     * @param j
     */
    public boolean search(int i, int j, int xe, int ye) {
        if(mg[xe][ye] == 2){
            return true;
        }
        if(mg[i][j] == 0){
            mg[i][j] = 2;
            if (search(i, j + 1, xe, ye)) {        // 右
                return true;
            } else if (search(i, j - 1, xe, ye)) {  // 左
                return true;
            } else if (search(i + 1, j, xe, ye)) {  // 上
                return true;
            } else if (search(i - 1, j, xe, ye)) {  // 下
                return true;
            } else {
                mg[i][j] = 3;
                return false;
            }
        }else{
            return false;
        }
    }

    @Test
    public void testMiGong1(){

        MiGong miGong = new MiGong();
        miGong.setMg(5,5);

        System.out.println("------寻找路径------");
        miGong.search(1, 1, 3, 3);
        miGong.show();
    }

    @Test
    public void testMiGong2(){
        MiGong miGong = new MiGong();
        miGong.setMg(5,5);

        List<Box> paths = new ArrayList<>();
        miGong.getPath(1,1,3,3,paths);
    }
}
