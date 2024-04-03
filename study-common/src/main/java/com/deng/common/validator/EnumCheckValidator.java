package com.chagee.cotp.common.validator;



import com.deng.common.annotation.EnumCheck;
import com.deng.common.exception.BizException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Desc: 自定义枚举规则校验
 * @Date: 2024/3/22 13:50
 * @Auther: dengyanliang
 */
public class EnumCheckValidator implements ConstraintValidator<EnumCheck,Object> {

    private Class<? extends Enum<?>> enumClass;
    private String enumMethod;


    /**
     * 初始化方法
     * @param enumCheck
     * @return
     */
    @Override
    public void initialize(EnumCheck enumCheck) {
        enumClass = enumCheck.enumClass();
        enumMethod = enumCheck.enumMethod();
    }

    /**
     * 验证方法
     * @param value 传入的数据
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(Objects.isNull(value) || Objects.isNull(enumClass)|| Objects.isNull(enumMethod)){
            return Boolean.FALSE;
        }

        Class<?> valueClass = value.getClass();
        try {
            Method method = enumClass.getMethod(enumMethod, valueClass);
            return (Boolean) method.invoke(null, value);
        } catch (Exception e) {
            throw new BizException("参数校验异常：" + e.getMessage());
        }
    }

}
