package com.deng.study.dao;

import com.deng.study.dao.po.MyCourse;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/12 19:58
 */
public interface MyCourseDao {
    void addMyCourse(MyCourse myCourse);

    void addBatchMyCourseByThread(List<MyCourse> myCourseList);

    void addBatchMyCourseByPreparedStatement(List<MyCourse> myCourseList);
}
