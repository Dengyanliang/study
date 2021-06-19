package com.deng.study.rpc.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Desc: 解码器
 * @Auther: dengyanliang
 * @Date: 2021/6/18 21:34
 */
public class NettyRpcEncoder extends MessageToByteEncoder {

    private Class<?> target; // 对目标对象进行解码

    public NettyRpcEncoder(Class<?> target) {
        this.target = target;
    }

    @Override
    protected void encode(ChannelHandlerContext context, Object msg, ByteBuf out) throws Exception {
        if(target.isInstance(msg)){
            byte[] data = JSON.toJSONBytes(msg); // 使用fastjson将对象转化为byte
            out.writeInt(data.length); // 先将消息长度写入，也就是消息头
            out.writeBytes(data);  // 消息体中包含要发送的数据
        }
    }
}
