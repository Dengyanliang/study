package com.deng.study.io.netty;

import com.deng.study.common.configuration.SerializerConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Desc: 必须和LengthFieldBasedFrameDecoder 一起使用，确保接到的 ByteBuf 消息是完整的
 * @Auther: dengyanliang
 * @Date: 2023/2/9 19:59
 */
@Slf4j
@ChannelHandler.Sharable // 标注了Sharable注解是，都是线程安全的  有状态的不能加Shareable，无状态的可以加Shareable
public class MyMessageCodecShareable extends MessageToMessageCodec<ByteBuf,MyMessage> {


    /**
     * 序列化
     * @param channelHandlerContext
     * @param msg
     * @param outList
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyMessage msg, List<Object> outList) throws Exception {
        ByteBuf byteBuf = objectToBytes(msg);
        outList.add(byteBuf);
    }

    public static ByteBuf objectToBytes(MyMessage msg) throws Exception{
        MySerializer.Algorithm algorithm = SerializerConfig.getAlgorithm();  // 获取配置的序列化方式

        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();

        log.debug("MyMessageCodec...encode  msg：{}",msg);
        // 1、4个字节的魔术：用来在第一时间判定是否是无效数据包
        out.writeBytes(new byte[]{1,2,3,4});

        // 2、1个字节的版本：可以支持协议的升级
        out.writeByte(1);

        // 3、1个字节的序列化方式：jdk-0,json-1  消息正文到底采用哪种序列化方式，可以由此扩展
        out.writeByte(algorithm.getCode());

        // 4、1个字节的指令类型：比如登陆，注册，单聊，群聊等业务
        out.writeByte(msg.getMessageType());

        // 5、4个字节的序列化id：为了双工通信，提供异步的能力
        out.writeInt(msg.getSequenceId());

        // 无意义的数据，仅仅是为了对齐，填充用的。总的字节数加起来= 16 = 2^4
        out.writeByte(0xff);

        // 6、读取内容的字节数组
        // 原始写法，使用jdk输出流的方式进行序列化
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(bos);
//        oos.writeObject(msg);
//        byte[] bytes = bos.toByteArray();

        // 使用自定义的算法进行序列化
        byte[] bytes = algorithm.serialize(msg);
        log.debug("bytes length：{}",bytes.length);

        // 7、4个字节的长度：正文长度
        out.writeInt(bytes.length);

        // 8、1个字节的写入内容：消息正文
        out.writeBytes(bytes);

        return out;
    }

    /**
     * 反序列化
     * @param channelHandlerContext
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        log.debug("MyMessageCodec...decode：{}",in);

        MySerializer.Algorithm algorithm = SerializerConfig.getAlgorithm();  // 获取配置的序列化方式

        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        byte no = in.readByte();
        int length = in.readInt();
        log.debug("MyMessageCodec...decode length：{}",length);

        byte[] bytes = new byte[length];
        in.readBytes(bytes,0,length);

        // 使用原始的jdk的方式进行发序列化
//        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
//        MyMessage myMessage = (MyMessage) ois.readObject();

        log.debug("进行反序列之前。。。。。");
        // 使用自定义的算法进行反序列化
        LoginRequestMessage myMessage = algorithm.deserialize(LoginRequestMessage.class, bytes);

        log.debug("{},{},{},{},{},{}",magicNum,version,serializerType,messageType,sequenceId,length);
        log.debug("myMessage：{}",myMessage);

        out.add(myMessage);
    }
}
