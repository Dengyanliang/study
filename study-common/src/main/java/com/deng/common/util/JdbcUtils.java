package com.deng.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 20:23
 */
public class JdbcUtils {

    private static final String name = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/deng?useUnicode=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "root123456";

    private static Connection conn = null;
    private static PreparedStatement pstmt = null;

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
            if(Objects.nonNull(pstmt)){
                pstmt.close();
            }
            if(Objects.nonNull(conn)){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
