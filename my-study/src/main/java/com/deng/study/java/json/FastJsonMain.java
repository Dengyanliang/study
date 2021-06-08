package com.deng.study.java.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.deng.study.domain.User;

public class FastJsonMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 创建对象并赋值
        User user = new User();
        user.setAge(12);
        user.setPersonId(123);
        user.setName("zhangsan");

        // 序列化配置对象
        SerializeConfig config1 = new SerializeConfig();
        config1.propertyNamingStrategy = PropertyNamingStrategy.CamelCase; // 大写 默认

        // 序列化对象
        String json1 = JSON.toJSONString(user,config1);
        System.out.println("反序列 person json 1-> " + json1); //  {"age":12,"name":"zhangsan","personId":123}

        SerializeConfig config2 = new SerializeConfig();
        config2.propertyNamingStrategy = PropertyNamingStrategy.PascalCase; // 大写
        String json2 = JSON.toJSONString(user,config2);
        System.out.println("反序列 person json 2-> " + json2); // {"Age":12,"Name":"zhangsan","PersonId":123}

        SerializeConfig config3 = new SerializeConfig();
        config3.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase; // 大写
        String json3 = JSON.toJSONString(user,config3);
        System.out.println("反序列 person json 3-> " + json3); // {"age":12,"name":"zhangsan","person_id":123}

        SerializeConfig config4 = new SerializeConfig();
        config4.propertyNamingStrategy = PropertyNamingStrategy.KebabCase; // 大写
        String json4 = JSON.toJSONString(user,config4);
        System.out.println("反序列 person json 4-> " + json4); // {"age":12,"name":"zhangsan","person-id":123}

        SerializeConfig config5 = new SerializeConfig();
        config5.propertyNamingStrategy = PropertyNamingStrategy.NoChange; // 大写
        String json5 = JSON.toJSONString(user,config5);
        System.out.println("反序列 person json 5-> " + json5); // {"age":12,"name":"zhangsan","personId":123}


        // 反序列化配置对象
        ParserConfig parserConfig = new ParserConfig();
        parserConfig.propertyNamingStrategy = PropertyNamingStrategy.PascalCase;

        // 反序列化对象
        user = JSON.parseObject(json1, User.class, parserConfig);
        System.out.println("反序列化 内容 -> " + user);
    }
}
