package com.deng.seata.tcc.storage.facade.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockRequest implements Serializable {
    private Long productId;
    private Long count;
}
