package com.deng.study.geekbang.week07;

import com.deng.study.MyApplication;
import com.deng.study.dao.po.MyCourse;
import com.deng.study.dao.po.PayOrder;
import com.deng.study.service.MyCourseService;
import com.deng.study.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:10
 */

@Slf4j
// @RunWith(SpringRunner.class)注解的意义在于Test测试类要使用注入的类，比如@Autowired注入的类,)这些类才能实例化到spring容器中，
// 自动注入才能生效，然直接一个NullPointerExecption
// 一定要使用org.junit.Test，不然会报错
@RunWith(SpringRunner.class)
@SpringBootTest(classes= MyApplication.class)
public class MyCourseTest {

    @Autowired
    private MyCourseService myCourseService;

    @Test
    public void addBatchMyCourse(){
        long start = System.currentTimeMillis();

        Random random = new Random();
        List<MyCourse> list = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            MyCourse course = new MyCourse();
            course.setName("测试-" + i);
            course.setUserId((long) random.nextInt(100000000));
            course.setStatus("normal");
            list.add(course);
        }
        myCourseService.addBatchMyCourseByPreparedStatement(list);

        long end = System.currentTimeMillis();
        System.out.println("耗费：" + (end-start));
    }

}
