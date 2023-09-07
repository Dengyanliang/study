package com.deng.tcc.order.tx.facade.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderResponse implements Serializable {
    private int code;
    private String message;
}
