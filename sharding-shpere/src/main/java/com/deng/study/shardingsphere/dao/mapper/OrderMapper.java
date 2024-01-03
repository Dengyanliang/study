package com.deng.study.shardingsphere.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.study.shardingsphere.dao.po.Order;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/14 20:32
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {
    /**
     * 批量插入
     * @param orderList 实体列表
     * @return 影响行数
     */
    Integer insertBatchSomeColumn(Collection<Order> orderList);
}
