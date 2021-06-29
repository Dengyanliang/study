package com.deng.study.rpc.service.client;


import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public abstract class AbstractBalanceService implements LoadBalanceService {

    @Override
    public String selectHost(List<String> serviceNodes){
        if(CollectionUtils.isEmpty(serviceNodes)){
            return "";
        }
        if(serviceNodes.size() == 1){
            return serviceNodes.get(0);
        }
        return doSelect(serviceNodes);
    }

    public abstract String doSelect(List<String> serviceNodes);
}
