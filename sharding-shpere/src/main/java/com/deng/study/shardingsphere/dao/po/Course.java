package com.deng.study.shardingsphere.dao.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
//@TableName("t_course")
public class Course implements Serializable {
    /**
     * type = IdType.NONE 数据库未设置主键类型（当手动设置主键id值后插入数据库，值会按设置的值插入，
     * 若未手动设置值，值会变成一串很长的数字插入）
     */
    @TableId(value = "course_id", type = IdType.NONE)
    private Long courseId;

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