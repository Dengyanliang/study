package com.deng.bank1.tx.remote.fallback;

import com.deng.bank1.tx.remote.client.BankClient;
import com.deng.bank1.tx.remote.request.AccountRequest;
import com.deng.bank1.tx.remote.response.AccountResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BankClientFallback implements FallbackFactory<BankClient> {

    @Override
    public BankClient create(Throwable throwable) {
        return new BankClient() {
            @Override
            public AccountResponse transfer(AccountRequest request) {
                log.error("发生了异常。。。");
                return null;
            }
        };
    }
}
