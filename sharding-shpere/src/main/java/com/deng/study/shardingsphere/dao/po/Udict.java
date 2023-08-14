package com.deng.study.shardingsphere.dao.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_udict") // 当类名和数据库名不一致的时候，要加上该配置
public class Udict {
    /**
     * 
     */
    private Long id;

    /**
     * 值
     */
    private String value;

    /**
     * 状态
     */
    private String status;
}