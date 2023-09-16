package com.deng.seata.dynamic.ds.dao.mapper;

import com.deng.seata.dynamic.ds.dao.po.Product;
import org.apache.ibatis.annotations.Param;

public interface ProductMapper {

    Product selectByPrimaryKey(Long id);

    /**
     * 扣减库存
     * @param id
     * @param count
     * @return
     */
    int deductStockById(@Param("id") long id, @Param("count") long count);
}