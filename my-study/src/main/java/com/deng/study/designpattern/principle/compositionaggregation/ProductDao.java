package com.deng.study.designpattern.principle.compositionaggregation;

import lombok.Data;

/**
 * @Desc:
 * @Date: 2023/12/29 12:56
 * @Auther: dengyanliang
 */
@Data
public class ProductDao {
    private DBConnection dbConnection;

    public void addProduct(){
        String conn = dbConnection.getConnection();
        System.out.println("使用"+conn+"增加产品");
    }
}
