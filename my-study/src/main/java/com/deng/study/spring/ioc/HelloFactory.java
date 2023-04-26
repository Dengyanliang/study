package com.deng.study.spring.ioc;

import com.deng.study.domain.Hello;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/3/16 12:11
 */
@Component
public class HelloFactory implements FactoryBean<Hello> {
    @Override
    public Hello getObject() throws Exception {
        return new Hello();
    }

    @Override
    public Class<?> getObjectType() {
        return Hello.class;
    }
}
