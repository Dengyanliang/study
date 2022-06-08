package com.deng.study.spring.ioc;

import org.springframework.stereotype.Service;

@Service(value = "service2")
public class ServiceImpl2 implements IService {
    @Override
    public void test() {
        System.out.println("ServiceTestImpl2 test");
    }
}
