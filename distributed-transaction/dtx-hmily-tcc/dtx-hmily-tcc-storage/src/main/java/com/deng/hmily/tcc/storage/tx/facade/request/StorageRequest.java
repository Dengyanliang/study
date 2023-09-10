package com.deng.hmily.tcc.storage.tx.facade.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class StorageRequest implements Serializable {
    private Long productId;
    private Long count;
}
