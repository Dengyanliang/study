package com.deng.study.redis.controller;

import com.deng.study.pojo.Product;
import com.deng.study.redis.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/28 18:46
 */
@Api(tags = "Product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("通过布隆过滤器查询Product")
    @RequestMapping(value = "/product/add",method = RequestMethod.POST)
    public void addProduct(){
        productService.add();
    }

    @ApiOperation("通过布隆过滤器查询Product")
    @RequestMapping(value = "/product/{id}",method = RequestMethod.GET)
    public Product findProductByIdWithBloomFilter(@PathVariable Long id){
        return productService.findProductByIdWithBloomFilter(id);
    }
}
