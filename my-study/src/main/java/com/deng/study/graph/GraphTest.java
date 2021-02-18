package com.deng.study.graph;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/2/9 14:29
 */
public class GraphTest {

    /**
     *
     *   C     B ----E
     *    \   /  \
     *     \ /    D
     *      A
     * @param args
     */
    public static void main(String[] args) {
        int n = 5; // 顶点个数
        String[] vertexValue = {"A","B","C","D","E"};
        Graph graph = new Graph(n);

        // 添加顶点
        for(String vertex : vertexValue){
            graph.addVertex(vertex);
        }

        // 添加边
        // A-B A-C B-C B-D B-E
        graph.addEdge(0,1,1); // A-B
        graph.addEdge(0,2,1); // A-C
//        graph.addEdge(1,2,1); // B-C
        graph.addEdge(1,3,1); // B-D
        graph.addEdge(1,4,1); // B-E

        // 显示
        graph.show();

        System.out.println("深度优先遍历：");
        graph.dfs();
        System.out.println();
        System.out.println("广度优先遍历：");
        graph.bfs();
    }


}
