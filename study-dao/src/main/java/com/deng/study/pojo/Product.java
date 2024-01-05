package com.deng.study.pojo;

import lombok.Data;

@Data
public class Product {
    /**
     * 
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品金额，单位：分
     */
    private Long price;

}