package com.deng.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Desc：
 * Convert帮助类
 *
 * @author dengyanliang
 * @date 2020-01-16 14:51
 */
public final class ModelConvertUtil {
    private static final Logger logger = LoggerFactory.getLogger(ModelConvertUtil.class);

    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<String, BeanCopier>();

    private ModelConvertUtil() {
    }

    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }

    public static <D, T> List<D> copyList(List<T> srcObj, Class<D> destinationClass) {
        if (srcObj == null || srcObj.size() == 0) {
            return new ArrayList<>(0);
        }
        try {
            String key = genKey(srcObj.get(0).getClass(), destinationClass);
            BeanCopier copier = null;
            if (!BEAN_COPIER_MAP.containsKey(key)) {
                copier = BeanCopier.create(srcObj.get(0).getClass(), destinationClass, false);
                BEAN_COPIER_MAP.put(key, copier);
            } else {
                copier = BEAN_COPIER_MAP.get(key);
            }
            BeanCopier finalCopier = copier;

            List<D> destList = srcObj.stream().map(c -> {
                try {
                    D destObj = destinationClass.newInstance();
                    finalCopier.copy(c, destObj, null);
                    return destObj;
                } catch (Exception ex) {
                    logger.warn("copyList error", ex);
                    return null;
                }
            }).collect(Collectors.toList());
            return destList;
        } catch (Exception ex) {
            logger.warn("copyList error", ex);
            return new ArrayList<>(0);
        }
    }

    public static void copyObj(Object srcObj, Object destObj) {
        if (srcObj == null || destObj == null) {
            return;
        }
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier = null;
        if (!BEAN_COPIER_MAP.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIER_MAP.put(key, copier);
        } else {
            copier = BEAN_COPIER_MAP.get(key);
        }
        copier.copy(srcObj, destObj, null);
    }

    public static <D> D copyObj(Object srcObj, Class<D> destinationClass) {
        if (srcObj == null || destinationClass == null) {
            return null;
        }
        String key = genKey(srcObj.getClass(), destinationClass);
        BeanCopier copier = null;
        if (!BEAN_COPIER_MAP.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destinationClass, false);
            BEAN_COPIER_MAP.put(key, copier);
        } else {
            copier = BEAN_COPIER_MAP.get(key);
        }
        try {
            D destObj = destinationClass.newInstance();
            copier.copy(srcObj, destObj, null);
            return destObj;
        } catch (Exception ex) {
            return null;
        }

    }

}
