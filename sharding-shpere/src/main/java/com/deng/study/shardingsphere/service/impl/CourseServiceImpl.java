package com.deng.study.shardingsphere.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.study.shardingsphere.dao.mapper.CourseMapper;
import com.deng.study.shardingsphere.po.Course;
import com.deng.study.shardingsphere.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    @DS("gits_sharding")
    public void addCourse(Course course) {
        courseMapper.insert(course);
    }

    @Override
    @DS("gits_sharding")
    public Course getCourse(QueryWrapper<Course> queryWrapper ) {
        return courseMapper.selectOne(queryWrapper);
    }
}




