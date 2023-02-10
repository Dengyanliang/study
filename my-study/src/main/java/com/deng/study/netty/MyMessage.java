package com.deng.study.netty;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc: 自定义消息
 * @Auther: dengyanliang
 * @Date: 2023/2/9 17:18
 */
@Data
public abstract class MyMessage implements Serializable {

    public static Class<?> getMessageType(int messageType){
        return messageClasses.get(messageType);
    }

    private int sequenceId;
    private int messageType;
    public abstract int getMessageType();

    public static final int LOGIN_REQUEST_MESSAGE = 100;
    public static final int RPC_REQUEST_MESSAGE = 101;
    public static final int RPC_RESPONSE_MESSAGE = 102;

    private static volatile Map<Integer,Class<?>> messageClasses = new ConcurrentHashMap<>();

    static {
        messageClasses.put(LOGIN_REQUEST_MESSAGE, LoginRequestMessage.class);
        messageClasses.put(RPC_REQUEST_MESSAGE, RpcRequestMessage.class);
        messageClasses.put(RPC_RESPONSE_MESSAGE, RpcResponseMessage.class);
    }
}
