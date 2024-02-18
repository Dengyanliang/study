package com.deng.study.shardingsphere.sharding;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
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

    private String url2;
    private String username2;
    private String password2;

    private String url3;
    private String username3;
    private String password3;

    private String url4;
    private String username4;
    private String password4;

    private String url5;
    private String username5;
    private String password5;

    private String url6;
    private String username6;
    private String password6;

    private String url7;
    private String username7;
    private String password7;

    private String url8;
    private String username8;
    private String password8;

    private String url9;
    private String username9;
    private String password9;

    private String url10;
    private String username10;
    private String password10;

    private String url11;
    private String username11;
    private String password11;

    private String url12;
    private String username12;
    private String password12;

    private String url13;
    private String username13;
    private String password13;

    private String url14;
    private String username14;
    private String password14;

    private String url15;
    private String username15;
    private String password15;

}
