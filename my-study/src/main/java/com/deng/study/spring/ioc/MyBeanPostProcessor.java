package com.deng.study.spring.ioc;

import com.deng.study.common.DataSource;
import com.deng.study.spring.aop.MyAnnotation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private IService service1;

    public MyBeanPostProcessor(){
        System.out.println("MyBeanPostProcessor...");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        service1.test();
//        System.out.println("MyBeanPostProcessor..postProcessBeforeInitialization: " + beanName + "," + bean.getClass().getName());
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        filter(bean,targetClass);
        return bean;
    }

    private void filter(Object bean,Class<?> targetClass){
        // 首先得到类上所有方法，针对每一个方法调用MethodFilter进行匹配检查，
        // 如果匹配上，调用MethodCallback回调方法，该回调方法会递归向上检查所有父类的实现的接口上的所有方法并处理
        ReflectionUtils.doWithMethods(targetClass,
                method -> {
                    System.out.println("MyBeanPostProcessor.filter:"+method.getName());
                },
                method -> Objects.nonNull(method.getAnnotation(DataSource.class))
        );
    }


}
