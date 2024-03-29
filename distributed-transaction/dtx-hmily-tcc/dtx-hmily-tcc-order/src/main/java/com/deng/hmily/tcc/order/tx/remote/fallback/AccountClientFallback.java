package com.deng.hmily.tcc.order.tx.remote.fallback;

import com.deng.hmily.tcc.order.tx.remote.client.AccountClient;
import com.deng.hmily.tcc.order.tx.remote.request.AccountRequest;
import com.deng.hmily.tcc.order.tx.remote.response.AccountResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 实现FallbackFactory的目的就是为了在服务降级的时候，能够拿到具体的服务错误信息
 */
@Slf4j
@Component
public class AccountClientFallback implements FallbackFactory<AccountClient> {

    @Override
    public AccountClient create(Throwable throwable) {
        return new AccountClient() {
            @Override
            public AccountResponse tryFreezeAmount(AccountRequest request) {
                log.error("tryFreezeAmount 发生了异常。。。" + throwable.getMessage());
                return null;
            }
        };
    }
}
