package com.deng.study.design_pattern.principle.compositionaggregation;

/**
 * @Desc:
 * @Date: 2023/12/29 12:59
 * @Auther: dengyanliang
 */
public class MySqlConnection extends DBConnection{
    @Override
    public String getConnection() {
        return "MySql数据库连接";
    }
}
