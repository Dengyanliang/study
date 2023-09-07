package com.deng.tcc.account.tx.facade.request;


import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:02
 */
@Data
public class AccountRequest implements Serializable {
    private Integer userId;
    private Long amount;
}
