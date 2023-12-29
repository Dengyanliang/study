package com.deng.study.designpattern.principle.openclose;

/**
 * @Desc: 这里使用了开闭原则，对原来方法的修改进行关闭，对新增方法进行开放
 * @Auther: dengyanliang
 * @Date: 2023/12/28 16:59
 */
public class JavaDiscountCourse extends JavaCourse {

    public JavaDiscountCourse(Integer id, String name, Double price) {
        super(id, name, price);
    }

    /**
     * @return 原价
     */
    public Double getOriginPrice(){
        return super.getPrice();
    }

    /**
     * @return 折扣价
     */
    @Override
    public Double getPrice() {
        return super.getPrice() * 0.8;
    }
}
