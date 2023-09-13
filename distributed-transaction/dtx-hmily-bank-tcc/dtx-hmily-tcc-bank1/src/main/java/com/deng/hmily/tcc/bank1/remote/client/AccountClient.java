package com.deng.hmily.tcc.bank1.remote.client;

import com.deng.hmily.tcc.bank1.remote.fallback.AccountClientFallback;
import com.deng.hmily.tcc.bank1.remote.request.Bank2AccountRequest;
import com.deng.hmily.tcc.bank1.remote.response.Bank2AccountResponse;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hmily-tcc-bank2",fallbackFactory = AccountClientFallback.class)
public interface AccountClient {

    @PostMapping("/account/tryFreezeAmount")
    @Hmily // keypoint 很重要，不能忘记
    Bank2AccountResponse tryFreezeAmount(@RequestBody Bank2AccountRequest request);
}
