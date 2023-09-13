package com.deng.hmily.tcc.bank2.facade.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:02
 */
@Data
public class Bank2AccountResponse implements Serializable {
    private int code;
    private String message;
}
