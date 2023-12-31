package com.deng.study.designpattern.principle.liskovsubstitution;

/**
 * @Desc: 正方形
 * @Date: 2023/12/29 10:54
 * @Auther: dengyanliang
 */
public class Square implements Quadrange{
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
    public long getLength() {
        return getSideLength();
    }
}
