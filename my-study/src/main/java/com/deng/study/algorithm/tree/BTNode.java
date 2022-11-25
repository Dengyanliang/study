package com.deng.study.algorithm.tree;

import lombok.Data;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-11-20 18:25:09
 */
@Data
public class BTNode<T> {
    T data;
    BTNode<T> lchild;
    BTNode<T> rchild;
}
