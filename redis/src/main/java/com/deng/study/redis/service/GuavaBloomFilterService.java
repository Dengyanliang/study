package com.deng.study.redis.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Desc: 布隆过滤器，解决缓存穿透
 * @Auther: dengyanliang
 * @Date: 2023/2/28 09:44
 */
@Slf4j
@Service
public class GuavaBloomFilterService {

    private static final int _1W = 10000;
    private static final int _10W = 10 * _1W;
    // 定义布隆过滤器的初始容量
    private static final int SIZE = 100 * _1W;
    // 误判率，默认是0.03；如果误判率越小，那么需要的bit位越多，并且需要的hash函数也越多，程序性能也更低
    // fpp=0.000001，numBits=28755175 numHashFunctions=20
    // fpp=0.01，numBits=9585058 numHashFunctions=7
    // fpp=0.03，numBits=7298440 numHashFunctions=5
    private static double fpp = 0.01;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), SIZE, fpp);

    /**
     * 初始化数据
     */
//    @PostConstruct
    public void initData(){
//        List<Integer> list = new ArrayList<>(); // keypoint 查询数据库获取表的主键
        List<Integer> list = Arrays.asList(1,2,3);
        // 遍历数据，放入到布隆过滤中中
        for (Integer id : list) {
            bloomFilter.put(id);
        }
    }

    /**
     * 判断该id是否在布隆过滤器中，防止缓存穿透
     *  1）如果数据在布隆过滤器中，则有可能存在，需要进一步查询数据库
     *  2）如果没有在布隆过滤中，则一定不存在，直接返回
     * @param id
     * @return
     */
    public boolean mightContain(Integer id){
        return bloomFilter.mightContain(id);
    }

    /**
     * 新增的数据添加到布隆过滤器中
     * @param id
     */
    public void putToBloomFilter(Integer id){
        bloomFilter.put(id);
    }

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
                // 判断布隆过滤器中是否包含i
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
