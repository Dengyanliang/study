package com.deng.seata.tcc.account.facade.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:02
 */
@Data
public class AccountResponse implements Serializable {
    private int code;
    private String message;
}
