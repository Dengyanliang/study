package com.deng.tcc.order.tx.facade.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OrderResponse implements Serializable {
    private int code;
    private String message;
}
