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
    Product findProductByIdWithBloomFilter(Long productId);

    Product findProductById(Long productId);

    /**
     * 更新商品
     * @param product
     * @return
     */
    boolean updateProduct(Product product);
}
