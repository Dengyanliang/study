package com.deng.study.service.impl;

import com.deng.study.dao.OrderDao;
import com.deng.study.dao.po.PayOrder;
import com.deng.study.java.thread.ThreadDemo5;
import com.deng.study.service.OrderService;
import com.deng.study.util.DateUtil;
import com.deng.study.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.*;
import java.util.List;
import java.util.UUID;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Resource
    ThreadPoolTaskExecutor taskExecutor;

    @Override
    @Transactional
    public void addOrder(PayOrder payOrder) {
        orderDao.insert(payOrder);
    }

    @Override
    @Transactional
    public void addBatchOrder(List<PayOrder> orderList) {
//        orderDao.addList(orderList);
        int count = taskExecutor.getActiveCount();
        System.out.println(count+"----");

        ThreadDemo5 threadDemo5 = new ThreadDemo5(orderList,orderDao);
        taskExecutor.execute(threadDemo5);

    }

    @Override
    public void addBatchOrder2() {
        //事务上限
        int maxCommit = 10000;
        //插入数量
        int insertNumber = 1000000;
        try{
            Connection conn = JdbcUtils.getConnection();
            String sql = "insert into pay_order (user_id, product_id,order_Fee, pay_status, version, create_Time, pay_finish_time)\n" +
                    "    values (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 关闭自动提交，不然conn.commit()运行到这句会报错
            conn.setAutoCommit(false);

            for (int i = 1; i <= insertNumber; i++) {
                // 设置参数
                pstmt.setLong(1, (long)(Math.random()*10000));
                pstmt.setLong(2, (long)(Math.random()*100000));
                pstmt.setLong(3, (long)(Math.random()*100));
                pstmt.setInt(4,1);
                pstmt.setInt(5,1);
                pstmt.setDate(6, DateUtil.getSqlDate());
                pstmt.setDate(7, DateUtil.getSqlDate());

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




