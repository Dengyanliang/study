package com.deng.study.java.java8.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/13 23:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Trade trade;
    private int year;
    private int value;
}
