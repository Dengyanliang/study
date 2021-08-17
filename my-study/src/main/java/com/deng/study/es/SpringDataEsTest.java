package com.deng.study.es;

import com.deng.study.dao.ProductDao;
import com.deng.study.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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

}
