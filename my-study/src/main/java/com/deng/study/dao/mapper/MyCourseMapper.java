package com.deng.study.dao.mapper;

import com.deng.study.dao.po.MyCourse;
import com.deng.study.dao.po.MyCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyCourseMapper {
    long countByExample(MyCourseExample example);

    int deleteByExample(MyCourseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MyCourse record);

    int insertSelective(MyCourse record);

    List<MyCourse> selectByExample(MyCourseExample example);

    MyCourse selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MyCourse record, @Param("example") MyCourseExample example);

    int updateByExample(@Param("record") MyCourse record, @Param("example") MyCourseExample example);

    int updateByPrimaryKeySelective(MyCourse record);

    int updateByPrimaryKey(MyCourse record);
}