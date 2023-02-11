package com.deng.study.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc: rpc响应处理器
 * @Auther: dengyanliang
 * @Date: 2023/2/10 15:38
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    private static final Map<Integer, Promise<Object>> PROMISE_MAP = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponseMessage message) throws Exception {
        log.debug("服务端响应消息：{}",message);
        // 这里用remove的原因，是因为获取map中的对象后，要把该对象值删除掉，不然消息会一直存在于map中
        Promise<Object> promise = PROMISE_MAP.remove(message.getSequenceId());
        if(Objects.nonNull(promise)){
            Object returnValue = message.getReturnValue();
            Exception exceptionValue = message.getExceptionValue();
            if(Objects.nonNull(exceptionValue)){
                promise.setFailure(exceptionValue);
            }else{
                promise.setSuccess(returnValue);
            }
        }
    }

    public static void put(Integer key, Promise<Object> value){
        PROMISE_MAP.put(key,value);
    }
}
