package com.deng.study.es.dao;

import com.deng.study.es.domain.ProductDomain;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/9 23:10
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<ProductDomain,Long> {

}
