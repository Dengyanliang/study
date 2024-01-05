package com.deng.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/3 19:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student  {
    private String name;
    private int score;

    public String toString(){
        return name + " " + score;
    }
}
