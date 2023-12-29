package com.deng.study.designpattern.proxy.dynamicproxy;

import com.deng.study.designpattern.proxy.IOrderService;
import com.deng.study.designpattern.proxy.Order;
import com.deng.study.designpattern.proxy.OrderServiceImpl;

/**
 * @Desc:
 * @Date: 2023/12/29 20:28
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        Order order = new Order();
        order.setUserId(1);

        // 被代理的对象
        IOrderService orderService = new OrderServiceImpl();

        OrderServiceDynamicProxy dynamicProxy = new OrderServiceDynamicProxy(orderService);
        IOrderService orderServiceProxy = (IOrderService)dynamicProxy.bind();

        // 通过代理类操作方法
        orderServiceProxy.saveOrder(order);

    }
}
