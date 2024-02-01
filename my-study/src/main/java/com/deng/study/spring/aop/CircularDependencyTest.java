package com.deng.study.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.PostConstruct;
import javax.inject.Provider;

/**
 * @Desc: 循环依赖测试
 * @Auther: dengyanliang
 * @Date: 2023/2/2 11:13
 */
public class CircularDependencyTest {

    /**
     * 测试set方式的循环依赖
     */
    @Test
    public void testSetCircular(){
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("a",A.class);
        context.registerBean("b",B.class);
        context.registerBean(MyAspect.class);   // 注入切面
        context.registerBean(AnnotationAwareAspectJAutoProxyCreator.class); // 注入增强
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.refresh();

        context.getBean(A.class).foo();
    }

    /**
     * 测试构造方式的循环依赖
     */
    @Test
    public void testConstuCircular(){
        // 源码 DefaultListableBeanFactory.java 中的resolveDependency() 调试

        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean("c",C.class);
        context.registerBean("d",D.class);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context.getDefaultListableBeanFactory());
        context.refresh();

//        D d = context.getBean(C.class).d.getObject();  // ObjectFactory方式
//        System.out.println("对象D：" + d);

        D d = context.getBean(C.class).d.get();     // Provider方式
        System.out.println("对象D：" + d);
    }

    static class A{
        private static final Logger log = LoggerFactory.getLogger("A");
        private B b;

        public A(){
            log.info("A()");
        }

        @Autowired
        public void setB(B b) {
            log.info("setB({})",b);
            this.b = b;
        }

        @PostConstruct
        public void init(){
            log.info("init()");
        }

        public void foo(){
            System.out.println("foo()");
        }
    }

    static class B{
        private static final Logger log = LoggerFactory.getLogger("B");
        private A a;
        public B(){
            log.info("B()");
        }

        @Autowired
        public void setA(A a) {
            log.info("setA({})",a.getClass());
            this.a = a;
        }

        @PostConstruct
        public void init(){
            log.info("init()");
        }
    }

    @Aspect
    static class MyAspect{

        @Before("execution(* foo())")
        public void before(){
            System.out.println("before...");
        }
    }

    static class C{
        private static final Logger log = LoggerFactory.getLogger("C");
//        private D d;
//        private ObjectFactory<D> d;
        private Provider<D> d; // 使用javax.inject 解决

        // 第一种方式，使用@Lazy注解解决构造方式的循环依赖
//        public C(@Lazy D d) {
//            log.info("D({})",d.getClass());
//            this.d = d;
//        }

        // 第二种方式，使用ObjectFactory解决
//        public C(ObjectFactory<D> d) {
//            log.info("D({})",d.getClass());
//            this.d = d;
//        }

        // 第三种方式，使用Provider方式解决
        public C(Provider<D> d) {
            log.info("D({})",d.getClass());
            this.d = d;
        }

        @PostConstruct
        public void init(){
            log.info("init()");
        }
    }

    static class D{
        private static final Logger log = LoggerFactory.getLogger("D");
        private C c;

        public D(C c) {
            log.info("C({})",c.getClass());
            this.c = c;
        }

        @PostConstruct
        public void init(){
            log.info("init()");
        }
    }

}
