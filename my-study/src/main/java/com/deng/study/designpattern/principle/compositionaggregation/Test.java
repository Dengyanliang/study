package com.deng.study.designpattern.principle.compositionaggregation;

/**
 * @Desc: 合成复用原则
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
