package com.deng.study.shardsphere.read.write.split.mapper;


import com.deng.study.shardsphere.read.write.split.entity.PayOrder;

//@Mapper
public interface PayOrderMapper {

    int insert(PayOrder record);

    PayOrder selectByPrimaryKey(Long id);

}