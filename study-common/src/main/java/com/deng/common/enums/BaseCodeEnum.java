package com.deng.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Desc:
 * @Date: 2024/4/3 18:18
 * @Auther: dengyanliang
 */
@Getter
@AllArgsConstructor
public enum BaseCodeEnum implements CodeEnum {
    SUCCESS("0000","成功"),

    FAIL("1001","处理失败"),

    SYSTEM_ERROR("1002", "服务器内部错误"),

    PARAMETER_NULL_ERROR("1003","参数为空异常!"),

    CONNECT_FAIL("1004","网络连接失败，请稍后再试~"),

    NET_ERROR("1005", "系统异常，请稍后再试~"),

    TRY_LOCK_ERROR("1006", "请勿频繁操作~"),

    NO_REQUEST_METHOD_ERROR("1007", "不支持的请求方法~"),

    REQUEST_PATH_NOT_EXISTS_ERROR("1008", "请求地址不存在~"),

    PARAM_ERROR("1009", "参数错误~"),

    PARAM_TYPE_ERROR("1010", "参数类型错误~"),

    DATA_IS_NULL("1011","返回数据为空"),

    DATA_DUPLICATION("1012","数据重复"),



    ;

    private String code;
    private String msg;
}
