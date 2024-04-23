package com.deng.hmily.tcc.order.tx;

import com.deng.hmily.tcc.order.tx.facade.request.OrderRequest;
import com.deng.hmily.tcc.order.tx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/6 12:13
 */
@Slf4j

@SpringBootTest(classes = OrderApplication.class)
public class HmilyTest {

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
