package com.deng.study.algorithm.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc: 将层和节点绑定起来
 * @Author: dengyanliang
 * @Date: 2022-11-27 20:16:43
 */
@Data
public class QNode<T> {
    int level;      // 第几层
    BTNode<T> node; // 当前节点

    public QNode(int level, BTNode<T> node) {
        this.level = level;
        this.node = node;
    }
}
