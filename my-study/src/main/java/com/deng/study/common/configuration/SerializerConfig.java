package com.deng.study.common.configuration;


import com.deng.study.netty.MySerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Desc: 获取序列化配置
 * @Auther: dengyanliang
 * @Date: 2023/2/10 10:53
 */
public abstract class SerializerConfig {
    static Properties properties;
    static {
        try (InputStream in = SerializerConfig.class.getResourceAsStream("/application.properties")) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static MySerializer.Algorithm getAlgorithm(){
        String type = properties.getProperty("my.serializer.algorithm");
        if(StringUtils.isBlank(type)){
            return MySerializer.Algorithm.JAVA;
        }else{
            return MySerializer.Algorithm.valueOf(type);
        }
    }
}
