package com.deng.study.rpc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 请求传输模型
 */
@Setter
@Getter
@ToString
public class RequestModel implements Serializable{
    /**
     * 将要调用的对象
     */
    private Object object;
    /**
     * 将要调用的方法
     */
    private String methodName;
    /**
     * 将要调用的方法参数类型
     */
    private Class<?>[] parameterTypes;
    /**
     * 将要调用的方法参数
     */
    private Object[] parameters;
}
