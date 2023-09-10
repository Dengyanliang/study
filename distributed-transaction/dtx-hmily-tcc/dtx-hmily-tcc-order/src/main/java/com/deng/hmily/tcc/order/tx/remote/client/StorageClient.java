package com.deng.hmily.tcc.order.tx.remote.client;

import com.deng.hmily.tcc.order.tx.remote.fallback.StorageClientFallback;
import com.deng.hmily.tcc.order.tx.remote.request.StorageRequest;
import com.deng.hmily.tcc.order.tx.remote.response.StorageResponse;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hmily-tcc-storage",fallbackFactory = StorageClientFallback.class)
public interface StorageClient {

    @PostMapping("/storage/tryFreezeStock")
    @Hmily // keypoint 很重要，不能忘记
    StorageResponse tryFreezeStock(@RequestBody StorageRequest request);
}
