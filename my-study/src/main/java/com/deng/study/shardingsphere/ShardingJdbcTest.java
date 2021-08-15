package com.deng.study.shardingsphere;

import com.deng.study.dao.mapper.CourseMapper;
import com.deng.study.dao.po.Course;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void addCourse(){
        Course course = new Course();
        course.setName("英语");
        course.setUserId(100L);
        course.setStatus("normal");

        courseMapper.insert(course);
    }

}
