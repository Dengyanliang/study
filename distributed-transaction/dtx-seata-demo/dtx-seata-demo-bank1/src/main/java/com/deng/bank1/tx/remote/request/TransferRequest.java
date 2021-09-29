package com.deng.bank1.tx.remote.request;


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
public class TransferRequest implements Serializable {
    private Long userId;
    private Double amount;
}
