package com.deng.study.algorithm.tree;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Desc: 将层和节点绑定起来
 * @Author: dengyanliang
 * @Date: 2022-11-27 20:16:43
 */
@Data
public class QNode<T> {
    int level;         // 第几层
    BTNode<T> node;    // 当前结点
    QNode<T> parent;   // 当前结点的父结点

    public QNode(int level, BTNode<T> node) {
        this.level = level;
        this.node = node;
    }

    public QNode(BTNode<T> node,QNode<T> parent) {
        this.node = node;
        this.parent = parent;
    }
}
