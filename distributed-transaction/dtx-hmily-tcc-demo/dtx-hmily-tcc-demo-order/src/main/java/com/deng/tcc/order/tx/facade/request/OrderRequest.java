package com.deng.tcc.order.tx.facade.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class OrderRequest implements Serializable {
    private Integer userId;
    private Long amount;
}
