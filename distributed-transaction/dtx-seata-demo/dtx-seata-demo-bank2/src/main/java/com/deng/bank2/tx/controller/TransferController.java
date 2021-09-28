package com.deng.bank2.tx.controller;

import com.deng.bank2.tx.facade.request.TransferRequest;
import com.deng.bank2.tx.facade.response.TransferResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/9/26 07:59
 */
@RestController("/tx")
public class TransferController {

    @PostMapping("/transfer")
    public TransferResponse transfer(TransferRequest request){
        TransferResponse response = new TransferResponse();

        return response;
    }
}
