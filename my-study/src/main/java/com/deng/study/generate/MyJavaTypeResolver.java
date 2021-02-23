package com.deng.study.generate;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * @Desc:转换器
 * @Auther: dengyanliang
 * @Date: 2020/1/15 20:26
 */
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {

    /**
     * 将数据库定义的字段TINYINT,在MyBatis自动生成java代码语句时对应的类型为Integer
     */
    public MyJavaTypeResolver() {
        super();
        this.typeMap.put(-6,
                new JavaTypeResolverDefaultImpl.JdbcTypeInformation("TINYINT",
                        new FullyQualifiedJavaType(Integer.class.getName())));

    }
}