package com.deng.seata.dynamic.ds.config;

import com.deng.seata.dynamic.ds.DynamicDsServer;
import com.deng.seata.dynamic.ds.facade.request.OrderRequest;
import com.deng.seata.dynamic.ds.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/16 00:36
 */
@Slf4j

@SpringBootTest(classes= DynamicDsServer.class)
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void test(){
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(2);
        orderRequest.setAmount(10L);
        orderRequest.setProductId(1L);
        orderRequest.setCount(1L);
        orderService.addOrder(orderRequest);
    }
}
