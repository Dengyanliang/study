package com.deng.shardingsphere.dynamic.datasource.mapper;

import com.deng.shardingsphere.dynamic.datasource.entity.PayOrder;

public interface PayOrderMapper {

    int insert(PayOrder record);

    PayOrder selectByPrimaryKey(Long id);

}