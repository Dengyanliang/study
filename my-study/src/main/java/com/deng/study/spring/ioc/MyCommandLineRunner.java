package com.deng.study.spring.ioc;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner, DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("MyCommandLineRunner..destory");
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("MyCommandLineRunner.. run");
    }
}
