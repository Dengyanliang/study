package com.deng.atomikos.xa;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/28 09:24
 */
public class Example {

    private DataSource ds1;

    private DataSource ds2;

    private UserTransaction userTransaction;

    public Example() {
        initDataSourceBeans();
        userTransaction = new UserTransactionImp();
    }

    private void initDataSourceBeans(){
        // 设置第一个数据源的信息
        Properties properties1 = new Properties();
        properties1.setProperty("user","root");
        properties1.setProperty("password","root123456");
        properties1.setProperty("url","jdbc:mysql://localhost:3306/deng?rewriteBatchedStatements=true&useUnicode=true&&useSSL=false&serverTimezone=Asia/Shanghai");

        AtomikosDataSourceBean dataSource1 = new AtomikosDataSourceBean();
        dataSource1.setXaDataSourceClassName("org.apache.commons.dbcp2.BasicDataSource");
        dataSource1.setUniqueResourceName("ds1");
        dataSource1.setXaProperties(properties1);
        dataSource1.setPoolSize(10);

        this.ds1 = dataSource1;

        // 设置第二个数据源的信息
        Properties properties2 = new Properties();
        properties2.setProperty("user","root");
        properties1.setProperty("password","root123456");
        properties1.setProperty("url","jdbc:mysql://localhost:3306/hu?rewriteBatchedStatements=true&useUnicode=true&&useSSL=false&serverTimezone=Asia/Shanghai");

        AtomikosDataSourceBean dataSource2 = new AtomikosDataSourceBean();
        dataSource2.setXaDataSourceClassName("org.apache.commons.dbcp2.BasicDataSource");
        dataSource2.setUniqueResourceName("ds2");
        dataSource2.setXaProperties(properties2);
        dataSource2.setPoolSize(10);
        this.ds2 = dataSource2;
    }

    public void test() throws SystemException {
        // 事务超时时间为60s
       try {
           userTransaction.setTransactionTimeout(60);
           userTransaction.begin();

           try (Connection conn1 = ds1.getConnection();
                Connection conn2 = ds2.getConnection()){
               try (PreparedStatement pst1 = conn1.prepareStatement("update pay_order set product_id = 2 where id = 1")){
                   pst1.executeUpdate();
               }
               try(PreparedStatement pst2 = conn2.prepareStatement("update product set count = 150 where id = 1;")){
                   pst2.executeUpdate();
               }
               int i = 10 / 0 ;
           }
           userTransaction.commit();
       } catch (Exception e) {
           e.printStackTrace();
           userTransaction.rollback();
       }
    }



    public static void main(String[] args) {
        try {
            Example example = new Example();
            example.test();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现了异常。。。");
        }
    }
}
