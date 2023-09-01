package com.deng.jta.atomikos.xa.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Desc: 数据库db2的配置信息
 * @Auther: dengyanliang
 * @Date: 2023/8/30 20:49
 */
@Component
@ConfigurationProperties(prefix = "mysql.datasources.db2")
@Data
public class DB2Properties {

    private String type;

    private String driverClassName;

    private String url;

    private String username;

    private String password;
}
