package com.deng.seata.saga.account.facade.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Date: 2024/5/16 10:29
 * @Auther: dengyanliang
 */
@Data
public class BalanceInfo implements Serializable {
    private String name;
    private Long amount;
}
