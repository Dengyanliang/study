package com.deng.study.rpc.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @Desc:解码器
 * @Auther: dengyanliang
 * @Date: 2021/6/18 21:27
 */
public class NettyRpcDecoder extends ByteToMessageDecoder {

    // 目标对象类型解码
    private Class<?> target;

    public NettyRpcDecoder(Class<?> target) {
        this.target = target;
    }

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4){ // 不够长度则丢弃
            return;
        }
        in.markReaderIndex();  // 标记一下当前的readIndex的位置
        int dataLength = in.readInt(); // 读取传送过来的消息长度。该方法会让readIndex增加4
        // 如果读到的消息体长度小于传递过来的消息长度，则调用resetReaderIndex重置。该方法配合markReaderIndex使用，把readIndex重置到mark的地方
        if(in.readableBytes() < dataLength){
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object object = JSON.parseObject(data, target); // 把byte数据转化为对象
        out.add(object);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {

    }
}
