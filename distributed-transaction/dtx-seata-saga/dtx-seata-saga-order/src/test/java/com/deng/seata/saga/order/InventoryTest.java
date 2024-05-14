package com.deng.seata.saga.order;

import com.deng.seata.saga.order.service.InventoryAction;
import com.deng.seata.saga.order.utils.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest(classes= OrderServer.class)
public class InventoryTest {

    @Autowired
    private InventoryAction inventoryAction;

    @Test
    public void test() throws InterruptedException {

        inventoryAction.execute("12323",20);

        Thread.sleep(100000);
    }
}
