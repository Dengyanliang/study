package com.deng.study.rocketmq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @Desc:
 * @Date: 2024/4/5 17:48
 * @Auther: dengyanliang
 */
@Slf4j
public abstract class AbstractBaseMessageHander <T>{

    protected void handleMessage(MessageExt messageExt){
        long start = System.currentTimeMillis();
        String body = new String(messageExt.getBody());
        if(this.getMessageTypeReference().getClass().equals(String.class)){
            this.handleMessage((T)body);
        }else{
            this.handleMessage(JSON.parseObject(body,this.getMessageTypeReference()));
        }
        long costTime = System.currentTimeMillis() - start;
        log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
    }

    /**
     * 处理业务消息，这里一个钩子方法
     * @param t
     */
    protected void handleMessage(T t){

    }

    /**
     * 消息类型，这里用了TypeReference，因为消息有可能是泛型包装的
     * @return
     */
    protected abstract TypeReference<T> getMessageTypeReference();
}
