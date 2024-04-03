package com.deng.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/7 18:19
 */
@Getter
@AllArgsConstructor
public enum PayStatusEnum {

    INIT(0, "初始化"),
    PROCESSING(1,"处理中"),
    PAY_SUCCESS(2, "待发货"),
    PAY_FAIL(3, "待发货"),
    CLOSED(4, "已关闭"),

    ;

    private int code;
    private String desc;

}
