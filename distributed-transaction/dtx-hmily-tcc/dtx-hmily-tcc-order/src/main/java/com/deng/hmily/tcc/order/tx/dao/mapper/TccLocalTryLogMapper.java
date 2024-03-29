package com.deng.hmily.tcc.order.tx.dao.mapper;

import com.deng.hmily.tcc.order.tx.dao.po.TccLocalTryLogExample;
import com.deng.hmily.tcc.order.tx.dao.po.TccLocalTryLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TccLocalTryLogMapper {
    long countByExample(TccLocalTryLogExample example);

    int deleteByExample(TccLocalTryLogExample example);

    int deleteByPrimaryKey(String txNo);

    int insert(TccLocalTryLog record);

    int insertSelective(TccLocalTryLog record);

    List<TccLocalTryLog> selectByExample(TccLocalTryLogExample example);

    TccLocalTryLog selectByPrimaryKey(String txNo);

    int updateByExampleSelective(@Param("record") TccLocalTryLog record, @Param("example") TccLocalTryLogExample example);

    int updateByExample(@Param("record") TccLocalTryLog record, @Param("example") TccLocalTryLogExample example);

    int updateByPrimaryKeySelective(TccLocalTryLog record);

    int updateByPrimaryKey(TccLocalTryLog record);
}