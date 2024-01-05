package com.deng.study.redis.common;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/3/5 17:28
 */
@Component
public class RedisIdWorker {
    public static final long begin_time = 0l;

    public long nextId(String prefix){


        return 0l;
    }

    public static void main(String[] args) {
        //
        final long l = System.currentTimeMillis();
        System.out.println(l);
        final long l1 = TimeUnit.MILLISECONDS.toSeconds(l);
        System.out.println(l1);

        final LocalDateTime time = LocalDateTime.of(2023, 3, 5, 17, 40, 00);
        final long l2 = time.toEpochSecond(ZoneOffset.UTC);
        System.out.println(l2);
    }

}
