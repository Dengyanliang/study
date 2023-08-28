package com.deng.tcc.order.tx.remote.client;

import com.deng.tcc.order.tx.remote.fallback.AccountClientFallback;
import com.deng.tcc.order.tx.remote.request.AccountRequest;
import com.deng.tcc.order.tx.remote.response.AccountResponse;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tcc-demo-account",fallbackFactory = AccountClientFallback.class)
public interface AccountClient {

    @PostMapping("/account/updateBalance")
    @Hmily // 很重要，不能忘记
    AccountResponse updateBalance(@RequestBody AccountRequest request);
}
