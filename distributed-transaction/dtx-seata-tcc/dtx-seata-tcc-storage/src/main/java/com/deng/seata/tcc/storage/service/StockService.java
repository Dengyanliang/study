package com.deng.seata.tcc.storage.service;


import com.deng.seata.tcc.storage.facade.request.StockRequest;

public interface StockService {

    void tryFreeze(StockRequest request);

    void commitFreeze(StockRequest request);

    void cancelFreeze(StockRequest request);
}
