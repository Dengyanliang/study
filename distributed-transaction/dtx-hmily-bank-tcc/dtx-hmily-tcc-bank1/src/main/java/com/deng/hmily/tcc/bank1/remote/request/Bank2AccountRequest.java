package com.deng.hmily.tcc.bank1.remote.request;


import lombok.Data;

import java.io.Serializable;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:02
 */
@Data
public class Bank2AccountRequest implements Serializable {
    private Integer userId;
    private Long amount;
}
