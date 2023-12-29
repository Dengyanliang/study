package com.deng.study.design_pattern.principle.compositionaggregation;

/**
 * @Desc:
 * @Date: 2023/12/29 12:56
 * @Auther: dengyanliang
 */
public class ProductDao {
    private DBConnection dbConnection;

    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void addProduct(){
        String conn = dbConnection.getConnection();
        System.out.println("使用"+conn+"增加产品");
    }
}
