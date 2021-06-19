package com.deng.study.rpc.server;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/18 22:36
 */
public class NettyServerMain {
    public static void main(String[] args) throws Exception {
        new NettyServer().bind(8082);
    }
}
