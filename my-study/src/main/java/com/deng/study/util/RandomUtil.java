package com.deng.study.util;

import java.util.Random;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/22 10:33
 */
public class RandomUtil {

    /**
     * 返回给定数字之内的大于0的随机数，
     * @param num 给定数字
     * @return
     */
    public static int getInt(int num){
        return new Random().nextInt(num)+1;
    }

}
