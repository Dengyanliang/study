package com.deng.seata.saga.order;

import com.deng.seata.saga.order.facade.request.OrderRequest;
import com.deng.seata.saga.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes= OrderServer.class)
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(2);
        orderRequest.setAmount(10L);
        orderRequest.setProductId(1L);
        orderRequest.setCount(1L);
        orderService.addOrder(orderRequest);
    }
}
