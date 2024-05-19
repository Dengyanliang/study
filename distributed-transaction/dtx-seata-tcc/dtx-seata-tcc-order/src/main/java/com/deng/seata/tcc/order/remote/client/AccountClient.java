package com.deng.seata.tcc.order.remote.client;

import com.deng.seata.tcc.order.remote.fallback.AccountClientFallback;
import com.deng.seata.tcc.order.remote.request.AccountRequest;
import com.deng.seata.tcc.order.remote.response.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "dtx-seata-tcc-account",fallbackFactory = AccountClientFallback.class)
public interface AccountClient {

    @PostMapping("/account/transfer")
    AccountResponse transfer(@RequestBody AccountRequest request);

}
