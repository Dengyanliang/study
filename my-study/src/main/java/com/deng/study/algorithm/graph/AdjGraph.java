package com.deng.study.algorithm.graph;

import java.util.Objects;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-12-10 20:42:55
 */
public class AdjGraph {
    final int MAX = 100;
    VNode[] nodes;  // 邻接头数组
    int n;          // 顶点数
    int e;          // 边数

    public AdjGraph() {
        nodes = new VNode[MAX];
        for (int i = 0; i < MAX; i++) {
            nodes[i] = new VNode();
        }
    }

    public void createAdjGraph(int[][] array, int n, int e){
        this.n = n;
        this.e = e;
        ArcNode tempNode;
        for (int i = 0; i < n; i++) {
            nodes[i].firstArc = null;  // 可以不要
        }
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                if(array[i][j] !=0){
                    tempNode = new ArcNode();
                    tempNode.adjvex = j;
                    tempNode.weight = array[i][j];
                    tempNode.nextVal = nodes[i].firstArc; // 使用头插法插入新结点
                    nodes[i].firstArc = tempNode;
                }
            }
        }
    }

    public void displayAdjGraph() {
        ArcNode p;
        for (int i = 0; i < n; i++) {
            System.out.print(i);
            p = nodes[i].firstArc;
            while (Objects.nonNull(p)) {
                System.out.print("-->(" + p.adjvex + "," + p.weight + ")");
                p = p.nextVal;
            }
            System.out.println("-->^");
        }
    }


}
