package com.deng.study.spring.ioc;

import com.deng.study.common.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

@Slf4j
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private IService service1;

    public MyBeanPostProcessor(){
        System.out.println("MyBeanPostProcessor...");
    }


    /**
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("JHSTaskService")){
            log.info("------JHSTaskService postProcessBeforeInitialization----");
        }
//        service1.test();
//        System.out.println("MyBeanPostProcessor..postProcessBeforeInitialization: " + beanName + "," + bean.getClass().getName());
        return bean; // keypoint 这里不能返回null，如果这里返回了null，那么就不会执行后面的@PostConstruct注解标注的方法了
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
