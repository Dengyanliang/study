package com.deng.study.java.thread;

import com.deng.study.dao.MyCourseDao;
import com.deng.study.dao.OrderDao;
import com.deng.study.dao.po.MyCourse;
import com.deng.study.dao.po.PayOrder;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc:批量插入数据库
 * @Auther: dengyanliang
 * @Date: 2021/3/19 16:07
 */
public class ThreadDemo5 implements Runnable{
    private OrderDao orderDao;
    private List<PayOrder> payOrderList;

    private MyCourseDao myCourseDao;
    private List<MyCourse> myCourseList;

    public ThreadDemo5(final List<PayOrder> payOrderList,OrderDao orderDao){
        this.payOrderList = payOrderList;
        this.orderDao = orderDao;
    }

    public ThreadDemo5(MyCourseDao myCourseDao, List<MyCourse> myCourseList) {
        this.myCourseDao = myCourseDao;
        this.myCourseList = myCourseList;
    }

    @Override
    public void run() {
        if(CollectionUtils.isNotEmpty(payOrderList)){
            orderDao.batchInsert(payOrderList);
        }
        if(CollectionUtils.isNotEmpty(myCourseList)){
//            myCourseDao.batchInsert(payOrderList);
        }
    }
}
