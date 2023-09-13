package com.deng.hmily.tcc.bank1.facade.request;


import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:02
 */
@Data
public class Bank1AccountRequest implements Serializable {
    private Integer bank1userId;
    private Integer bank2UserId;
    private Long amount;
}
