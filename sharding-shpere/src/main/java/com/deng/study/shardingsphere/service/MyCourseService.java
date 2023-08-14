package com.deng.study.shardingsphere.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deng.study.shardingsphere.dao.po.MyCourse;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface MyCourseService {
    void addCourse(MyCourse myCourse);

    void addBatchMyCourseByPreparedStatement(List<MyCourse> myCourseList);

    MyCourse getCourse(QueryWrapper<MyCourse> queryWrapper);

    IPage<MyCourse> getCourseByPage(IPage<MyCourse> page, QueryWrapper<MyCourse> queryWrapper);
}
