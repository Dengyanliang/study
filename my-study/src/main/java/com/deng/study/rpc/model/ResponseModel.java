package com.deng.study.rpc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 响应传输模型
 */
@Setter
@Getter
@ToString
public class ResponseModel implements Serializable{
    /**
     * 返回结果
     */
    private Object result;
}
