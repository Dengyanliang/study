package com.deng.study.mybatis.plus.dynamic.datasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.study.mybatis.plus.dynamic.datasource.entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {


    int insert(PayOrder record);

    @Select("select * from pay_order where id = #{id}")
    PayOrder selectByPrimaryKey(Long id);

}