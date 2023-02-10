package com.deng.study.netty;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Desc: 替换掉原来的默认的基于长度字段的帧解码器
 * @Auther: dengyanliang
 * @Date: 2023/2/10 15:21
 */
public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProcotolFrameDecoder() {
        super(1024, 12, 4);
    }
}
