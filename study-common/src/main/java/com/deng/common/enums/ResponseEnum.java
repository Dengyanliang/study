package com.deng.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/7 18:19
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(10000, "成功"),


    ;

    private int code;

    private String msg;

}
