package com.deng.study.netty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/10 14:59
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponseMessage extends MyMessage{

    // 返回值
    private Object returnValue;

    // 异常值
    private Exception exceptionValue;


    @Override
    public int getMessageType() {
        return MyMessage.RPC_RESPONSE_MESSAGE;
    }
}
