package com.deng.study.designpattern.structural.proxy.staticproxy;

import com.deng.study.designpattern.structural.proxy.Order;

/**
 * @Desc:
 * @Date: 2023/12/29 19:59
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        Order order = new Order();
        order.setUserId(2);

        OrderServiceStaticProxy proxy = new OrderServiceStaticProxy();
        proxy.saveOrder(order);
    }
}
