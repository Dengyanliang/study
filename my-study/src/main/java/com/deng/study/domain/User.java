package com.deng.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/3 19:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String name;
    private int age;

}
