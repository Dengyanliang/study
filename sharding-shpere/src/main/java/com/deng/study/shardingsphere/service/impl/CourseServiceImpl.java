package com.deng.study.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.study.shardingsphere.dao.mapper.CourseMapper;
import com.deng.study.shardingsphere.dao.po.Course;
import com.deng.study.shardingsphere.service.CourseService;
import com.deng.study.shardingsphere.service.thread.BatchThread;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
//    @Transactional
//    @ShardingTransactionType(TransactionType.XA)
    public void addCourse(Course course) {
        courseMapper.insert(course);
    }

    @Override
    public int batchAdd(List<Course> courseList) {

        BatchThread batchThread = new BatchThread(courseMapper,courseList);
        threadPoolExecutor.execute(batchThread);

        return 0;
    }

    @Override
    public Course getCourse(QueryWrapper<Course> queryWrapper ) {
        return courseMapper.selectOne(queryWrapper);
    }
}




