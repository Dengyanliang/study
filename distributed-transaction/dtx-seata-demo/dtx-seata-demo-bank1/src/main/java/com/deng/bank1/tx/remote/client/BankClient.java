package com.deng.bank1.tx.remote.client;

import com.deng.bank1.tx.remote.fallback.BankClientFallback;
import com.deng.bank1.tx.remote.request.AccountRequest;
import com.deng.bank1.tx.remote.response.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "seata-demo-bank2",fallbackFactory = BankClientFallback.class)
public interface BankClient {

    @PostMapping("/account/transfer")
    AccountResponse transfer(@RequestBody AccountRequest request);
}
