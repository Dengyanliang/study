package com.deng.tcc.order.tx.controller;

import com.deng.tcc.order.tx.facade.request.OrderRequest;
import com.deng.tcc.order.tx.facade.response.OrderResponse;
import com.deng.tcc.order.tx.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public OrderResponse addOrder(@RequestBody OrderRequest orderRequest){
        orderService.addOrder(orderRequest);

        OrderResponse response = new OrderResponse();
        response.setCode(0);
        response.setMessage("成功");

        return response;
    }
}
