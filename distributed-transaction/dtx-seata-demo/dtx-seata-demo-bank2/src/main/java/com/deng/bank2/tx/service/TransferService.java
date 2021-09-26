package com.deng.bank2.tx.service;

import com.deng.bank2.tx.facade.request.TransferRequest;
import com.deng.bank2.tx.facade.response.TransferResponse;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 22:06
 */
public interface TransferService {
    boolean transfer(TransferRequest request);
}
