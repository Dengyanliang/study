package com.deng.study.java.thread;

import com.deng.study.mapper.MyOrderMapper;
import com.deng.study.mapper.PayOrderMapper;
import com.deng.study.pojo.MyOrder;
import com.deng.study.pojo.PayOrder;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Desc:批量插入数据库
 * @Auther: dengyanliang
 * @Date: 2021/3/19 16:07
 */
public class ThreadDemo5 implements Runnable{
    private PayOrderMapper payOrderMapper;
    private List<PayOrder> payOrderList;

    private MyOrderMapper myOrderMapper;
    private List<MyOrder> myOrderList;

    public ThreadDemo5(final List<PayOrder> payOrderList,PayOrderMapper payOrderMapper){
        this.payOrderList = payOrderList;
        this.payOrderMapper = payOrderMapper;
    }

    public ThreadDemo5(MyOrderMapper myOrderMapper, List<MyOrder> myOrderList) {
        this.myOrderMapper = myOrderMapper;
        this.myOrderList = myOrderList;
    }

    @Override
    public void run() {
        if(CollectionUtils.isNotEmpty(payOrderList)){
            payOrderMapper.batchInsert(payOrderList);
        }
//        if(CollectionUtils.isNotEmpty(myOrderList)){
//            myOrderMapper.batchInsert(payOrderList);
//        }
    }
}
