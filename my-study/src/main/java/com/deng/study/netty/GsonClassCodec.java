package com.deng.study.netty;

import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/10 14:49
 */
@Slf4j
public class GsonClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class,new GsonClassCodec()).create();
        System.out.println(gson.toJson(String.class));  //  "java.lang.String"
//        System.out.println(new Gson().toJson(String.class)); // 直接使用Gson将String转化为json的时候会报错
    }

    /**
     * 反序列化
     * @param jsonElement
     * @param type
     * @param context
     * @return
     * @throws JsonParseException
     */
    @Override
    public Class<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            String str = jsonElement.getAsString();
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            log.error("反序列化失败",e);
            throw new JsonParseException(e);
        }
    }


    /**
     * 序列化
     * @param aClass
     * @param type
     * @param context
     * @return
     */
    @Override
    public JsonElement serialize(Class<?> aClass, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(aClass.getName());
    }
}
