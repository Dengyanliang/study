package com.deng.study.java.json;

import com.deng.study.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonTest {
    public static void main(String[] args) throws JsonProcessingException {
        User user = User.getSimpleUser();

        // fastjson并不认识jsonProperty 所以不会生效
        String s = new ObjectMapper().writeValueAsString(user);
        System.out.println(s); // {"age":12,"personId":123,"use_name":"zhangsan"}


    }
}
