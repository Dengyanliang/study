package com.deng.study.rpc.service.client;

import java.util.List;
import java.util.Random;

public class RandomBalanceServiceImpl extends AbstractBalanceService{

    @Override
    public String doSelect(List<String> serviceNodes) {
        Random random = new Random();
        int indexSelect = random.nextInt(serviceNodes.size());
        return serviceNodes.get(indexSelect);
    }
}
