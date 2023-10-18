package com.deng.study.dynamic.datasource.datasouce;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 注册动态数据源
 * @Auther: dengyanliang
 * @Date: 2023/10/16 21:32
 */
@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    /**
     * 默认数据源
     */
    private DataSource defalutDataSource;

    /**
     * 所有用户自定义数据源
     */
    private Map<String,DataSource> allDataSources = new HashMap<>();

    @Override
    public void setEnvironment(Environment environment) {
        initDataSource(environment);
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map<Object,Object> targetDataSources = new HashMap<>(6);
        targetDataSources.putAll(allDataSources);

        // 创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);
        beanDefinition.setSynthetic(true);

        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        mpv.addPropertyValue("defaultTargetDataSource",defalutDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);

        // 注册-beanDefinitionRegistry
        beanDefinitionRegistry.registerBeanDefinition("dataSource",beanDefinition);

        log.info("DynamicDataSourceRegister...registerBeanDefinitions...... ");
    }

    /**
     * 初始化数据源
     * @param environment
     */
    private void initDataSource(Environment environment) {
        String dsNames = environment.getProperty("spring.datasource.names");
        if(dsNames != null){
            String[] dsNameArr = dsNames.split(",");
            for (String dsName : dsNameArr) {
                String prefix = "spring.datasource." + dsName;
                Boolean isPrimary = environment.getProperty(prefix + ".primary", Boolean.class, Boolean.FALSE);
                if(isPrimary){
                    defalutDataSource = buildDataSource(environment,prefix);
                    allDataSources.put(dsName,defalutDataSource);
                }else{
                    allDataSources.put(dsName,buildDataSource(environment,prefix));
                    DynamicDataSourceHandler.addSlaveDataSourceName(dsName);
                }
            }
            // 如果默认数据源为空，则取所有数据源中的第一个
            if(defalutDataSource == null){
                String dsName = dsNameArr[0];
                defalutDataSource = allDataSources.get(dsName);
                allDataSources.put(dsName,defalutDataSource);
            }
        }
    }

    private DataSource buildDataSource(Environment environment,String prefix){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty(prefix + ".driver-class-name"));
        dataSource.setUrl(environment.getProperty(prefix + ".url"));
        dataSource.setUsername(environment.getProperty(prefix + ".username"));
        dataSource.setPassword(environment.getProperty(prefix + ".password"));

        return dataSource;
    }



}
