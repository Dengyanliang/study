package com.deng.study.java;

import com.deng.study.domain.Pet;
import com.deng.study.domain.User;
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

        User newUser = new User();
        BeanUtils.copyProperties(user,newUser);

        System.out.println(newUser);

    }

}
