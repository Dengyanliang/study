package com.deng.study.netty;

import com.deng.study.common.configuration.ServiceFactoryConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Desc: rpc请求处理器
 * @Auther: dengyanliang
 * @Date: 2023/2/10 15:38
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage message) throws Exception {
        log.debug("服务端收到请求信息：{}",message);
        RpcResponseMessage response = new RpcResponseMessage();
        response.setSequenceId(message.getSequenceId());
        try {
            // 利用反射处理
            HelloService service = ServiceFactoryConfig.getService(Class.forName(message.getInterfaceName()));
            Method method = service.getClass().getMethod(message.getMethodName(),message.getParameterTypes());
            Object result = method.invoke(service, message.getParameterValue());
            response.setReturnValue(result);

            ctx.writeAndFlush(response);
        } catch (Exception e) {
            log.error("出现了异常",e);
            response.setExceptionValue(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RpcRequestMessage message = new RpcRequestMessage(
                1,
                "com.deng.study.netty.HelloService",
                "sayHello",
                String.class,
                new Class[]{String.class},
                new Object[]{"张三"}
        );
        HelloService service = (HelloService) ServiceFactoryConfig.getService(Class.forName(message.getInterfaceName()));
        Method method = service.getClass().getMethod(message.getMethodName(),message.getParameterTypes());
        Object result = method.invoke(service, message.getParameterValue());
        System.out.println(result);
    }
}
