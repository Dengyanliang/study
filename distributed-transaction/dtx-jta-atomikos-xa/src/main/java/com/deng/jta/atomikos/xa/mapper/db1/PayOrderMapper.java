package com.deng.jta.atomikos.xa.mapper.db1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deng.jta.atomikos.xa.entity.PayOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {

}