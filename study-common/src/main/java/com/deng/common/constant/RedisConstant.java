package com.deng.common.constant;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/28 19:13
 */
public class RedisConstant {
    public static final String CACHE_KEY_PRODUCT = "product:";
    public static final String LOCK_KEY_PRODUCT = "lockProduct:";
    public static final String WHITE_LIST_PRODUCT = "whitelistProduct";
    public static final String SECKILL_STOCK_KEY = "seckill:stock:";

    public static final String REDIS_HOST_IP = "127.0.0.1";
    public static final Integer REDIS_PORT = 6379;
    public static final Integer TIMEOUT = 10000;
    public static final String REDIS_PASSWORD = "123456";

}
