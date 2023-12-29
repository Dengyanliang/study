package com.deng.study.designpattern.principle.liskovsubstitution;

/**
 * @Desc: 正方形
 * @Date: 2023/12/29 10:54
 * @Auther: yanliangdeng
 */
public class Square_V1 extends Rectangle_V1{
    private long sideLength;

    public long getSideLength() {
        return sideLength;
    }

    public void setSideLength(long sideLength) {
        this.sideLength = sideLength;
    }

    @Override
    public long getWidth() {
        return getSideLength();
    }

    @Override
    public void setWidth(long width) {
        this.setSideLength(width);
    }

    @Override
    public long getLength() {
        return getSideLength();
    }

    @Override
    public void setLength(long length) {
        this.setSideLength(length);
    }
}
