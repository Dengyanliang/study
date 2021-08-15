package com.deng.study.shardingsphere.po;


import lombok.Data;

@Data
public class Course {
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
}