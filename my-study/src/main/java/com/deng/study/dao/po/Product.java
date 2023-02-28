package com.deng.study.dao.po;

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

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 商品名称
     * @return name 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 商品名称
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 商品金额，单位：分
     * @return price 商品金额，单位：分
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 商品金额，单位：分
     * @param price 商品金额，单位：分
     */
    public void setPrice(Long price) {
        this.price = price;
    }
}