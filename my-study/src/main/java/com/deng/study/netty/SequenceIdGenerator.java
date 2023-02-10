package com.deng.study.netty;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Desc: 获取序列id
 * @Auther: dengyanliang
 * @Date: 2023/2/10 23:03
 */
public abstract class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();

    public static int getNext(){
        return id.incrementAndGet();
    }
}
