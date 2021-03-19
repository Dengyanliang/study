package com.deng.study.dao.po;

import java.util.Date;

public class Student {
    /**
     * 
     */
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 身高
     */
    private Integer height;

    /**
     * 性别 1男 2女
     */
    private Integer sex;

    /**
     * 出生日期
     */
    private Date birthday;

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
     * 名字
     * @return name 名字
     */
    public String getName() {
        return name;
    }

    /**
     * 名字
     * @param name 名字
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 身高
     * @return height 身高
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 身高
     * @param height 身高
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 性别 1男 2女
     * @return sex 性别 1男 2女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 性别 1男 2女
     * @param sex 性别 1男 2女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 出生日期
     * @return birthday 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 出生日期
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}