package com.deng.study.designpattern.principle.liskovsubstitution;

/**
 * @Desc: 长方形
 * @Date: 2023/12/29 10:51
 * @Auther: dengyanliang
 */
public class Rectangle_V1 {
    private long width;
    private long length;

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
