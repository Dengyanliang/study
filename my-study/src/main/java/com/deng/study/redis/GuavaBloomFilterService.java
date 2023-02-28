package com.deng.study.redis;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/28 09:44
 */
@Slf4j
@Service
public class GuavaBloomFilterService {

    private static final int _1W = 10000;
    private static final int _10W = 10 * _1W;
    private static final int SIZE = 100 * _1W;
    // 误判率，默认是0.03；如果误判率越小，那么需要的bit位越多，并且需要的hash函数也越多，程序性能也更低
    // fpp=0.000001，numBits=28755175 numHashFunctions=20
    // fpp=0.01，numBits=9585058 numHashFunctions=7
    // fpp=0.03，numBits=7298440 numHashFunctions=5
    private static double fpp = 0.000001;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), SIZE, fpp);

    public void guavaBloomFilter() {
        // 先让bloomFilter加入100W白名单数据
        for (int i = 1; i <= SIZE; i++) {
            bloomFilter.put(i);
        }
        // 故意取10w个不在合法范围内的数据，来进行误判率的演示
        List<Integer> list = new ArrayList<>(_10W);
        // 验证
        for (int i = SIZE+1; i <= SIZE + _10W ; i++) {
            if(bloomFilter.mightContain(i)){
                log.info("被误判了：{}",i);
                list.add(i);
            }
        }
        log.info("误判总量：{}",list.size());
    }

    public static void main(String[] args) {
        new GuavaBloomFilterService().guavaBloomFilter();
    }
}
