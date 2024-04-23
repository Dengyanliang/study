package com.deng.study.es.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

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
     * 格式化日期的格式，将使用指定的格式进行序列化和反序列化
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Field(type = FieldType.Date, format = DateFormat.date_time, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}

