package com.deng.study.controller;

import com.deng.study.domain.ProductDomain;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Desc: 模拟聚划算Controller
 * @Auther: dengyanliang
 * @Date: 2023/2/28 10:46
 */
@Api(tags = "聚划算测试")
@Slf4j
@RestController
public class JHSTaskController {
    private static final String JHS_KEY = "jhs";
    private static final String JHS_KEY_A = "jhs:a";
    private static final String JHS_KEY_B = "jhs:b";

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("聚划算案例，每次1页每页5条")
    @RequestMapping(value="/product/find",method = RequestMethod.GET)
    public List<ProductDomain> find(int page, int size){
        long start = (page-1)*size;
        long end = start + size -1;
        List<ProductDomain> list = null;
        try {
            list = redisTemplate.opsForList().range(JHS_KEY,start,end);
            if(CollectionUtils.isEmpty(list)){
                // 查询mysql
            }
            log.info("参加活动的商家：{}",list);
        } catch (Exception e) {
            log.error("聚划算发生了异常",e);
            e.printStackTrace();
        }

        return list;

    }





}

