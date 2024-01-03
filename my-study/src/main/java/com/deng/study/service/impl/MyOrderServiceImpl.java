package com.deng.study.service.impl;

import com.deng.common.util.DateUtil;
import com.deng.common.util.JdbcUtils;
import com.deng.study.dao.MyOrderDao;
import com.deng.study.dao.po.MyOrder;
import com.deng.study.service.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    MyOrderDao myOrderDao;

    @Resource
    ThreadPoolTaskExecutor taskExecutor;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMyOrder(MyOrder MyOrder) {
        myOrderDao.addMyOrder(MyOrder);
    }


    @Override
    @Transactional
    public void addBatchMyOrderByThread(List<MyOrder> myOrderList) {
//        orderDao.addList(orderList);
        int count = taskExecutor.getActiveCount();
        System.out.println(count+"----");

//        ThreadDemo5 threadDemo5 = new ThreadDemo5(orderList,orderDao);
//        taskExecutor.execute(threadDemo5);

    }

    @Override
    public void addBatchMyOrderByPreparedStatement(List<MyOrder> myOrderList) {
        //事务上限，每次累计这么多条数，提交一次事务
        int maxCommit = 100000;
        try{
            Connection conn = JdbcUtils.getConnection();
            String sql = "insert into my_order (name, user_id, status, create_Time, update_time) values (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 关闭自动提交，不然conn.commit()运行到这句会报错
            conn.setAutoCommit(false);

            for (int i = 0; i < myOrderList.size(); i++) {
                MyOrder MyOrder = myOrderList.get(i);
                // 设置参数
                pstmt.setString(1, MyOrder.getName());
                pstmt.setLong(2, MyOrder.getUserId());
                pstmt.setString(3, MyOrder.getStatus());
                pstmt.setDate(4, DateUtil.getSqlDate());
                pstmt.setDate(5,DateUtil.getSqlDate());

                // 增加到批处理中
                pstmt.addBatch();
                //分段提交
                if (i % maxCommit == 0) {
                    pstmt.executeBatch();
                    conn.commit();

                    // 重新开始
                    conn.setAutoCommit(false);// 关闭自动提交
                    pstmt = conn.prepareStatement(sql);
                }
            }

            // 执行
            pstmt.executeBatch();
            //提交事务
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release();
        }
    }
}




