package com.deng.study.rpc.loadbalance;

import lombok.Data;

/**
 * @Desc:
 * @Date: 2024/4/25 22:54
 * @Auther: dengyanliang
 */
@Data
public class ServerNode {
    private String ip; // 服务ip
    private Integer weight; // 权重
    private Integer currentWeight; // 当前权重

    public ServerNode(String ip, Integer weight) {
        this.ip = ip;
        this.weight = weight;
    }
}
