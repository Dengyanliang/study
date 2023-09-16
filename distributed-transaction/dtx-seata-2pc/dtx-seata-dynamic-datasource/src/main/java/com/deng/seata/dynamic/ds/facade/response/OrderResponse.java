package com.deng.seata.dynamic.ds.facade.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderResponse implements Serializable {
    private int code;
    private String message;
}
