package com.deng.seata.tcc.order.remote.fallback;

import com.deng.seata.tcc.order.remote.client.AccountClient;
import com.deng.seata.tcc.order.remote.request.AccountRequest;
import com.deng.seata.tcc.order.remote.response.AccountResponse;
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
            public AccountResponse transfer(AccountRequest request) {
                log.error("发生了异常。。。");
                return null;
            }
        };
    }
}
