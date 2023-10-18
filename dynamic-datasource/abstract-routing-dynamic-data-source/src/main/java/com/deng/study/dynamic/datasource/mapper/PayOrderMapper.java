package com.deng.study.dynamic.datasource.mapper;

import com.deng.study.dynamic.datasource.entity.PayOrder;
import org.apache.ibatis.annotations.Param;

//@Mapper
public interface PayOrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PayOrder record);

//    @Select("select * from pay_order where id = #{id}")
    PayOrder selectByPrimaryKey(Long id);

    PayOrder selectByPrimaryKeyAndProductId(@Param("id") Long id,@Param("productId") Long productId);

    int updateByPrimaryKeySelective(PayOrder record);
}