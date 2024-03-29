package com.deng.study.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class MyOrder {
    /**
     * 
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;
}