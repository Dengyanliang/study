package com.deng.study.spring.ioc;

import org.springframework.stereotype.Service;

@Service(value = "service1")
public class ServiceImpl1 implements IService {
    @Override
    public void test() {
        System.out.println("ServiceTestImpl1 test");
    }
}
