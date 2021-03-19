package com.deng.study.geekbang.week07;

import com.deng.study.dao.po.Order;
import com.deng.study.service.OrderService;
import com.deng.study.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:10
 */
@Slf4j
@SpringBootTest
public class OrderTest {

    @Autowired
    OrderService orderService;

    @Test
    void addOrder(){
        long start = System.currentTimeMillis();
        int max = 1000000;
        Order order = null;
        for(int i = 1; i <= max; i++){
            order = new Order();
            order.setUserId((long)(Math.random()*10000));
            order.setProductId((long)(Math.random()*100000));
            order.setProductNum((long)(Math.random()*5));
            order.setOrderPrice(order.getProductNum() * (long)(Math.random()*100));
            order.setStatus(1);
            order.setPayStatus(1);
            order.setCreateTime(DateUtil.now());
            order.setPayFinishedTime(DateUtil.now());

            orderService.addOrder(order);
        }
        long end = System.currentTimeMillis();
        log.info("总的耗时：{}",end-start);
    }

}
