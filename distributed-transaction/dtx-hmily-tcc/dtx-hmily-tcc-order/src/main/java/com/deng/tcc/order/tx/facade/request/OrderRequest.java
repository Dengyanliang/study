package com.deng.tcc.order.tx.facade.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderRequest implements Serializable {
    private Integer userId;
    private Integer productId;
    private Long amount;
}
