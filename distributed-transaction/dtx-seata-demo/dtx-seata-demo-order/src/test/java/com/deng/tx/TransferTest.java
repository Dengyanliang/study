package com.deng.tx;

import com.deng.order.tx.OrderServer;
import com.deng.order.tx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= OrderServer.class)
public class TransferTest {

    @Autowired
    private OrderService bankService;

    @Test
    public void test(){
        bankService.transfer(1,10L);
    }
}
