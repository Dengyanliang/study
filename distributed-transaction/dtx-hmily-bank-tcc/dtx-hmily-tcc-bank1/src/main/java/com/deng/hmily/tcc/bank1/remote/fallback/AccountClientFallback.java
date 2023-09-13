package com.deng.hmily.tcc.bank1.remote.fallback;

import com.deng.hmily.tcc.bank1.remote.client.AccountClient;
import com.deng.hmily.tcc.bank1.remote.request.Bank2AccountRequest;
import com.deng.hmily.tcc.bank1.remote.response.Bank2AccountResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountClientFallback implements FallbackFactory<AccountClient> {

    @Override
    public AccountClient create(Throwable throwable) {
        return new AccountClient() {
            @Override
            public Bank2AccountResponse tryFreezeAmount(Bank2AccountRequest request) {
                log.error("tryFreezeAmount 发生了异常。。。");
                return null;
            }
        };
    }
}
