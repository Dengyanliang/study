package com.deng.study.datastru_algorithm.graph;

import lombok.Data;

/**
 * @Desc: 图--边结点类
 * @Author: dengyanliang
 * @Date: 2022-12-10 20:40:10
 */
@Data
public class ArcNode {
    int adjvex;      // 该边的顶点编号
    ArcNode nextVal; // 指向下一条边的指针
    int weight;      // 权值
}
