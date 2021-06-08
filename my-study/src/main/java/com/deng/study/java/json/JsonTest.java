package com.deng.study.java.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.deng.study.domain.Pet;
import com.deng.study.domain.User;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {

    public static void main(String[] args) {
        // 1.将对象转化为json对象
        User user = User.getComplexUser();
        JSONObject jsonObject = (JSONObject) JSON.toJSON(user);
        System.out.println(jsonObject);

        // 2.将对象转化为json字符串
        String jsonString = JSON.toJSONString(user);
        System.out.println(jsonString);


        // 3.1 从String中获取JsonObject,JsonArray
        String sourceUserStr = "{\"age\":12,\"name\":\"zhangsan\",\"personId\":123,\"petList\":[{\"age\":3,\"name\":\"dog\"},{\"age\":3,\"name\":\"dog\"}]}";
        JSONObject targetJsonObject = JSONObject.parseObject(sourceUserStr);

        // 3.2 将json转化为目标对象
        User targetUser = targetJsonObject.toJavaObject(User.class);
        System.out.println(targetUser);

        // 3.1 从String中获取JsonArray
        String sourcePetStr = "[{\"age\":3,\"name\":\"dog\"},{\"age\":3,\"name\":\"dog\"}]";
        JSONArray jsonArray = JSONObject.parseArray(sourcePetStr);
        // 3.2 将json转化为目标对象
        List<Pet> targetPetList = jsonArray.toJavaList(Pet.class);
        System.out.println(targetPetList);

        System.out.println("-----------------------");

        // 可以将3.1和3.2合并
        User targetUser2 = JSONObject.parseObject(sourceUserStr, User.class);
        System.out.println(targetUser2);
        List<Pet> targetPetList2 = JSONObject.parseArray(sourcePetStr, Pet.class);
        System.out.println(targetPetList2);

    }


}
