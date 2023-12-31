package com.deng.study.designpattern.creational.abstractfactory;

/**
 * @Desc:
 * @Date: 2023/12/31 15:39
 * @Auther: dengyanliang
 */
public class PythonArticle extends Article{
    @Override
    public void produce() {
        System.out.println("编写Python手记");
    }
}
