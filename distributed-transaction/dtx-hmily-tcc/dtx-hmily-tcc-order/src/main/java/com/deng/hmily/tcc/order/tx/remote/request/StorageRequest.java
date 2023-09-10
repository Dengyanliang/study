package com.deng.hmily.tcc.order.tx.remote.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class StorageRequest implements Serializable {
    private Long productId;
    private Long count;
}
