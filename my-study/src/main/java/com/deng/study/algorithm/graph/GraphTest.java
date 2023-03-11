package com.deng.study.algorithm.graph;

import org.junit.Test;

/**
 * @Desc: 图数据结构测试
 * @Auther: dengyanliang
 * @Date: 2021/2/9 14:29
 */
public class GraphTest {

    /**    F
     *    /
     *   C     B ---- E
     *    \   /  \
     *     \ /    D
     *      A
     * @param args
     */
    public static void main(String[] args) {
        String[] vertexValue = {"A","B","C","D","E","F"};
        int n = vertexValue.length;  // 顶点个数
        Graph graph = new Graph(n);

        // 添加顶点
        for(String vertex : vertexValue){
            graph.addVertex(vertex);
        }

        // 添加边 A-B A-C B-D B-E C-F
        graph.addEdge(0,1,1); // A-B
        graph.addEdge(0,2,1); // A-C
        graph.addEdge(1,3,1); // B-D
        graph.addEdge(1,4,1); // B-E
        graph.addEdge(2,5,1); // C-F

        // 显示
        graph.show();

        System.out.println("深度优先遍历：");
        graph.dfs();
        System.out.println();
        System.out.println("广度优先遍历：");
        graph.bfs();
    }


    @Test
    public void testMatGraph() {
        MatGraph matGraph = new MatGraph();
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {5, 6, 8}};
        matGraph.createMatGraph(array, 3, 3);
        matGraph.displayMatGraph();
    }

    @Test
    public void testAdjGraph() {
        AdjGraph adjGraph = new AdjGraph();
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {5, 6, 8}};
        adjGraph.createAdjGraph(array, 3, 3);
        adjGraph.displayAdjGraph();
    }
}
