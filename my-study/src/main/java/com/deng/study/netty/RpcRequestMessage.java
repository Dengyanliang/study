package com.deng.study.netty;

import lombok.Data;
import lombok.ToString;

/**
 * @Desc: rpc请求对象
 * @Auther: dengyanliang
 * @Date: 2023/2/10 14:59
 */
@Data
@ToString(callSuper = true)
public class RpcRequestMessage extends MyMessage{

    // 调用接口的全限定名，服务器根据它找到实现
    private String interfaceName;

    // 调用接口中的方法名
    private String methodName;

    // 方法返回类型
    private Class<?> methodReturnType;

    // 方法参数类型数组
    private Class[] parameterTypes;

    // 方法参数数组
    private Object[] parameterValue;

    public RpcRequestMessage(int sequenceId, String interfaceName, String methodName, Class<?> methodReturnType, Class[] parameterTypes, Object[] parameterValue) {
        super.setSequenceId(sequenceId);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.methodReturnType = methodReturnType;
        this.parameterTypes = parameterTypes;
        this.parameterValue = parameterValue;
    }

    @Override
    public int getMessageType() {
        return MyMessage.RPC_REQUEST_MESSAGE;
    }
}
