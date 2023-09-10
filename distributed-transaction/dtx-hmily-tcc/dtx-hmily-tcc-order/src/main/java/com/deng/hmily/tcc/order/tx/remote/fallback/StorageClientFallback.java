package com.deng.hmily.tcc.order.tx.remote.fallback;

import com.deng.hmily.tcc.order.tx.remote.request.StorageRequest;
import com.deng.hmily.tcc.order.tx.remote.response.StorageResponse;
import com.deng.hmily.tcc.order.tx.remote.client.StorageClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StorageClientFallback implements FallbackFactory<StorageClient> {

    @Override
    public StorageClient create(Throwable throwable) {
        return new StorageClient() {
            @Override
            public StorageResponse tryFreezeStock(StorageRequest request) {
                log.error("tryFreezeStock 发生了异常。。。");
                return null;
            }
        };
    }
}
