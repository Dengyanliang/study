package com.deng.study.java.thread.pool;

/**
 * @Desc: 拒绝策略
 * @Auther: dengyanliang
 * @Date: 2023/2/16 16:47
 */
@FunctionalInterface
public interface RejectPolicy<T> {
    void reject(BlockQueue<T> queue,T task);
}
