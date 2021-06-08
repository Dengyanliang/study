package com.deng.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/13 23:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    private String name;
    private String city;
}
