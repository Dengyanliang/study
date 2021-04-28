package com.deng.study.java;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;

import java.util.concurrent.ConcurrentMap;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/10 16:14
 */
public class StringTest {

    public static final String TITLE_LENGTH_EXCEEDED_MESSAGE = "标题已超过%s字限制，请适当缩减"; // 对于final修饰的字符串，也可以使用%s进行修饰

    public static void main(String[] args) {
        String ss = String.format(TITLE_LENGTH_EXCEEDED_MESSAGE, 10);
        System.out.println(ss);
    }
}
