package com.deng.study.service;

import com.deng.study.dao.po.MyCourse;
import com.deng.study.dao.po.PayOrder;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:02
 */
public interface MyCourseService {
    void addMyCourse(MyCourse myCourse);

    void addBatchMyCourseByThread(List<MyCourse> myCourseList);

    void addBatchMyCourseByPreparedStatement(List<MyCourse> myCourseList);
}
