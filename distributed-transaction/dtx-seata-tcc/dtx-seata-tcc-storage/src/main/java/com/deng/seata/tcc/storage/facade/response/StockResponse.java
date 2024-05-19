package com.deng.seata.tcc.storage.facade.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockResponse implements Serializable {
    private String code;
    private String message;
}
