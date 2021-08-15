package com.deng.study.dao.mapper;


import com.deng.study.dao.po.Course;
import com.deng.study.dao.po.CourseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/14 20:32
 */
public interface CourseMapper{
    long countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}
