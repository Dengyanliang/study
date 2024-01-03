package com.deng.study.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deng.common.util.DateUtil;
import com.deng.common.util.JdbcUtils;
import com.deng.study.shardingsphere.dao.mapper.MyOrderMapper;
import com.deng.study.shardingsphere.dao.po.MyOrder;
import com.deng.study.shardingsphere.service.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

//import com.deng.study.shardingsphere.util.JdbcUtils;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {

    @Autowired
    private MyOrderMapper myOrderMapper;

    @Override
    public void addMyOrder(MyOrder MyOrder) {
        myOrderMapper.insert(MyOrder);
    }

    @Override
    public MyOrder getMyOrder(QueryWrapper<MyOrder> queryWrapper ) {
        return myOrderMapper.selectOne(queryWrapper);
    }


    @Override
    public IPage<MyOrder> getMyOrderByPage(IPage<MyOrder> page, QueryWrapper<MyOrder> queryWrapper) {
        return myOrderMapper.selectPage(page,queryWrapper);
    }

    /**
     * 批量插入时，使用url中添加rewriteBatchedStatements=true该参数，可以提高插入的效率
     * 目前测试插入1千万的数据，花费15分钟左右；如果没有这个参数，则需要3个小时
     *
     * @param myOrderList
     */
    @Override
    public void addBatchMyOrderByPreparedStatement(List<MyOrder> myOrderList) {
        //事务上限，每次累计这么多条数，提交一次事务
        int maxCommit = 100000;
        try{
            Connection conn = JdbcUtils.getConnection();
            String sql = "insert into my_order (name, user_id, status, create_time, update_time) values (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 关闭自动提交，不然conn.commit()运行到这句会报错
            conn.setAutoCommit(false);

            for (int i = 0; i < myOrderList.size(); i++) {
                MyOrder MyOrder = myOrderList.get(i);
                // 设置参数
                pstmt.setString(1, MyOrder.getName());
                pstmt.setLong(2, MyOrder.getUserId());
                pstmt.setString(3, MyOrder.getStatus());

                // 设置日期，直接用string
                pstmt.setString(4, DateUtil.getDateNow());
                pstmt.setString(5, DateUtil.getDateNow());

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




