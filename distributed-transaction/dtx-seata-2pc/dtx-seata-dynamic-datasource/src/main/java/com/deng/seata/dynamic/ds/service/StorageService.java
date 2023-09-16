package com.deng.seata.dynamic.ds.service;


public interface StorageService {

    void deductStock(Long id, Long count);
}
