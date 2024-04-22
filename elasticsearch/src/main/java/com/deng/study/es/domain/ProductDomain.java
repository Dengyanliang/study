package com.deng.study.es.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/9 22:25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product", shards = 1, replicas = 1)
public class ProductDomain {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Text,index = false)
    private String images;

    /**
     * 发送时间
     */
    private long createTime;

    /**
     * 手机号
     */
    private String mobile;
}

