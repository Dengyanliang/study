package com.deng.study.domain;

import lombok.ToString;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/13 18:02
 */
@ToString
public class Country {
    public String name;
    public int gold;
    public int silver;
    public int bronze;

    public Country(String name, int gold, int silver, int bronze) {
        this.name = name;
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }
}
