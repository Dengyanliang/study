package com.deng.study.designpattern.proxy;

/**
 * @Desc:
 * @Date: 2023/12/29 19:49
 * @Auther: dengyanliang
 */
public class OrderServiceImpl implements IOrderService{
    private IOrderDao orderDao;

    @Override
    public int saveOrder(Order order) {
        // Spring会自己注入，这里直接new
        orderDao = new OrderDaoImpl();
        System.out.println("Service层调用Dao层添加Order");
        return orderDao.insert(order);
    }

}
