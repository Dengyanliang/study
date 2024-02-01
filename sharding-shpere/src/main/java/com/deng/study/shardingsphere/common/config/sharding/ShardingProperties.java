package com.deng.study.shardingsphere.common.config.sharding;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
//@Configuration
@ConfigurationProperties(prefix = "sharding.datasource")
public class ShardingProperties {

    private String url;
    private String username;
    private String password;

    private String url0;
    private String username0;
    private String password0;

    private String url1;
    private String username1;
    private String password1;

}
