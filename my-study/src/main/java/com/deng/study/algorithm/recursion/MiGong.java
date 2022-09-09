package com.deng.study.algorithm.recursion;

/**
 * @Desc:迷宫问题
 * @Auther: dengyanliang
 * @Date: 2021/2/5 11:26
 */
public class MiGong {

    /**
     * 思路：
     * 1、map 表示地图，i和j 定位出从哪个位置开始找
     * 2、如果找到map[6][5]这个点表示能够找到
     * 3、map[i][j]为0 表示该点没有找过，为1表示墙，为2表示此路能够走通，为3表示已经找过但是走不通
     * 4、约定：走的路线为下->右—>上—>左
     *
     * @param map 地图
     * @param i
     * @param j
     */
    public static boolean search(int[][] map,int i, int j){
        if(map[6][5] == 2){
            return true;
        }
        if(map[i][j] == 0){  // 表示该点没有找过
            map[i][j] = 2;   // 假定该点可以走通
            if(search(map,i+1,j)) { // 向下
                return true;
            }else if(search(map,i,j+1)) { // 向右
                return true;
            }else if(search(map,i-1,j)){ // 向上
                return true;
            }else if(search(map,i-1,j-1)){ // 向左
                return true;
            }else{
                map[i][j] = 3;  // 表示死路，走不通
                return false;
            }
        }else{ // 如果map[i][j] != 0 可能是1，2，3
            return false;
        }
    }

    public static void show(int[][] array){
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[i].length; j++){
                System.out.print(array[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 先创建二维数组
        int[][] array = new int[8][7];

        System.out.println("------构造上下------");
        // 第一行和最后一行置位1
        for(int i = 0; i < 7; i++){
            array[0][i] = 1;
            array[7][i] = 1;
        }
        show(array);

        System.out.println("------构造左右------");
        for(int j = 0; j < 8; j++){
            array[j][0] = 1;
            array[j][6] = 1;
        }
        show(array);

        System.out.println("------构造墙------");
        array[3][1] = 1;
        array[3][2] = 1;

        show(array);

        System.out.println("------寻找路径------");
        search(array,1,1);
        show(array);
    }


}
