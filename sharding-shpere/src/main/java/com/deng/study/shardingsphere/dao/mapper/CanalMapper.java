package com.deng.study.shardingsphere.dao.mapper;

import org.apache.ibatis.annotations.*;

/**
 * @Desc: canal操作数据库，这里直接操作的是sql语句
 * @Auther: dengyanliang
 * @Date: 2023/8/26 19:37
 */
@Mapper
public interface CanalMapper {

    @Insert("${insertSQL}")
    int insert(@Param("insertSQL") String insertSQL);

    @Insert("${updateSQL}")
    int update(@Param("updateSQL") String updateSQL);

    @Insert("${deleteSQL}")
    int delete(@Param("deleteSQL") String deleteSQL);
}
