package com.deng.study.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Desc: 自定义消息测试
 * @Auther: dengyanliang
 * @Date: 2023/2/9 17:42
 */
@Slf4j
public class MyMessageTest {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new MyMessageCodecShareable(),
                new LoggingHandler()
        );

        // 测试序列化
        LoginRequestMessage message = new LoginRequestMessage("zhangsan","123","张三");
//        channel.writeOutbound(message);  // 调用序列化  将对象转化为字节数组，所以直接传入message对象即可

        ByteBuf byteBuf = MyMessageCodecShareable.objectToBytes(message);
        // 测试反序列化
        channel.writeInbound(byteBuf); // 调用反序列化  将字节数组转化为对象，所以需要传入字节数组
    }

    /**
     * 测试半包粘包问题
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                // lengthFieldOffset=12 是因为内容字段长度之前是12个字节
                // lengthFieldLength=4  是因为内容字段长度是4个字节
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
                new MyMessageCodecShareable()
        );
        LoginRequestMessage message = new LoginRequestMessage("zhangsan","123","张三");
//        channel.writeOutbound(message) ;

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        // 把message数据进行编码，写入到buf中
        new MyMessageCodec().encode(null, message, buf);

        int i = buf.readableBytes();
        log.debug("buf.readableBytes：{}",i);

        ByteBuf b1 = buf.slice(0,100);
        ByteBuf b2 = buf.slice(100,buf.readableBytes()-100);
        b1.retain();   // 不加这一行会报错

        channel.writeInbound(b1);
        channel.writeInbound(b2);
    }
}
