package com.deng.restroom.annotation;

import java.lang.annotation.*;

/**
 * @Desc: 标注需要鉴权
 * @Date: 2024/4/3 17:24
 * @Auther: dengyanliang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignVerify {

}
