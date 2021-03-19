package com.deng.study.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 20:23
 */
public class JdbcUtils {

    static final String name = "com.mysql.jdbc.Driver";
    static final String url = "jdbc:mysql://127.0.0.1:3306/deng?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    static final String user = "root";
    static final String password = "root123456";

    static Connection conn = null;
    static PreparedStatement pstmt = null;

    public static Connection getConnection()  {
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void release(){
        try {
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
