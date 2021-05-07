package com.deng.study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Desc：
 * Convert帮助类
 *
 * @author jinxudong
 * @date 2020-01-16 14:51
 */
public final  class ModelConvertUtil {
    private static final Logger logger = LoggerFactory.getLogger(ModelConvertUtil.class);

    private static final Map<String, BeanCopier> BEAN_COPIERS = new ConcurrentHashMap<String, BeanCopier>();
    private ModelConvertUtil(){}

    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }
    public static <D,T> List<D> mapList(List<T> srcObj, Class<D> destinationClass) {
        if(srcObj==null||srcObj.size()==0){
            return new ArrayList<>(0);
        }
        try {
            String key = genKey(srcObj.get(0).getClass(), destinationClass);
            BeanCopier copier = null;
            if (!BEAN_COPIERS.containsKey(key)) {
                copier = BeanCopier.create(srcObj.get(0).getClass(), destinationClass, false);
                BEAN_COPIERS.put(key, copier);
            } else {
                copier = BEAN_COPIERS.get(key);
            }
            BeanCopier copier1=copier;

            List<D> destList= srcObj.stream().map(c->{
                try {
                    D destObj=destinationClass.newInstance();
                    copier1.copy(c, destObj, null);
                    return destObj;
                }catch (Exception ex){
                    logger.warn("mapList error", ex);
                    return null;

                }
            }).collect(Collectors.toList());
            return destList;
        }
        catch (Exception ex){
            logger.warn("mapList error", ex);
            return new ArrayList<>(0);
        }
    }

    public static void map(Object srcObj, Object destObj)
    {
        if(srcObj==null || destObj==null) {
            return;
        }
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier = null;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        copier.copy(srcObj, destObj, null);
    }
    public static <D> D map(Object srcObj, Class<D> destinationClass)
    {
        if(srcObj==null || destinationClass == null){
            return null;
        }
        String key = genKey(srcObj.getClass(), destinationClass);
        BeanCopier copier = null;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destinationClass, false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        try
        {
            D destObj= destinationClass.newInstance();
            copier.copy(srcObj, destObj, null);
            return destObj;
        }catch (Exception ex){
            return null;
        }

    }

}
