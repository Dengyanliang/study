package com.deng.hmily.tcc.storage.tx.service;


import com.deng.hmily.tcc.storage.tx.facade.request.StorageRequest;

public interface StorageService {

    void tryFreezeStock(StorageRequest request);
}
