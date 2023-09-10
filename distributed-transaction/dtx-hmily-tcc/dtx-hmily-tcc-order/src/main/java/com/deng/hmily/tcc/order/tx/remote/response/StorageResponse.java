package com.deng.hmily.tcc.order.tx.remote.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class StorageResponse implements Serializable {
    private int code;
    private String message;
}
