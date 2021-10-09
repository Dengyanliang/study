package com.deng.tcc.order.tx.remote.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:02
 */
@Setter
@Getter
public class AccountResponse implements Serializable {
    private int code;
    private String message;
}
