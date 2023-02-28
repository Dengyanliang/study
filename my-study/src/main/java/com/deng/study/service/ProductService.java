package com.deng.study.service;

import com.deng.study.dao.po.Product;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/28 18:34
 */
public interface ProductService {

    /**
     * 添加商品
     */
    void add();

    /**
     * 通过productId查询商品
     * @param productId
     * @return
     */
    Product findProductByIdWithBloomFilter(Integer productId);
}
