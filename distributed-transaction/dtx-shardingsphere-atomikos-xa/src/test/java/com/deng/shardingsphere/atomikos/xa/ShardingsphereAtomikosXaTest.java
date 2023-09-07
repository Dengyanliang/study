package com.deng.shardingsphere.atomikos.xa;

import com.deng.shardingsphere.atomikos.xa.entity.PayOrder;
import com.deng.shardingsphere.atomikos.xa.entity.Product;
import com.deng.shardingsphere.atomikos.xa.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 11:57
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class ShardingsphereAtomikosXaTest {

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
