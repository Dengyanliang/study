package com.deng.seata.saga.account.facade.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Date: 2024/5/16 10:24
 * @Auther: dengyanliang
 */
@Data
public class BalanceResponse implements Serializable {
    private String code;
    private String msg;
}
