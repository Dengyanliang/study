package com.deng.study.geekbang.week07;

import com.deng.study.MyApplication;
import com.deng.study.dao.po.MyOrder;
import com.deng.study.service.MyOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class MyOrderTest {

    @Autowired
    private MyOrderService myOrderService;

    @Test
    public void addBatchMyCourse(){
        long start = System.currentTimeMillis();

        Random random = new Random();
        List<MyOrder> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            MyOrder order = new MyOrder();
            order.setName("测试-" + i);
            order.setUserId((long) random.nextInt(100000000));
            order.setStatus("normal");
            list.add(order);
        }
        myOrderService.addBatchMyOrderByPreparedStatement(list);

        long end = System.currentTimeMillis();
        System.out.println("耗费：" + (end-start));
    }

}
