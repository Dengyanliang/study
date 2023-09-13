package com.deng.hmily.tcc.bank1;

import com.deng.hmily.tcc.bank1.facade.request.Bank1AccountRequest;
import com.deng.hmily.tcc.bank1.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/13 18:16
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Bank1Application.class)
public class HmilyTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void test(){
        Bank1AccountRequest request = new Bank1AccountRequest();
        request.setBank1userId(2);
        request.setBank2UserId(1);
        request.setAmount(10L);
        accountService.tryFreezeAmount(request);
    }

}
