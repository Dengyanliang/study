package com.deng.study.enums;

/**
 * @Desc:
 * @Date: 2024/2/2 15:30
 * @Auther: dengyanliang
 */
public enum DelayTimeLevelEnum implements MyEnum{

    // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
    LEVEL_0s(0,"0s"),
    LEVEL_1s(1,"1s"),
    LEVEL_5s(2,"5s"),
    LEVEL_10s(3,"10s"),
    LEVEL_30s(4,"30s"),
    LEVEL_1m(5,"1m"),
    LEVEL_2m(6,"2m"),
    LEVEL_3m(7,"3m"),

    ;

    DelayTimeLevelEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public static DelayTimeLevelEnum getDelayTimeLevel(int level){
        for (DelayTimeLevelEnum delayTimeLevelEnum : DelayTimeLevelEnum.values()) {
            if(delayTimeLevelEnum.getCode() == level){
                return delayTimeLevelEnum;
            }
        }
        // 如果找不到，则立即发送
        return DelayTimeLevelEnum.LEVEL_0s;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
