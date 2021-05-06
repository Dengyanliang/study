package com.deng.study.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/3 19:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {


    // JsonProperty的作用是将属性的名称序列化为另外一个名称。这里是把name属性序列化为user_name
    @JsonProperty(value="user_name")
    private String name;
    private int age;
    private int personId;

    private List<Pet> petList;

}
