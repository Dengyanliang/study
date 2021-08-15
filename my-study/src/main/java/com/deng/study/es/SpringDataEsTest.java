package com.deng.study.es;

import com.deng.study.dao.ProductDao;
import com.deng.study.domain.Product;
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
        boolean flag = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("删除索引：" + flag);

    }

    @Test
    public void save(){
        Product product = new Product();
        product.setId(2L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.00);
        product.setImages("http://localhost:8081/image.jpg");
        productDao.save(product);
    }


    @Test
    public void update(){
        Product product = new Product();
        product.setId(1L);
        product.setTitle("苹果手机");
        product.setCategory("手机");
        product.setPrice(4999.00);
        product.setImages("http://localhost:8081/image.jpg");

        productDao.save(product);
    }

    @Test
    public void findById(){
        Product product = productDao.findById(1L).get();
        System.out.println(product);
    }

    @Test
    public void findAll(){
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    public void saveAll(){
        List<Product> products = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(Long.valueOf(i));
            product.setTitle("小米手机-"+ (i+1));
            product.setCategory("小米");
            product.setPrice(1999.00 + i);
            product.setImages("http://www.baidu.com");

            products.add(product);
        }
        productDao.saveAll(products);
    }

    @Test
    public void findByPage(){
        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        int currentPage = 0;
        int pageSize = 5;

        PageRequest pageRequest = PageRequest.of(currentPage,pageSize,sort);

        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product product : productPage) {
            System.out.println(product);
        }
    }

    @Test
    public void termQuery(){
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("category","小米");
        Iterable<Product> products = productDao.search(queryBuilder);
        for (Product product : products) {
            System.out.println("------" + product);
        }

        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("title", "小米");
        Iterable<Product> products2 = productDao.search(fuzzyQueryBuilder);
        for (Product product : products2) {
            System.out.println("========" + product);
        }
    }

    @Test
    public void termQueryByPage(){
        int currentPage = 0;
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(currentPage,pageSize);
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery("category", "小米");

        Page<Product> products = productDao.search(queryBuilder, pageRequest);
        for (Product product : products) {
            System.out.println("========" + product);
        }
    }


}
