package com.deng.study.rpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应传输模型
 */
@Data
public class ResponseModel implements Serializable{
    /**
     * 返回结果
     */
    private Object result;
}
