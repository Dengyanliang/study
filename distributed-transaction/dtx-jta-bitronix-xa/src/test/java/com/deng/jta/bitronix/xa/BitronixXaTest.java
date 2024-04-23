package com.deng.jta.bitronix.xa;

import com.deng.jta.bitronix.xa.entity.PayOrder;
import com.deng.jta.bitronix.xa.entity.Product;
import com.deng.jta.bitronix.xa.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 11:57
 */
@Slf4j

@SpringBootTest(classes = com.deng.jta.bitronix.xa.MyApplication.class)
public class BitronixXaTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void test(){
        PayOrder payOrder = new PayOrder();
        payOrder.setId(1L);
        payOrder.setProductId(2L);

        Product product = new Product();
        product.setId(1L);
        product.setCount(200L);

        orderService.updateOrder(payOrder,product);
    }
}
