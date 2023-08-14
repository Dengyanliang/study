package com.deng.study.shardingsphere.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.study.shardingsphere.dao.po.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/14 20:32
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 批量插入
     * @param courseList 实体列表
     * @return 影响行数
     */
    Integer insertBatchSomeColumn(Collection<Course> courseList);
}
