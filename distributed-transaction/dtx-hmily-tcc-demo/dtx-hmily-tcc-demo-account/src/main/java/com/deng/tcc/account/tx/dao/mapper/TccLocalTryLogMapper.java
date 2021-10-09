package com.deng.tcc.account.tx.dao.mapper;

import com.deng.tcc.account.tx.dao.po.TccLocalTryLog;
import com.deng.tcc.account.tx.dao.po.TccLocalTryLogExample;
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