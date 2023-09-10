package com.deng.hmily.tcc.storage.tx.dao.mapper;

import com.deng.hmily.tcc.storage.tx.dao.po.Product;
import com.deng.hmily.tcc.storage.tx.dao.po.ProductExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    long countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 增加冻结库存 --- try调用
     * @param id
     * @param count
     * @return
     */
    int addFreezeStockAndCheckStockById(@Param("id") long id,@Param("count") long count);


    /**
     * 扣减冻结库存、扣减库存--- confirm调用
     * @param id
     * @param count
     * @return
     */
    int deductStockAndFreezeStockById(@Param("id") long id, @Param("count") long count);

    /**
     * 扣减冻结库存 --- cancel调用
     * @param id
     * @param count
     * @return
     */
    int deductFreezeStockById(@Param("id") long id, @Param("count") long count);
}