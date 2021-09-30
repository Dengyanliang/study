package com.deng.bank1.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.bank1.tx.remote.client.BankClient;
import com.deng.bank1.tx.remote.request.TransferRequest;
import com.deng.bank1.tx.remote.response.TransferResponse;
import com.deng.bank1.tx.service.BankService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// https://developer.aliyun.com/article/768872
// https://developer.51cto.com/art/202012/634212.htm
// https://blog.csdn.net/qq_37014611/article/details/106467014
// https://cloud.tencent.com/developer/article/1583806
// https://blog.csdn.net/u010634066/article/details/106739176/

@Slf4j
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankClient bankClient;

    @Transactional
    @GlobalTransactional
    @Override
    public boolean transfer(Long id, Double amount) {
        TransferRequest request = new TransferRequest();
        request.setUserId(id);
        request.setAmount(amount);
        TransferResponse response = bankClient.transfer(request);
        log.info("response:{}", JSON.toJSONString(response));
        return true;
    }
}
