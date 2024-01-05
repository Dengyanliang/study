package com.deng.study.service.impl;

import com.deng.common.util.DateUtil;
import com.deng.common.util.JdbcUtils;
import com.deng.study.common.DataSource;
import com.deng.study.java.thread.ThreadDemo5;
import com.deng.study.mapper.PayOrderMapper;
import com.deng.study.pojo.PayOrder;
import com.deng.study.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private JdbcTemplate jdbcTemplate_master;

    @Autowired
    private JdbcTemplate jdbcTemplate_slave;


    @Override
    @Transactional
    public void addOrder(PayOrder payOrder) {
//        sqlSessionFactoryBean.setMapperLocations(resource);
        payOrderMapper.insert(payOrder);
    }

    @Override
    public PayOrder getOrder(Long id) {
//        SqlSessionFactoryBean SqlSessionFactoryBean = ApplicationContext
//        sqlSessionFactoryBean.setDataSource(dataSource_slave);

//        String res = "classpath:datasource.xml";
//        org.springframework.core.io.Resource resource = new ClassPathResource(res);
//        Reader reader = null;
//        try {
//            reader = Resources.getResourceAsReader(res);
//        }catch (Exception e){
//        }
//
//        SqlSessionFactory sqlSessionFactory = new SqlSeonFactoryBuilder().build(reader);

//        TransactionFactory transactionFactory = new JdbcTransactionFactory();
//        Environment environment = new Environment("",transactionFactory,dataSource_slave);
//        Configuration configuration = new Configuration(environment);
//        configuration.addMapper(PayOrderMapper.class);
//        configuration.addMappers("com/deng/study/dao/mapper/");
//
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        PayOrderMapper payOrderMapper = sqlSession.getMapper(PayOrderMapper.class);


//        String res = "classpath:base/mapper/*Mapper.xml";
//        org.springframework.core.io.Resource resource = new ClassPathResource(res);
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setMapperLocations(resource);
//        sqlSessionFactoryBean.setDataSource(dataSource_slave);
//
//        SqlSessionFactory sqlSessionFactory = null;
//        try {
//            sqlSessionFactory_master.setDataSource(dataSource_slave);
//            sqlSessionFactory = sqlSessionFactory_master.getObject();
//        }catch (Exception e){
//
//        }
////
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        PayOrderMapper payOrderMapper = sqlSession.getMapper(PayOrderMapper.class);
//        PayOrder p = payOrderMapper.selectByPrimaryKey(id);

        String sql = "select * from pay_order where id = ?";
        PayOrder payOrder = new PayOrder();
        jdbcTemplate_slave.query(sql,new Object[]{id},new RowCallbackHandler(){
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                payOrder.setProductId(resultSet.getLong("product_id"));
            }
        });
        return payOrder;
//        return orderDao.select(id);
    }

    @Override
    @DataSource(isMaster = false)
    public PayOrder getOrder2(Long id) {
        return payOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void addBatchOrder(List<PayOrder> orderList) {
//        orderDao.addList(orderList);
        int count = taskExecutor.getActiveCount();
        System.out.println(count+"----");

        ThreadDemo5 threadDemo5 = new ThreadDemo5(orderList,payOrderMapper);
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




