package com.deng.study.datastru_algorithm.algorithm;

import lombok.Data;
import lombok.val;

/**
 * @Desc: 动态规划算法
 * @Auther: dengyanliang
 * @Date: 2023/3/1 15:42
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        Product[] pros = {
                new Product(1,1500),
                new Product(4,3000),
                new Product(3,2000),
        };
//        int[] w = {1,4,3}; // 物品的重量
//        int[] v = {1500,3000,2000}; // 物品的价值 v[i]
        int n = pros.length; // 物品的个数
        int m = 4; // 背包的容量

        // 创建二维数组
        // v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] arr = new int[n+1][m+1];

        // 为了记录放入商品的情况，我们定一个二维数组
        int[][] path = new int[n+1][m+1];


        // 初始化
//        for (int i = 0; i < v.length; i++) {
//            v[i][0] = 0;
//        }

        for (int i = 1; i < arr.length; i++) { // 不处理第一行
            for (int j = 1; j < arr[0].length; j++) { // 不处理第一列
                if(pros[i-1].weigth > j){
                    arr[i][j] = arr[i-1][j];
                }else{
//                    arr[i][j] = Math.max(arr[i-1][j],v[i-1] + arr[i-1][j-w[i-1]]);
                    if(arr[i-1][j] < pros[i-1].cost + arr[i-1][j-pros[i-1].weigth]){
                        arr[i][j] = pros[i-1].cost + arr[i-1][j-pros[i-1].weigth];
                        path[i][j] = 1; 
                    }else{
                        arr[i][j] = arr[i-1][j];
                    }
                }
            }
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]+ " ");
            }
            System.out.println();
        }

        System.out.println("======path=======");
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[i].length; j++) {
                System.out.print(path[i][j]+ " ");
            }
            System.out.println();
        }

        // 输出最后放入的是哪些商品
        // 通过path，这样输出会把所有的放入情况都得到，其实只需要得到最后的放入
//        for (int i = 0; i < path.length; i++) {
//            for (int j = 0; j < path[i].length; j++) {
//                if(path[i][j]== 1){
//                    System.out.println("第"+i+"个商品放入到背包");
//                }
//            }
//        }

        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) { // 从path最后开始找
                System.out.println("第" + i + "个商品放入到背包");
                j = j - pros[i-1].weigth;
            }
            i--;
        }
    }
}

@Data
class Product{
    int weigth; // 重量
    int cost;   // 价值

    public Product(int weigth, int cost) {
        this.weigth = weigth;
        this.cost = cost;
    }
}

