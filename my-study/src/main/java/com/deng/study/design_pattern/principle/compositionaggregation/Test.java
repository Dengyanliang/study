package com.deng.study.design_pattern.principle.compositionaggregation;

/**
 * @Desc:
 * @Date: 2023/12/29 12:58
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        productDao.setDbConnection(new MySqlConnection());
        productDao.addProduct();
    }
}
