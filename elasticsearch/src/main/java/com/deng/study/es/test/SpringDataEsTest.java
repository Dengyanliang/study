package com.deng.study.es.test;

import com.deng.study.es.dao.ProductDao;
import com.deng.study.es.domain.ProductDomain;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/9 23:14
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataEsTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ProductDao productDao;

    @Test
    public void createIndex(){
        System.out.println("创建索引");
    }

    @Test
    public void deleteIndex(){
        boolean flag = elasticsearchRestTemplate.deleteIndex(ProductDomain.class);
        System.out.println("删除索引：" + flag);

    }

    @Test
    public void save(){
        ProductDomain productDomain = new ProductDomain();
        productDomain.setId(2L);
        productDomain.setTitle("华为手机");
        productDomain.setCategory("手机");
        productDomain.setPrice(2999.00);
        productDomain.setImages("http://localhost:8081/image.jpg");
        productDao.save(productDomain);
    }


    @Test
    public void update(){
        ProductDomain productDomain = new ProductDomain();
        productDomain.setId(1L);
        productDomain.setTitle("苹果手机");
        productDomain.setCategory("手机");
        productDomain.setPrice(4999.00);
        productDomain.setImages("http://localhost:8081/image.jpg");

        productDao.save(productDomain);
    }

    @Test
    public void findById(){
        ProductDomain productDomain = productDao.findById(1L).get();
        System.out.println(productDomain);
    }

    @Test
    public void findAll(){
        Iterable<ProductDomain> products = productDao.findAll();
        for (ProductDomain productDomain : products) {
            System.out.println(productDomain);
        }
    }

    @Test
    public void saveAll(){
        List<ProductDomain> productDomains = new ArrayList();
        for (int i = 0; i < 10; i++) {
            ProductDomain productDomain = new ProductDomain();
            productDomain.setId(Long.valueOf(i));
            productDomain.setTitle("小米手机-"+ (i+1));
            productDomain.setCategory("小米");
            productDomain.setPrice(1999.00 + i);
            productDomain.setImages("http://www.baidu.com");

            productDomains.add(productDomain);
        }
        productDao.saveAll(productDomains);
    }

    @Test
    public void findByPage(){
        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        int currentPage = 0;
        int pageSize = 5;

        PageRequest pageRequest = PageRequest.of(currentPage,pageSize,sort);

        Page<ProductDomain> productPage = productDao.findAll(pageRequest);
        for (ProductDomain productDomain : productPage) {
            System.out.println(productDomain);
        }
    }

    @Test
    public void termQuery(){
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("category","小米");
        Iterable<ProductDomain> products = productDao.search(queryBuilder);
        for (ProductDomain productDomain : products) {
            System.out.println("------" + productDomain);
        }

        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("title", "小米");
        Iterable<ProductDomain> products2 = productDao.search(fuzzyQueryBuilder);
        for (ProductDomain productDomain : products2) {
            System.out.println("========" + productDomain);
        }
    }

    @Test
    public void termQueryByPage(){
        int currentPage = 0;
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(currentPage,pageSize);
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery("category", "小米");

        Page<ProductDomain> products = productDao.search(queryBuilder, pageRequest);
        for (ProductDomain productDomain : products) {
            System.out.println("========" + productDomain);
        }
    }


}
