package com.deng.study.rpc.service.client;


import java.util.List;

public interface LoadBalanceService {
    String selectHost(List<String> serviceNodes);
}
