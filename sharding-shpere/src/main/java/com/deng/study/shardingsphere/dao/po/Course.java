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
     * 
     */
//    private Long id;
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