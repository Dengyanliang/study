package com.deng.study.java;

import com.deng.study.domain.Person;
import com.deng.study.domain.Pet;
import com.deng.study.domain.User;
import com.deng.study.util.ModelConvertUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyTest {



    public static void main(String[] args) {
        Pet dogPet = new Pet();
        dogPet.setAge(3);
        dogPet.setName("dog");

        Pet catPet = new Pet();
        catPet.setAge(3);
        catPet.setName("dog");

        List<Pet> petList = new ArrayList<>();
        petList.add(dogPet);
        petList.add(catPet);

        User user = new User();
        user.setAge(12);
        user.setPersonId(123);
        user.setName("zhangsan");
        user.setPetList(petList);

        User user2 = new User();
        user2.setAge(20);
        user2.setPersonId(456);
        user2.setName("lisi");
        user2.setPetList(petList);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);


//        User newUser = new User();
//        List<User> newUserList = new ArrayList<>();

        Person person3 = new Person();
        BeanUtils.copyProperties(user2,person3); // 数据为空
        System.out.println("person3:"+person3);

        List<Person> newUserList = ModelConvertUtil.copyList(userList, Person.class);
        System.out.println(newUserList);

        System.out.println("**************************");

        Person person = ModelConvertUtil.copyObj(user, Person.class);
        System.out.println(person);

        System.out.println("------------------");

        Person person2 = new Person();
        ModelConvertUtil.copyObj(user,person2);
        System.out.println(person2);

    }

}
