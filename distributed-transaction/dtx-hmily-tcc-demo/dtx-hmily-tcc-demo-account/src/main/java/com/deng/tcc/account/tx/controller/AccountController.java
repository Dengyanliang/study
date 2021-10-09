package com.deng.tcc.account.tx.controller;

import com.alibaba.fastjson.JSON;
import com.deng.tcc.account.tx.facade.request.AccountRequest;
import com.deng.tcc.account.tx.facade.response.AccountResponse;
import com.deng.tcc.account.tx.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:59
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService transferService;

    @PostMapping("/transfer")
    public AccountResponse transfer(@RequestBody AccountRequest request){
        log.info("request:{}", JSON.toJSONString(request));
        AccountResponse response = new AccountResponse();
        transferService.transfer(request);
        return response;
    }
}
