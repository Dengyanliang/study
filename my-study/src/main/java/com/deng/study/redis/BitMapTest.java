package com.deng.study.redis;

import com.deng.study.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/3/5 19:21
 */
@Slf4j
public class BitMapTest {

    /**
     * 测试签到
     */
    @Test
    public void testSign(){
        Long userId = 3300L;
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        System.out.println(keySuffix);

        String key = "user_sign_key_" + userId + keySuffix;
        System.out.println(key);

        int dayOfMonth = now.getDayOfMonth();
        System.out.println(dayOfMonth);

        // bitMap的offset是从0开始的，所以存放的offset是dayOfMonth
        RedisUtil.getJedis().setbit(key, dayOfMonth - 1, true);
    }

}
