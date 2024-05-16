package com.deng.seata.saga.order;

import com.deng.seata.saga.order.service.InventoryAction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes= OrderServer.class)
public class InventoryTest {

    @Autowired
    private InventoryAction inventoryAction;

    @Test
    public void test() {
        inventoryAction.execute("12323",20);
    }
}
