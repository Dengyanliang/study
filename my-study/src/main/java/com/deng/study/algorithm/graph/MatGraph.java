package com.deng.study.algorithm.graph;

/**
 * @Desc: 图的邻接矩阵类
 * @Author: dengyanliang
 * @Date: 2022-12-10 20:03:35
 */
public class MatGraph {
    final int MAX = 100;    // 最多顶点个数
    int[][] edges;  // 邻接矩阵数组这里假设为int类型
    int n;          // 顶点数
    int e;          // 边数
    String[] vexs;  // 存放顶点信息

    public MatGraph() {
        this.edges = new int[MAX][MAX];
        this.vexs = new String[MAX];
    }

    /**
     * 建立图的邻接矩阵
     * @param array 传入的数组
     * @param n     顶点数
     * @param e     边数
     */
    public void createMatGraph(int[][] array,int n,int e){
        this.n = n;
        this.e = e;
        for (int i = 0; i < n; i++) {
            edges[i] = new int[n];
            for (int j = 0; j < n; j++) {
                edges[i][j] = array[i][j];
            }
        }
    }

    public void displayMatGraph(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(edges[i][j] + " ");
            }
            System.out.println();
        }
    }

}
