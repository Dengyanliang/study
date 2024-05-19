package com.deng.seata.tcc.storage.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.seata.tcc.storage.dao.po.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}