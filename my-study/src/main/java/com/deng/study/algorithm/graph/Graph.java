package com.deng.study.algorithm.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Desc: 深度优先和广度优先，这里使用邻接矩阵遍历
 * @Auther: dengyanliang
 * @Date: 2021/2/9 14:32
 */
public class Graph {

    private List<String> vertexList; // 顶点集合
    private int[][] edges;    // 图对应的邻接矩阵
    private int numOfEdges;   // 表示边的数目
    private boolean[] isVisited; // 是否被访问过


    public Graph(int n){
        vertexList = new ArrayList<>(n);
        edges = new int[n][n];
    }

    /**
     * 返回顶点的个数
     * @return
     */
    public int getNumOfEdges(){
        return numOfEdges;
    }

    /**
     * 返回边的数目
     * @return
     */
    public int getNumOfVertex(){
        return vertexList.size();
    }

    /**
     * 返回结点i(下标)对应的数据 0->'A' 1->'B' 2->'C'
     * @param i
     * @return
     */
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    /**
     * 返回v1到v2的权值
     * @param v1 顶点
     * @param v2 顶点
     * @return
     */
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    /**
     * 添加顶点
     * @param vertex
     */
    public void addVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 第一个顶点的下标
     * @param v2 第二个顶点的下标
     * @param weight 边的权重，表示v1到v2的权值
     */
    public void addEdge(int v1,int v2,int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    public void show(){
        System.out.print(" ");
        for(String vertex : vertexList){
            System.out.print(" " + vertex);
        }
        System.out.println();

        for(int i = 0; i < edges.length; i++){
            System.out.print(vertexList.get(i)+" ");
            for(int j = 0; j < edges[i].length; j++){
                System.out.print(edges[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 得到第一个邻接节点的下标w
     * @param index
     * @return
     */
    public int getFirstNeighbor(int index){
        for(int i = 0; i < vertexList.size(); i++){
            if(edges[index][i] > 0){ // 遍历index行中值大于0的元素
                return i;            // 输出列的值
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标获取得到下一个节点邻接节点
     * @param v1
     * @param v2
     * @return
     */
    public int getNextNeighbor(int v1,int v2){
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if(edges[v1][i] > 0){ // 从V1行继续遍历
                return i;
            }
        }
        return -1;
    }

    /**
     * 深度优先遍历
     */
    public void dfs(){
        isVisited = new boolean[vertexList.size()];
        for(int i = 0; i < getNumOfVertex(); i++){
            if(!isVisited[i]){  //当前节点是否被访问过
                dfs(isVisited,i);
            }
        }
    }

    /**
     * 深度优先遍历
     * @param isVisited 是否被访问
     * @param i
     */
    private void dfs(boolean[] isVisited,int i){
        // 首先我们访问该节点，输出
        System.out.print(getValueByIndex(i) + "-->");
        // 把该节点置为已访问
        isVisited[i] = true;

        // 查找节点i的第一个邻接节点
        int w = getFirstNeighbor(i);
        while(w != -1){ // 说明存在
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }

            // 如果节点已经被访问过，则取w的下一个邻接节点
            w = getNextNeighbor(i,w);
        }
    }


    /**
     * 广度优先遍历
     */
    public void bfs(){
        isVisited = new boolean[vertexList.size()];
        for(int i = 0; i < getNumOfVertex(); i++){
            if(!isVisited[i]){  //当前节点是否被访问过
                bfs(isVisited, i);
            }
        }
    }

    /**
     * 广度优先遍历
     */
    public void bfs(boolean[] isVisited,int i){
        int u; // 表示队列的头节点对应下标
        int w; // 表示邻接节点
        // 队列，记录节点访问的顺序
        LinkedList queue = new LinkedList();
        // 输出节点信息
        System.out.print(getValueByIndex(i) + "-->");
        // 将节点置为已访问
        isVisited[i] = true;
        // 将节点加入队列
        queue.addLast(i);
        while(!queue.isEmpty()){
            // 取出队列的头节点下标
            u = (Integer)queue.removeFirst();
            // 得到第一个邻接节点的下标w
            w = getFirstNeighbor(u);
            // 是否被访问过
            while(w != -1){
                if(!isVisited[w]){
                    System.out.print(getValueByIndex(w) + "-->");
                    // 将节点置为已访问
                    isVisited[w] = true;
                    // 将节点加入队列
                    queue.addLast(i);
                }
                //以u为前驱节点，找w后面的下一个邻接节点
                w = getNextNeighbor(u,w);
            }
        }

    }
}
