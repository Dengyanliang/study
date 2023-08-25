package com.deng.study.shardingsphere.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deng.study.shardingsphere.dao.mapper.MyCourseMapper;
import com.deng.study.shardingsphere.dao.po.MyCourse;
import com.deng.study.shardingsphere.service.MyCourseService;
import com.deng.study.shardingsphere.util.DateUtil;
import com.deng.study.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/3/19 00:03
 */
@Service
public class MyCourseServiceImpl implements MyCourseService {

    @Autowired
    private MyCourseMapper myCourseMapper;

    @Override
    public void addCourse(MyCourse myCourse) {
        myCourseMapper.insert(myCourse);
    }

    @Override
    public MyCourse getCourse(QueryWrapper<MyCourse> queryWrapper ) {
        return myCourseMapper.selectOne(queryWrapper);
    }


    @Override
    public IPage<MyCourse> getCourseByPage(IPage<MyCourse> page, QueryWrapper<MyCourse> queryWrapper) {
        return myCourseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public void addBatchMyCourseByPreparedStatement(List<MyCourse> myCourseList) {
        //事务上限，每次累计这么多条数，提交一次事务
        int maxCommit = 100000;
        try{
            Connection conn = JdbcUtils.getConnection();
            String sql = "insert into my_course (name, user_id, status, create_time, update_time) values (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            // 关闭自动提交，不然conn.commit()运行到这句会报错
            conn.setAutoCommit(false);

            for (int i = 0; i < myCourseList.size(); i++) {
                MyCourse myCourse = myCourseList.get(i);
                // 设置参数
                pstmt.setString(1, myCourse.getName());
                pstmt.setLong(2, myCourse.getUserId());
                pstmt.setString(3, myCourse.getStatus());

                // 设置日期，直接用string
                pstmt.setString(4, DateUtil.getDateNow());
                pstmt.setString(5, DateUtil.getDateNow());

                // 增加到批处理中
                pstmt.addBatch();
                //分段提交
                if (i % maxCommit == 0) {
                    pstmt.executeBatch();
                    conn.commit();

                    // 重新开始
                    conn.setAutoCommit(false);// 关闭自动提交
                    pstmt = conn.prepareStatement(sql);
                }
            }

            // 执行
            pstmt.executeBatch();
            //提交事务
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release();
        }
    }
}




