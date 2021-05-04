package com.deng.study.java.fastjson;

import com.deng.study.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTest {
    public static void main(String[] args) throws JsonProcessingException {
        User user = new User();
        user.setAge(12);
        user.setPersonId(123);
        user.setName("zhangsan");

        // fastjson并不认识jsonProperty 所以不会生效
        String s = new ObjectMapper().writeValueAsString(user);
        System.out.println(s); // {"age":12,"personId":123,"use_name":"zhangsan"}


    }
}
