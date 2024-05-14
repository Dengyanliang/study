package com.deng.seata.saga.order;

import com.deng.seata.saga.order.facade.request.OrderRequest;
import com.deng.seata.saga.order.service.OrderService;
import com.deng.seata.saga.order.utils.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Slf4j
@SpringBootTest(classes= OrderServer.class)
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void test() throws IOException {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setUserId(2);
        orderRequest.setAmount(10L);
        orderRequest.setProductId(1L);
        orderRequest.setCount(1L);
        orderService.addOrder(orderRequest);
    }
}
