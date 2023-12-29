package com.deng.study.designpattern.principle.openclose;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/28 16:42
 */
public class JavaCourse implements IOCCourse {
    private Integer id;

    private String name;

    private Double price;

    public JavaCourse(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getPrice() {
        return this.price;
    }
}
