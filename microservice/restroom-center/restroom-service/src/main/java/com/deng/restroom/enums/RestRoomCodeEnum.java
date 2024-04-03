package com.deng.restroom.enums;

import com.deng.common.enums.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestRoomCodeEnum implements CodeEnum {
    SIGN_ILLEGAL("2000","签名校验失败"),
    SIGN_VALID_ERROR("2001", "缺少参数或参数错误"),
    SYNC_TIME_ERROR("2002", "请重新同步系统时间"),
    TIME_OUT_ERROR("2003", "请求超时，请重试"),

    ;

    private String code;
    private String msg;
}
