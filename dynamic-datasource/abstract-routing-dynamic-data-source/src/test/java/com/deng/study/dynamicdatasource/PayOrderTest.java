package com.deng.study.dynamicdatasource;

import com.deng.study.dynamic.datasource.MyApplication;
import com.deng.study.dynamic.datasource.entity.PayOrder;
import com.deng.study.dynamic.datasource.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:10
 */

@Slf4j
@SpringBootTest(classes= MyApplication.class)
public class PayOrderTest {

    @Autowired
    private PayOrderService payOrderService;

    @Test
    public void addOrder(){
        long start = System.currentTimeMillis();
        int max = 5;
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
            payOrderService.addOrder(order);
        }
        long end = System.currentTimeMillis();
        log.info("总的耗时：{}",end-start);
    }

    @Test
    public void getOrder(){
        for (int i = 0; i < 4; i++) {
            PayOrder payOrder = payOrderService.getOrder(1L,2L);
            log.info("第 {} 次 查询，payOrder：{}",(i+1),payOrder);
        }
    }
}
