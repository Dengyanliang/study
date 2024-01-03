package com.deng.study.shardingsphere.dao.po;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MyOrder implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 课程状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


}