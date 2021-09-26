package com.deng.bank2.tx.facade.response;

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
public class TransferResponse implements Serializable {
    private int code;
    private String message;
}
