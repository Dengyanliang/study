package com.deng.jta.bitronix.xa.mapper.db2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.jta.bitronix.xa.entity.Product;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}