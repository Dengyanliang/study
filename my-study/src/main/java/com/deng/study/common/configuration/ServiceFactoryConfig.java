package com.deng.study.common.configuration;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/10 16:42
 */
public class ServiceFactoryConfig {
    static Properties properties;
    static Map<Class<?>,Object> map = new ConcurrentHashMap<>();
    static {
        try (InputStream in = SerializerConfig.class.getResourceAsStream("/application.properties")) {
            properties = new Properties();
            properties.load(in);

            Set<String> names = properties.stringPropertyNames();
            for (String name :  names){
                if(name.endsWith("Service")){
                    Class<?> interfaceClass = Class.forName(name);
                    Class<?> instanceClass = Class.forName(properties.getProperty(name));
                    map.put(interfaceClass,instanceClass.newInstance());
                }
            }
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public static <T> T getService(Class<?> interfaceClass){
        return (T)map.get(interfaceClass);
    }



}
