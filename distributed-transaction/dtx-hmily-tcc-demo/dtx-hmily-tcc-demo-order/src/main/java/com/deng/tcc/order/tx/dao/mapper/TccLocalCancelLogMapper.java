package com.deng.tcc.order.tx.dao.mapper;

import com.deng.tcc.order.tx.dao.po.TccLocalCancelLog;
import com.deng.tcc.order.tx.dao.po.TccLocalCancelLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TccLocalCancelLogMapper {
    long countByExample(TccLocalCancelLogExample example);

    int deleteByExample(TccLocalCancelLogExample example);

    int deleteByPrimaryKey(String txNo);

    int insert(TccLocalCancelLog record);

    int insertSelective(TccLocalCancelLog record);

    List<TccLocalCancelLog> selectByExample(TccLocalCancelLogExample example);

    TccLocalCancelLog selectByPrimaryKey(String txNo);

    int updateByExampleSelective(@Param("record") TccLocalCancelLog record, @Param("example") TccLocalCancelLogExample example);

    int updateByExample(@Param("record") TccLocalCancelLog record, @Param("example") TccLocalCancelLogExample example);

    int updateByPrimaryKeySelective(TccLocalCancelLog record);

    int updateByPrimaryKey(TccLocalCancelLog record);
}