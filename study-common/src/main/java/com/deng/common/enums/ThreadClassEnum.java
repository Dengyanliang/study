package com.deng.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-12-10 16:34:04
 */
@Getter
@AllArgsConstructor
public enum ThreadClassEnum {
    PRE("先序线索二叉树"),
    MIDDLE("中序线索二叉树"),
    POST("后序线索二叉树");

    private String desc;

}
