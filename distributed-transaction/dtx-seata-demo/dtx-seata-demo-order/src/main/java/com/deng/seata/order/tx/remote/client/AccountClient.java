package com.deng.seata.order.tx.remote.client;

import com.deng.seata.order.tx.remote.fallback.AccountClientFallback;
import com.deng.seata.order.tx.remote.request.AccountRequest;
import com.deng.seata.order.tx.remote.response.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "seata-demo-account",fallbackFactory = AccountClientFallback.class)
public interface AccountClient {

    @PostMapping("/account/transfer")
    AccountResponse transfer(@RequestBody AccountRequest request);
}
