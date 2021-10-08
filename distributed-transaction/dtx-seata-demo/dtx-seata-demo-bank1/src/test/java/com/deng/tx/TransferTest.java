package com.deng.tx;

import com.deng.bank1.tx.Bank1Server;
import com.deng.bank1.tx.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= Bank1Server.class)
public class TransferTest {

    @Autowired
    private BankService bankService;

    @Test
    public void test(){
        bankService.transfer(1,2L);
    }
}
