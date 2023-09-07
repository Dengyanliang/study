package com.deng.common.enums;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/7 18:19
 */
public enum PayStatusEnum {

    INIT(0, "初始化"),
    PROCESSING(1,"处理中"),
    PAY_SUCCESS(2, "待发货"),
    PAY_FAIL(3, "待发货"),
    CLOSED(4, "已关闭");

    PayStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
