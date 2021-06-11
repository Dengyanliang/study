package com.deng.study.rpc.service.server;


import java.util.List;

public interface ILoadBalanceService {
    String selectHost(List<String> serviceNodes);
}
