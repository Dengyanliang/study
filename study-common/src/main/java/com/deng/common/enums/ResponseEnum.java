package com.deng.common.enums;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/7 18:19
 */
public enum ResponseEnum {

    SUCCESS(10000, "成功"),



    ;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
