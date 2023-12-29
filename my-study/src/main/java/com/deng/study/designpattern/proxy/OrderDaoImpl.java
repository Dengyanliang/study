package com.deng.study.designpattern.proxy;

/**
 * @Desc:
 * @Date: 2023/12/29 19:48
 * @Auther: dengyanliang
 */
public class OrderDaoImpl implements IOrderDao{
    @Override
    public int insert(Order order) {
        System.out.println("Dao 层添加数据成功");
        return 1;
    }
}
