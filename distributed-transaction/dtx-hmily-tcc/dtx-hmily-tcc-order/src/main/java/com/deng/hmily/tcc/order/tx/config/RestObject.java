package com.deng.hmily.tcc.order.tx.config;

import lombok.Builder;
import lombok.Data;

/**
 * @Desc: 自定义响应对象
 * @Auther: dengyanliang
 * @Date: 2023/11/5 14:10
 */
@Data
@Builder
public class RestObject {
    private int statusCode;
    private String message;
    private Object data;
}
