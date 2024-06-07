package com.deng.jta.bitronix.xa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.deng.common.util.DateUtil;
import com.deng.jta.bitronix.xa.entity.PayOrder;
import com.deng.jta.bitronix.xa.entity.Product;
import com.deng.jta.bitronix.xa.mapper.db1.PayOrderMapper;
import com.deng.jta.bitronix.xa.mapper.db2.ProductMapper;
import com.deng.jta.bitronix.xa.service.OrderService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/8/30 11:28
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private PayOrderMapper payOrderMapper;

    @Resource
    private ProductMapper productMapper;


    /**
     * 嵌套查询
     * select * from pay_order where pay_status = 1 and  (now() < createTime or now() > updateTime))
     * @return
     */
    public PayOrder query(){
        LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrder::getPayStatus, 1);

        String now = DateUtil.getDateNow();
        queryWrapper.and(wq-> wq.gt(PayOrder::getCreateTime,now).or().lt(PayOrder::getUpdateTime,now));

        List<PayOrder> list = payOrderMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            throw new RuntimeException("");
        }

        return list.get(0);
    }

    @Override
    @Transactional
    public void updateOrder(PayOrder payOrder, Product product) {

        payOrderMapper.updateById(payOrder);
        productMapper.updateById(product);
        int i = 10 / 0 ;
    }
}
