package com.deng.jta.atomikos.xa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("product")
public class Product implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品金额，单位：分
     */
    private Long price;

    /**
     * 总数量
     */
    private Long count;

}