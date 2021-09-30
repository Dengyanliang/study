package com.deng.bank2.tx.controller;

import com.alibaba.fastjson.JSON;
import com.deng.bank2.tx.facade.request.TransferRequest;
import com.deng.bank2.tx.facade.response.TransferResponse;
import com.deng.bank2.tx.service.TransferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:59
 */
@Slf4j
@RestController
@RequestMapping("/bank2")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping("/transfer")
    public TransferResponse transfer(@RequestBody TransferRequest request){
        log.info("request:{}", JSON.toJSONString(request));
        TransferResponse response = new TransferResponse();
        transferService.transfer(request);
        return response;
    }
}
