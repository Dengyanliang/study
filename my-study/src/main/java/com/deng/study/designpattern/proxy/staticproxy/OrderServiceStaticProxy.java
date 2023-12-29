package com.deng.study.designpattern.proxy.staticproxy;

import com.deng.study.designpattern.proxy.IOrderService;
import com.deng.study.designpattern.proxy.Order;
import com.deng.study.designpattern.proxy.OrderServiceImpl;
import com.deng.study.designpattern.proxy.db.DataSourceContextHolder;

/**
 * @Desc:
 * @Date: 2023/12/29 19:52
 * @Auther: dengyanliang
 */
public class OrderServiceStaticProxy {
    private IOrderService orderService;

    public int saveOrder(Order order) {
        // 方法增强前
        beforeMethod(order);
        // 执行真正的目标方法
        orderService = new OrderServiceImpl();
        int count = orderService.saveOrder(order);

        // 方法增强后
        afterMethod();
        return count;
    }


    private void beforeMethod(Order order){
        int userId = order.getUserId();
        int dbRouter = userId % 2;
        System.out.println("静态代理分配到到【db"+dbRouter+"】处理数据");
        // 设置dataSource
        DataSourceContextHolder.setDBType("db"+dbRouter);

        System.out.println("静态代理 before code");
    }

    private void afterMethod(){
        // 清除dataSource
        DataSourceContextHolder.clearDBType();

        System.out.println("静态代理 after code");
    }

}
