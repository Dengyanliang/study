package com.deng.study.design_pattern.principle.liskovsubstitution.methodoutput;

import java.util.HashMap;

/**
 * @Desc:
 * @Date: 2023/12/29 11:55
 * @Auther: dengyanliang
 */
public class Child extends Base{

    /**
     * 当子类实现父类的抽象方法时，返回值应该比父类的返回值更严格或者相等
     * @return
     */
    @Override
    public HashMap method() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message","子类method被执行");

        System.out.println("子类method被执行");
        return hashMap;
    }
}
