package com.deng.study.algorithm.graph;

import lombok.Data;

/**
 * @Desc: 图--头结点类
 * @Author: dengyanliang
 * @Date: 2022-12-10 20:41:47
 */
@Data
public class VNode {
    String[] data;  // 顶点信息
    ArcNode firstArc; // 指向第一条边的邻接顶点
}
