package com.deng.seata.tcc.account.controller;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.ResponseEnum;
import com.deng.seata.tcc.account.facade.request.AccountRequest;
import com.deng.seata.tcc.account.facade.response.AccountResponse;
import com.deng.seata.tcc.account.service.AccountService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
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
public class AccountController implements AccountTCCFacade{

    @Autowired
    private AccountService transferService;

    @PostMapping("/transfer")
    public AccountResponse transferTCC(@RequestBody AccountRequest request){
        log.info("request:{}", JSON.toJSONString(request));

        String xid = RootContext.getXID(); // 全局事务id
        log.info("全局事务事务ID------>{}", xid);

        AccountResponse response = new AccountResponse();
        transferService.transfer(request);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMsg());
        return response;
    }

    public void transferCommit(BusinessActionContext actionContext){
        String requestString = actionContext.getActionContext("accountRequest").toString();
        log.info("》》》》transferCommit 请求信息：{}", requestString);
        AccountRequest accountRequest = JSON.parseObject(requestString, AccountRequest.class);
        transferService.transferCommit(accountRequest);
    }

    public void transferCancel(BusinessActionContext actionContext){
        String requestString = actionContext.getActionContext("accountRequest").toString();
        log.info("》》》》transferCancel 请求信息：{}", requestString);
        AccountRequest accountRequest = JSON.parseObject(requestString, AccountRequest.class);
        transferService.transferCancel(accountRequest);
    }
}
