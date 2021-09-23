package com.deng.study.geekbang.week07;

import com.deng.study.MyApplication;
import com.deng.study.dao.po.PayOrder;
import com.deng.study.service.OrderService;
import com.deng.study.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:10
 */

@Slf4j
// @RunWith(SpringRunner.class)注解的意义在于Test测试类要使用注入的类，比如@Autowired注入的类,)这些类才能实例化到spring容器中，
// 自动注入才能生效，然直接一个NullPointerExecption
// 一定要使用org.junit.Test，不然会报错
@RunWith(SpringRunner.class)
@SpringBootTest(classes= MyApplication.class)
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Test
    public void addOrder(){
        long start = System.currentTimeMillis();
        int max = 10;
        PayOrder order = null;
        for(int i = 1; i <= max; i++){
            order = new PayOrder();
            order.setUserId((long)(Math.random()*10000));
            order.setProductId((long)(Math.random()*100000));
            order.setOrderFee((long)(Math.random()*100));
            order.setPayStatus(1);
            order.setCreateTime(DateUtil.now());
            order.setPayFinishTime(DateUtil.now());
            order.setVersion(1);
            orderService.addOrder(order);
        }
        long end = System.currentTimeMillis();
        log.info("总的耗时：{}",end-start);
    }

    @Test
    public void addOrderList(){
        log.info("add order list begin...");
        long start = System.currentTimeMillis();
        int max = 1000000;
        List<PayOrder> orderList = new CopyOnWriteArrayList<>();
        PayOrder order = null;
        for(int i = 1; i <= max; i++){
            order = new PayOrder();
            order.setUserId((long)(Math.random()*10000));
            order.setProductId((long)(Math.random()*100000));
            order.setOrderFee((long)(Math.random()*100));
            order.setPayStatus(1);
            order.setCreateTime(DateUtil.now());
            order.setPayFinishTime(DateUtil.now());
            order.setVersion(1);
            orderList.add(order);

            if(i % 10000 == 0){
                orderService.addBatchOrder(orderList);
                log.info("orderList:{}",orderList.size());
                orderList = new CopyOnWriteArrayList<>();
            }
        }
        long end = System.currentTimeMillis();
        log.info("add order list end,总的耗时：{}",end-start);
    }

    @Test
    public void addOrderList２(){
        log.info("add order list begin...");
        long start = System.currentTimeMillis();
        orderService.addBatchOrder2();
        long end = System.currentTimeMillis();
        log.info("add order list end,总的耗时：{}",end-start);
    }

    @Test
    public void getOrder(){
        log.info("add order list begin...");
        long start = System.currentTimeMillis();
        for(int i = 0 ; i < 4; i++){
            PayOrder payOrder = orderService.getOrder2(1L);
            log.info("payOrder:{}",payOrder);
        }
        long end = System.currentTimeMillis();
        log.info("add order list end,总的耗时：{}",end-start);
    }
}
