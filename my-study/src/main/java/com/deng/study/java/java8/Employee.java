package com.deng.study.java.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/9 20:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private String name;
    private int age;
    private double salary;
}
