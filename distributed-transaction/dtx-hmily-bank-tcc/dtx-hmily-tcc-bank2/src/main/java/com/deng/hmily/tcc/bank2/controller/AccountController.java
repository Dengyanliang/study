package com.deng.hmily.tcc.bank2.controller;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.ResponseEnum;
import com.deng.hmily.tcc.bank2.service.AccountService;
import com.deng.hmily.tcc.bank2.facade.request.Bank2AccountRequest;
import com.deng.hmily.tcc.bank2.facade.response.Bank2AccountResponse;
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
    public Bank2AccountResponse tryFreezeAmount(@RequestBody Bank2AccountRequest request){
        log.info("request:{}", JSON.toJSONString(request));
        Bank2AccountResponse response = new Bank2AccountResponse();
        transferService.tryFreezeAmount(request);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMsg());

        return response;
    }
}
