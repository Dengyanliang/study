package com.deng.hmily.tcc.order.tx.remote.client;

import com.deng.hmily.tcc.order.tx.remote.fallback.AccountClientFallback;
import com.deng.hmily.tcc.order.tx.remote.request.AccountRequest;
import com.deng.hmily.tcc.order.tx.remote.response.AccountResponse;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hmily-tcc-account",fallbackFactory = AccountClientFallback.class)
public interface AccountClient {

    @PostMapping("/account/tryFreezeAmount")
    @Hmily // keypoint 很重要，不能忘记
    AccountResponse tryFreezeAmount(@RequestBody AccountRequest request);
}
