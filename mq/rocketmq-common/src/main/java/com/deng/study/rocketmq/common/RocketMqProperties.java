package com.deng.study.rocketmq.common;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Desc: 配置信息
 * @Date: 2024/4/3 19:47
 * @Auther: dengyanliang
 */
@Data
@ConfigurationProperties(prefix = RocketMqConstant.ROCKET_MQ_CONFIG_PREFIX)
public class RocketMqProperties {

    // 地址
    private String nameServer;
    // accessKey
    private String accessKey;
    // secretKey
    private String secretKey;
    // 分组配置
    private List<RocketMqGroupParam> groupList;

    public boolean checkConfig(){
        if(StringUtils.isAnyBlank(nameServer,accessKey,secretKey)){
            return false;
        }
        return true;
    }

    @Data
    public static class RocketMqGroupParam{
        // 组名
        private String groupName;
        // 消费者的配置
        private List<RocketMqConsumerParam> consumerList;

        public boolean checkConfig(){
            if(StringUtils.isBlank(groupName) || CollectionUtils.isEmpty(consumerList)){
                return false;
            }
            return true;
        }
    }

    @Data
    public static class RocketMqConsumerParam{
        // 自定义的消费者监听器
        private String consumerListener;
        // 监听的topic
        private String topic;
        // 过滤的tag，如果没有配置，取默认值*则表示不过滤
        private String selectorExpression = "*";


        public boolean checkConfig() {
            if (StringUtils.isAnyBlank(consumerListener, topic)) {
                return false;
            }
            return true;
        }
    }

}
