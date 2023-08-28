package com.deng.seata.order.tx.remote.fallback;

import com.deng.seata.order.tx.remote.client.AccountClient;
import com.deng.seata.order.tx.remote.request.AccountRequest;
import com.deng.seata.order.tx.remote.response.AccountResponse;
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
