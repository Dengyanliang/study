package com.deng.study.shardingsphere.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deng.study.shardingsphere.dao.mapper.CourseMapper;
import com.deng.study.shardingsphere.dao.mapper.PayOrderMapper;
import com.deng.study.shardingsphere.dao.mapper.UdictMapper;
import com.deng.study.shardingsphere.po.Course;
import com.deng.study.shardingsphere.po.PayOrder;
import com.deng.study.shardingsphere.po.Udict;
import com.deng.study.shardingsphere.service.CourseService;
import com.deng.study.shardingsphere.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/14 20:34
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJdbcTest {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UdictMapper udictMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private PayOrderService payOrderService;

    /**
     * 分库分表都能使用
     */
    @Test
    public void addCourse(){
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setName("英语");
            course.setUserId(Long.valueOf(random.nextInt(100)));
            course.setStatus("normal");

//            courseMapper.insert(course);
            courseService.addCourse(course);
        }
    }

    /**
     * 测试分表不分库
     */
    @Test
    public void getCourseByTable(){
        for (int i = 0; i < 10; i++) {
            QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",1428868022394122241L);
//        Course course = courseMapper.selectOne(queryWrapper);

            Course course = courseService.getCourse(queryWrapper);
            System.out.println(course);

            if(course.getUserId().intValue() == 53){
                System.out.println("=============");
            }
        }
    }

    /**
     * 测试分库分表
     */
    @Test
    public void getCourseByDbAndTable(){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",53);
        queryWrapper.eq("id",1428868022394122241L);
        Course course = courseMapper.selectOne(queryWrapper);
        System.out.println(course);
    }

    @Test
    public void addDict(){
        Udict udict = new Udict();
        udict.setValue("2");
        udict.setStatus("已执行");

        udictMapper.insert(udict);
    }

    @Test
    public void deleteDictById(){
       QueryWrapper<Udict> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("id",1426833054297960450L);
       udictMapper.delete(queryWrapper);
    }

    @Test
    public void addOrder(){
        long start = System.currentTimeMillis();
        int max = 1;
        PayOrder order = null;
        for(int i = 1; i <= max; i++){
            order = new PayOrder();
            order.setUserId((long)(Math.random()*10000));
            order.setProductId((long)(Math.random()*100000));
            order.setOrderFee((long)(Math.random()*100));
            order.setPayStatus(1);
            order.setCreateTime(DateUtil.now());
            order.setPayFinishTime(DateUtil.now());
            order.setVersion(1);
            payOrderService.addPayOrder(order);
        }
        long end = System.currentTimeMillis();
        log.info("总的耗时：{}",end-start);
    }

}
