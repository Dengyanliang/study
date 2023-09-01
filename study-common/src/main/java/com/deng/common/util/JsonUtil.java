package com.deng.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.*;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final String EMPTY_JSON = "{}";
    private static final String EMPTY_JSON_ARRAY = "[]";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String toJson(Object target) {
        return toJson(target, null, null);
    }

    public static String toJson(Object target, Type targetType, String datePattern) {
        if (target == null) {
            return "{}";
        }
        GsonBuilder builder = new GsonBuilder();
        if ((datePattern != null) && (datePattern.length() >= 1)) {
            builder.setDateFormat(datePattern);
        }

        Gson gson = builder.create();
        String result = "{}";
        try {
            if (targetType == null) {
                result = gson.toJson(target);
            }
            else {
                result = gson.toJson(target, targetType);
            }
        } catch (Exception ex) {
            logger.debug("gson error", ex);
            if (((target instanceof Collection)) || ((target instanceof Iterator)) || ((target instanceof Enumeration)) || (target.getClass().isArray())) {
                result = "[]";
            }
        }
        return result;
    }

    public static String toJson(Object target, Type targetType) {
        return toJson(target, targetType, null);
    }

    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        if ((json == null) || (json.length() < 1)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if ((datePattern != null) && (datePattern.length() >= 1)) {
            builder.setDateFormat(datePattern);
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            logger.warn("gson error", ex);
        }
        return null;
    }

    public static Object fromJson(String json, Type type, String datePattern) {
        if ((json == null) || (json.length() < 1)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if ((datePattern != null) && (datePattern.length() >= 1)) {
            builder.setDateFormat(datePattern);
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, type);
        } catch (Exception ex) {
            logger.warn("gson error", ex);
        }
        return null;
    }

    public static Object fromJson(String json, Type type) {
        return fromJson(json, type, null);
    }

    public static <T> T fromJson(String json, TypeToken<T> token) {
        return fromJson(json, token, null);
    }

    public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
        if ((json == null) || (json.length() < 1)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if ((datePattern != null) && (datePattern.length() >= 1)) {
            builder.setDateFormat(datePattern);
        }
        builder.setDateFormat(datePattern);
        Gson gson = builder.create();
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception ex) {
            logger.warn("gson error", ex);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return fromJson(json, clazz, null);
    }

    /**
     * json to VO实例 并且处理对应的科学计数法
     *
     * @param <T>
     * @param obj
     * @return
     * @exception
     * @date        2018/12/27 9:49
     */
    public static <T> Object jsonToObjectLong(String json, Class<T> obj)
    {
        return new Gson().fromJson(
                new GsonBuilder().setLongSerializationPolicy(LongSerializationPolicy.STRING).create()
                        .toJson(JSONObject.parse(json)), obj);
    }

    /**
     * 解决gson解析long自动转为科学计数的问题
     */
    public static Gson getMapGson() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Map.class, new JsonDeserializer<Map>() {
            @Override
            public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                HashMap<String, Object> resultMap = new HashMap<>();
                JsonObject jsonObject = json.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    resultMap.put(entry.getKey(), entry.getValue());
                }
                return resultMap;
            }
        }).setLongSerializationPolicy(LongSerializationPolicy.STRING).create();
        return gson;
    }
    /**
     * 解决gson自动转译特殊字符问题
     */
    public static Gson getHtmlEscapingGson(){
        return new GsonBuilder().disableHtmlEscaping().create();
    }
}
