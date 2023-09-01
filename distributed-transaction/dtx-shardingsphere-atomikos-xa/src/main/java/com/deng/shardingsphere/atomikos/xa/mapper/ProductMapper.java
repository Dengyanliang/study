package com.deng.shardingsphere.atomikos.xa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.shardingsphere.atomikos.xa.entity.Product;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductMapper extends BaseMapper<Product> {

}