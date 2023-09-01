package com.deng.jta.atomikos.xa.mapper.db2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.jta.atomikos.xa.entity.Product;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}