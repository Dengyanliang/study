package com.deng.hmily.tcc.storage.tx.controller;

import com.alibaba.fastjson.JSON;
import com.deng.common.enums.ResponseEnum;
import com.deng.hmily.tcc.storage.tx.facade.request.StorageRequest;
import com.deng.hmily.tcc.storage.tx.facade.response.StorageResponse;
import com.deng.hmily.tcc.storage.tx.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/9/8 17:29
 */
@Slf4j
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/tryFreezeStock")
    public StorageResponse tryFreezeStock(@RequestBody StorageRequest request){
        log.info("request:{}", JSON.toJSONString(request));

        StorageResponse response = new StorageResponse();
        storageService.tryFreezeStock(request);
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMessage(ResponseEnum.SUCCESS.getMsg());
        return response;
    }

}
