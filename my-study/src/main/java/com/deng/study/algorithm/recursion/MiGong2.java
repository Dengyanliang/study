package com.deng.study.algorithm.recursion;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-08-23 12:40:42
 */
public class MiGong2 {

    private static int[][] mg; // 定义一个数组

    private static void createArray(int row,int column){
        mg = new int[row][column];

        System.out.println("------构造上下------");
        // 第一行和最后一行置位1
        for(int i = 0; i < column; i++){
            mg[0][i] = 1;
            mg[row-1][i] = 1;
        }

        System.out.println("------构造左右------");
        for(int j = 0; j < row; j++){
            mg[j][0] = 1;
            mg[j][column-1] = 1;
        }
    }


    /**
     * 求解路径为(xi,yi)->(xe,ye);
     * @param xi
     * @param yi
     * @param xe
     * @param ye
     * @param path
     */
    public static void mgpath(int xi,int yi, int xe,int ye,PathType path){
        int di,k,i = 0,j = 0;
        if(xi==xe && yi == ye){ // 找到出口了
            path.data[path.length].i = xi;
            path.data[path.length].j = yi;
            path.length++;
            System.out.println("迷宫路径如下：\n");
            for (k = 0; k < path.length; k++) {
                System.out.println("" + path.data[k].i + ", " + path.data[k].j);
                if((k+1) % 5 == 0)
                    System.out.println();
            }
            System.out.println();
        }else{              // 没找到出口
            if(mg[xi][yi] == 0){
                di = 0;
                while (di < 4){
                    switch (di){
                        case 0:
                            i = xi-1;j = yi; // 上
                            break;
                        case 1:
                            i = xi; j = yi + 1; // 下
                            break;
                        case 2:
                            i = xi+1;j = yi; // 右
                            break;
                        case 3:
                            i = xi; j = yi -1; // 左
                            break;
                    }
                    path.data[path.length].i = xi;
                    path.data[path.length].j = yi;
                    path.length++;
                    mg[xi][yi] = -1;
                    mgpath(i,j,xe,ye,path);
                    path.length--;
                    mg[xi][yi] = 0;
                    di++;
                }
            }
        }
    }

    public static void main(String[] args) {
        PathType path = new PathType();
        path.length = 0;
        createArray(7,8);

        mgpath(1,1,4,4,path);
    }
}
