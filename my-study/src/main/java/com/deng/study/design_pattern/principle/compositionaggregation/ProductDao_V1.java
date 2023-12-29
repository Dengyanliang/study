package com.deng.study.design_pattern.principle.compositionaggregation;

/**
 * @Desc:
 * @Date: 2023/12/29 12:56
 * @Auther: dengyanliang
 */
public class ProductDao_V1 extends DBConnection_V1{
    public void addProduct(){
        String conn = super.getConnection();
        System.out.println("使用"+conn+"增加产品");
    }
}
