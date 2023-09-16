package com.deng.seata.dynamic.ds.service;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
public interface AccountService {
    void deductAmount(Integer userId, Long amount);
}
