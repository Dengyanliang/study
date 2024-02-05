package com.deng.study.enums;

/**
 * @Desc:
 * @Date: 2024/2/2 15:30
 * @Auther: dengyanliang
 */
public enum DelayTimeLevelEnum implements MyEnum{

    // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
    DELAY_0s(0,"0s"),
    DELAY_1s(1,"1s"),
    DELAY_5s(2,"5s"),
    DELAY_10s(3,"10s"),
    DELAY_30s(4,"30s"),
    DELAY_1m(5,"1m"),
    DELAY_2m(6,"2m"),
    DELAY_3m(7,"3m"),

    ;

    private int level; // 延迟级别
    private String levelDesc; // 延时的时间

    DelayTimeLevelEnum(int level, String levelDesc) {
        this.level = level;
        this.levelDesc = levelDesc;
    }

    public static DelayTimeLevelEnum getDelayTimeLevel(int level){
        for (DelayTimeLevelEnum delayTimeLevelEnum : DelayTimeLevelEnum.values()) {
            if(delayTimeLevelEnum.getCode() == level){
                return delayTimeLevelEnum;
            }
        }
        // 如果找不到，则立即发送
        return DelayTimeLevelEnum.DELAY_0s;
    }

    @Override
    public int getCode() {
        return level;
    }

    @Override
    public String getDesc() {
        return levelDesc;
    }
}
