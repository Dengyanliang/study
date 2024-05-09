package com.deng.study.geekbang.week07;

import com.deng.study.MyApplication;
import com.deng.study.pojo.MyOrder;
import com.deng.study.service.MyOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Desc:
 * 1、@RunWith(SpringRunner.class)
 *   1.1 注解的意义在于Test测试类要使用spring容器中的类，比如@Autowired修饰的，如果不用该注解，在使用类时会抛出NullPointerExecption
 *   1.2 配置的@Test 一定要使用org.junit.Test，不然会报错
 * 2、如果不用@RunWith(SpringRunner.class)，那么使用@Test，必须是org.junit.jupiter.api.Test
 *
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:10
 */
//@RunWith(SpringRunner.class)
@Slf4j
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
