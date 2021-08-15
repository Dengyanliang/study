package com.deng.study.shardingsphere.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.study.shardingsphere.po.Course;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface CourseService {
    void addCourse(Course course);

    Course getCourse(QueryWrapper<Course> queryWrapper );
}
