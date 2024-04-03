package com.deng.restroom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = VerifySignPreperties.PREFIX)
public class VerifySignPreperties {
    public static final String PREFIX = "restroom-sign";

    private Map<String ,AppSignConfig> signMap = new HashMap<>();

    public AppSignConfig getAppSignConfig(String appid){
        return signMap.get(appid);
    }

    @Data
    public static class AppSignConfig{
        private String signKey;

        //签名头参数名称
        private String signHeaderName = "sign";

        //签名有效时间(单位：毫秒)
        private int signEffectiveTime = 5*60000;

        //系统时间差(本系统与外部请求系统时间差距，单位毫秒)
        private int systemTimeOffset = 5*60000;

        //签名时间头参数名称
        private String signTimeHeaderName = "timestamp";

        //是否启用签名验证
        private Boolean enableSign = false;
    }
}
