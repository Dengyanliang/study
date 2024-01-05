package com.deng.study.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
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


    public static User getSimpleUser(){
        User user = new User();
        user.setAge(12);
        user.setPersonId(123);
        user.setName("zhangsan");
        return user;
    }

    public static User getComplexUser(){
        Pet dogPet = new Pet();
        dogPet.setAge(3);
        dogPet.setName("dog");

        Pet catPet = new Pet();
        catPet.setAge(2);
        catPet.setName("cat");

        List<Pet> petList = new ArrayList<>();
        petList.add(dogPet);
        petList.add(catPet);

        User user = new User();
        user.setAge(12);
        user.setPersonId(123);
        user.setName("zhangsan");
        user.setPetList(petList);

        return user;
    }
}
