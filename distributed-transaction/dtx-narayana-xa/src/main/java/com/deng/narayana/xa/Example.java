package com.deng.narayana.xa;

import com.arjuna.ats.internal.jdbc.ConnectionManager;
import com.arjuna.ats.jdbc.TransactionalDriver;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import javax.transaction.TransactionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 00:09
 */
public class Example {

    private TransactionManager transactionManager;

    private MysqlXADataSource dataSource1;

    private MysqlXADataSource dataSource2;

    public Example() {
        initDataSourceBean();
        transactionManager = com.arjuna.ats.jta.TransactionManager.transactionManager();
    }

    private void initDataSourceBean() {
        dataSource1 = new MysqlXADataSource();
        dataSource1.setURL("jdbc:mysql://localhost:3306/deng?rewriteBatchedStatements=true&useUnicode=true&serverTimezone=Asia/Shanghai");
        dataSource1.setUser("root");
        dataSource1.setPassword("root123456");

        dataSource2 = new MysqlXADataSource();
        dataSource2.setURL("jdbc:mysql://localhost:3306/hu?rewriteBatchedStatements=true&useUnicode=true&serverTimezone=Asia/Shanghai");
        dataSource2.setUser("root");
        dataSource2.setPassword("root123456");
    }

    public void test(){
        try {
            transactionManager.setTransactionTimeout(60);
            transactionManager.begin();
            Properties conn1Prop = new Properties();
            conn1Prop.put(TransactionalDriver.XADataSource,dataSource1);

            Properties conn2Prop = new Properties();
            conn2Prop.put(TransactionalDriver.XADataSource,dataSource2);

            try(Connection conn1 = ConnectionManager.create(null,conn1Prop);
                Connection conn2 = ConnectionManager.create(null,conn2Prop)) {
                try (PreparedStatement pst1 = conn1.prepareStatement("update pay_order set product_id = 2 where id = 1")){
                    pst1.executeUpdate();
                }
                // 如果没有TM，也就是如果没有事务管理器，那么这两个SQL语句的执行就不会保证原子性
//               int i = 10 / 0 ;
                try(PreparedStatement pst2 = conn2.prepareStatement("update product set count = 150 where id = 1;")){
                    pst2.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                transactionManager.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Example example = new Example();
        example.test();
    }
}
