package com.deng.study.geekbang.week07;

import com.deng.common.util.DateUtil;
import com.deng.study.MyApplication;
import com.deng.study.pojo.PayOrder;
import com.deng.study.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:10
 */

@Slf4j
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
