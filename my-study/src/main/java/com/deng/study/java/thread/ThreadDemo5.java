package com.deng.study.java.thread;

import com.deng.study.dao.OrderDao;
import com.deng.study.dao.po.PayOrder;
import java.util.List;

/**
 * @Desc:批量插入数据库
 * @Auther: dengyanliang
 * @Date: 2021/3/19 16:07
 */
public class ThreadDemo5 implements Runnable{
    private OrderDao orderDao;
    private List<PayOrder> payOrderList;

    public ThreadDemo5(final List<PayOrder> payOrderList,OrderDao orderDao){
        this.payOrderList = payOrderList;
        this.orderDao = orderDao;
    }

    @Override
    public void run() {
        orderDao.batchInsert(payOrderList);
    }
}
