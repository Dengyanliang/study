package com.deng.study.java.thread;

import lombok.Data;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/16 10:52
 */
@Data
public class Student {
    private volatile int id;
    volatile String name;
}
