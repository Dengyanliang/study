package com.deng.study.datastru_algorithm.tree;

import lombok.Data;


/**
 * @Desc: 哈夫曼结点
 * @Author: dengyanliang
 * @Date: 2022-12-03 19:59:13
 */
@Data
public class HuffmanNode<T> {

    T data;                // 结点的值
    double weight;         // 结点权值
    boolean flag;          // 标记是左孩子还是右孩子
    HuffmanNode<T> parent; // 父节点
    HuffmanNode<T> lchild; // 左孩子
    HuffmanNode<T> rchild; // 右孩子
}
