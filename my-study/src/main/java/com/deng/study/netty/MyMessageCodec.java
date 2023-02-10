package com.deng.study.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @Desc: 自定义消息的编解码器
 * @Auther: dengyanliang
 * @Date: 2023/2/9 17:22
 */
@Slf4j
//@ChannelHandler.Sharable   ByteToMessageCodec的子类是不能添加 @Sharable 注解的
public class MyMessageCodec extends ByteToMessageCodec<MyMessage> {

    /**
     * 编码部分
     * @param channelHandlerContext
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MyMessage msg, ByteBuf out) throws Exception {
        log.debug("MyMessageCodec...encode  msg：{}",msg);
        // 1、4个字节的魔术：用来在第一时间判定是否是无效数据包
        out.writeBytes(new byte[]{1,2,3,4});

        // 2、1个字节的版本：可以支持协议的升级
        out.writeByte(1);

        // 3、1个字节的序列化方式：jdk-0,json-1  消息正文到底采用哪种序列化方式，可以由此扩展
        out.writeByte(0);

        // 4、1个字节的指令类型：比如登陆，注册，单聊，群聊等业务
        out.writeByte(msg.getMessageType());

        // 5、4个字节的序列化id：为了双工通信，提供异步的能力
        out.writeInt(msg.getSequenceId());

        // 无意义的数据，仅仅是为了对齐，填充用的。总的字节数加起来= 16 = 2^4
        out.writeByte(0xff);

        // 6、读取内容的字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        log.debug("bytes length：{}",bytes.length);

        // 7、4个字节的长度：正文长度
        out.writeInt(bytes.length);

        // 8、1个字节的写入内容：消息正文
        out.writeBytes(bytes);
    }

    /**
     * 解码部分 跟编码部分是逆过程
     * @param channelHandlerContext
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        log.debug("MyMessageCodec...decode：{}",in);

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

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        MyMessage myMessage = (MyMessage) ois.readObject();

        log.debug("{},{},{},{},{},{}",magicNum,version,serializerType,messageType,sequenceId,length);
        log.debug("myMessage：{}",myMessage);

        out.add(myMessage);
    }
}
