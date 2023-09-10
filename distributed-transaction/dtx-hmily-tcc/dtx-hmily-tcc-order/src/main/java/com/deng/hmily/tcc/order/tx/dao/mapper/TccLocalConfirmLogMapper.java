package com.deng.hmily.tcc.order.tx.dao.mapper;

import com.deng.hmily.tcc.order.tx.dao.po.TccLocalConfirmLog;
import com.deng.hmily.tcc.order.tx.dao.po.TccLocalConfirmLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TccLocalConfirmLogMapper {
    long countByExample(TccLocalConfirmLogExample example);

    int deleteByExample(TccLocalConfirmLogExample example);

    int deleteByPrimaryKey(String txNo);

    int insert(TccLocalConfirmLog record);

    int insertSelective(TccLocalConfirmLog record);

    List<TccLocalConfirmLog> selectByExample(TccLocalConfirmLogExample example);

    TccLocalConfirmLog selectByPrimaryKey(String txNo);

    int updateByExampleSelective(@Param("record") TccLocalConfirmLog record, @Param("example") TccLocalConfirmLogExample example);

    int updateByExample(@Param("record") TccLocalConfirmLog record, @Param("example") TccLocalConfirmLogExample example);

    int updateByPrimaryKeySelective(TccLocalConfirmLog record);

    int updateByPrimaryKey(TccLocalConfirmLog record);
}