package com.deng.study.designpattern.principle.compositionaggregation;

/**
 * @Desc:
 * @Date: 2023/12/29 12:59
 * @Auther: dengyanliang
 */
public class SqlServerConnection extends DBConnection{
    @Override
    public String getConnection() {
        return "SqlServer数据库连接";
    }
}
