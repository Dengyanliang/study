package com.deng.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/11 11:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Apple {
    private String color;
    private Integer weight;
    private String country;

}
