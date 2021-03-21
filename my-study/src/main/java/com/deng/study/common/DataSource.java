package com.deng.study.common;

import java.lang.annotation.*;

/**
 * @Desc: 自定义注解
 * @Auther: dengyanliang
 * @Date: 2021/3/21 21:46
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    boolean isMaster();
}
