package com.deng.study.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Desc: 秒杀信息
 * @Auther: dengyanliang
 * @Date: 2023/3/5 17:58
 */
@Data
public class SeckillVoucher {
    private Long id;
    private Long stock;
    private Date beginTime;
    private Date endTime;
}
