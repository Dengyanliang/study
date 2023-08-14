package com.deng.study.dao;

import com.deng.study.dao.mapper.MyCourseMapper;
import com.deng.study.dao.mapper.PayOrderMapper;
import com.deng.study.dao.po.MyCourse;
import com.deng.study.dao.po.PayOrder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:05
 */
@Component
public class MyCourseDaoImpl implements MyCourseDao{

    @Resource
    MyCourseMapper myCourseMapper;

    @Override
    public void addMyCourse(MyCourse myCourse) {
        myCourseMapper.insert(myCourse);
    }

    @Override
    public void addBatchMyCourseByThread(List<MyCourse> myCourseList) {

    }

    @Override
    public void addBatchMyCourseByPreparedStatement(List<MyCourse> myCourseList) {

    }
}
