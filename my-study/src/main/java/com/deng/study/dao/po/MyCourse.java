package com.deng.study.dao.po;

import java.util.Date;

public class MyCourse {
    /**
     * 
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 课程状态
     */
    private String status;

    /**
     * 订单时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 用户id
     * @return user_id 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 用户id
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 课程状态
     * @return status 课程状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 课程状态
     * @param status 课程状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 订单时间
     * @return create_Time 订单时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 订单时间
     * @param createTime 订单时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     * @return update_time 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}