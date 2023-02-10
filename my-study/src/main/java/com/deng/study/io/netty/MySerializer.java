package com.deng.study.io.netty;

import com.google.gson.Gson;
import lombok.Data;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Desc: 自定义序列化和反序列化算法
 * @Auther: dengyanliang
 * @Date: 2023/2/9 20:25
 */
public interface MySerializer {

    /**
     * 反序列化算法
     * 将字节byte[] 转化为clazz类型
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 序列化算法：将对象转换成byte[]
     * @param object
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T object) throws IOException;

    enum Algorithm implements MySerializer {

        JAVA(0) {
            @Override
            public <T> byte[] serialize(T object)  {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);

                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败",e);
                }
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (Exception e) {
                    throw new RuntimeException("反序列化失败",e);
                }
            }
        },
        JSON(1) {
                @Override
                public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                    String str = new String(bytes,StandardCharsets.UTF_8);
                    return new Gson().fromJson(str,clazz);
                }

                @Override
                public <T> byte[] serialize(T object){
                    String json = new Gson().toJson(object);
                    return json.getBytes(StandardCharsets.UTF_8);
                }
            }
        ;

        private int code;

        Algorithm(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

}
