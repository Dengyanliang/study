package com.deng.study.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**git branch --set-upstream-to=origin/master
 * @Desc: 客户端Stub
 * @Auther: dengyanliang
 * @Date: 2023/2/10 22:42
 */
@Slf4j
public class RpcClientManager {

    private static volatile Channel channel = null;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        // 手动调用
        /*
        Channel channel = getChannel();
        RpcRequestMessage message = new RpcRequestMessage(
                1,
                "com.deng.study.netty.HelloService",
                "sayHello",
                String.class,
                new Class[]{String.class},
                new Object[]{"张三"}
        );
        channel.writeAndFlush(message);*/

        // 使用代理类调用
        HelloService helloService = getProxyService(HelloService.class);
        helloService.sayHello("张三");
        helloService.sayHello("李四");
    }

    /**
     * 创建代理类
     * @param interfaceClass 接口class信息
     * @param <T>
     * @return
     */
    public static <T> T getProxyService(Class<T> interfaceClass){
        // 获取类加载器
        ClassLoader loader = interfaceClass.getClassLoader();
        // 获取接口列表
        Class<?>[] interfaces = new Class[]{interfaceClass};
        // 获取InvocationHandler
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 将方法调用转化为消息对象
                RpcRequestMessage msg = new RpcRequestMessage(
                        SequenceIdGenerator.getNext(),
                        interfaceClass.getName(),
                        method.getName(),
                        method.getReturnType(),
                        method.getParameterTypes(),
                        args
                );
                // 将消息对象发送出去
                getChannel().writeAndFlush(msg);
                // 返回结果
                return null;
            }
        };

        Object o = Proxy.newProxyInstance(loader, interfaces, handler);
        return (T)o;
    }

    public static Channel getChannel(){
        if(channel == null){
            synchronized (LOCK){
                if(channel == null){
                    initChannel();
                }
            }
        }
        return channel;
    }

    private static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MyMessageCodecShareable messageCodec = new MyMessageCodecShareable();

        RpcResponseMessageHandler responseMessageHandler = new RpcResponseMessageHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ProcotolFrameDecoder());
                    socketChannel.pipeline().addLast(loggingHandler);
                    socketChannel.pipeline().addLast(messageCodec);
                    socketChannel.pipeline().addLast(responseMessageHandler);
                }
            });
            channel = bootstrap.connect("localhost", 8088).sync().channel();

            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            log.error("client error",e);
        }
    }
}
