package com.deng.common.annotation;


import com.chagee.cotp.common.validator.EnumCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Desc: 自定义注解校验
 * @Date: 2024/3/22 12:50
 * @Auther: dengyanliang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD})
@Constraint(validatedBy = EnumCheckValidator.class)
public @interface EnumCheck {

    /**
     * 用于指定当校验失败时，返回的错误信息
     */
    String message() default "";

    /**
     * 用于指定分组，即根据不同的分组应用不同的校验规则
     */
    Class<?>[] groups() default {};

    /**
     * 用于指定元数据，即可以通过该属性传递一些额外的验证信息
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 自定义的枚举类
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * 枚举类的方法
     */
    String enumMethod();
}
