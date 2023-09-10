package com.deng.hmily.tcc.account.tx.controller;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.ResponseEnum;
import com.deng.hmily.tcc.account.tx.service.AccountService;
import com.deng.hmily.tcc.account.tx.facade.request.AccountRequest;
import com.deng.hmily.tcc.account.tx.facade.response.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:59
 */
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService transferService;

    @PostMapping("/tryFreezeAmount")
    public AccountResponse tryFreezeAmount(@RequestBody AccountRequest request){
        log.info("request:{}", JSON.toJSONString(request));
        AccountResponse response = new AccountResponse();
        transferService.tryFreezeAmount(request);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMsg());

        return response;
    }
}
