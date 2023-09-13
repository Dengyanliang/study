package com.deng.hmily.tcc.bank1.facade.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:02
 */
@Data
public class Bank1AccountResponse implements Serializable {
    private int code;
    private String message;
}
