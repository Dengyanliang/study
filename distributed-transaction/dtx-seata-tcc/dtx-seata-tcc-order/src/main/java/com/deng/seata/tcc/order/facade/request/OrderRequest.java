package com.deng.seata.tcc.order.facade.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRequest implements Serializable {
    private Integer userId;
    private Long productId;
    private Long amount;
    private Long count;
}
