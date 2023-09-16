package com.deng.seata.dynamic.ds.facade.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRequest implements Serializable {
    private Integer userId;
    private Long productId;
    private Long count;
    private Long amount;
}
