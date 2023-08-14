package com.deng.study.shardingsphere.service.thread;

import com.deng.study.shardingsphere.dao.mapper.CourseMapper;
import com.deng.study.shardingsphere.dao.po.Course;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/13 19:25
 */
public class BatchThread implements Runnable{

    private CourseMapper courseMapper;
    private List<Course> courseList;

    public BatchThread(CourseMapper courseMapper, List<Course> courseList) {
        this.courseMapper = courseMapper;
        this.courseList = courseList;
    }

    @Override
    public void run() {
        System.out.println("开始添加....");
        if(CollectionUtils.isNotEmpty(courseList)){
            courseMapper.insertBatchSomeColumn(courseList);
        }
        System.out.println("结束添加....添加总数为："  + courseList.size());
    }
}
