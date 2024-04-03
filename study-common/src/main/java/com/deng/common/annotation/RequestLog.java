package com.deng.common.annotation;

import java.lang.annotation.*;

/**
 * @Desc: 请求注解
 * @Date: 2024/4/1 15:42
 * @Auther: dengyanliang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface RequestLog {

}
