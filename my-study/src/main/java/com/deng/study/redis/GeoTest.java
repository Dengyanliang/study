package com.deng.study.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/3/3 16:20
 */
@Slf4j
@SpringBootTest
public class GeoTest {
    private static final String CITY = "上海";

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        String add = add();
        log.info("add：{}", add);

        Point position = position("莘庄");
        log.info("position：{}", position);

        String hash = hash("莘庄");
        log.info("hash：{}", hash);

        Distance distance = distance("莘庄","徐家汇");
        log.info("distance：{}", distance);

        GeoResults radiusBYxy = radiusBYxy();
        log.info("radiusBYxy：{}", radiusBYxy);

        GeoResults radiusByMember = radiusByMember();
        log.info("radiusByMember：{}", radiusByMember);
    }

    /**
     * 添加当前城市的一些测试地点
     * @return
     */
    public String add(){
        Map<String, Point> map = new HashMap<>();
        map.put("莘庄",new Point(121.392077,31.116976));
        map.put("徐家汇",new Point(121.443228,31.200668));
        map.put("上海南站",new Point(121.435865,31.159439));

        redisTemplate.opsForGeo().add(CITY,map);
        return map.toString();
    }

    /**
     * 获取经纬度坐标
     * @param member
     * @return
     */
    public Point position(String member){
        List<Point> positions = redisTemplate.opsForGeo().position(CITY, member);
        return positions.get(0);
    }

    /**
     * 通过geohash算法生成base32编码值
     * @param member
     * @return
     */
    public String hash(String member){
        List<String> geohash = redisTemplate.opsForGeo().hash(CITY, member);
        return geohash.get(0);
    }

    /**
     * 获取给定位置之间的距离
     * @param member1
     * @param member2
     * @return
     */
    public Distance distance(String member1, String member2) {
        Distance distance = redisTemplate.opsForGeo().distance(CITY, member1, member2, RedisGeoCommands.DistanceUnit.KILOMETERS);
        return distance;
    }


    /**
     * 通过经纬度查找给定位置附近的50条记录
     * @return
     */
    public GeoResults radiusBYxy(){
        Circle circle = new Circle(121.377542,31.149117, Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs args =
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortDescending().limit(50);
        GeoResults radius = redisTemplate.opsForGeo().radius(CITY, circle, args);
        return radius;
    }

    /**
     * 通过指定位置查找半径10公里之内的50条记录
     * @return
     */
    public GeoResults radiusByMember(){
        String member = "徐家汇";
        RedisGeoCommands.GeoRadiusCommandArgs args =
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().sortDescending().limit(50);
        Distance distance = new Distance(10,Metrics.KILOMETERS);
        GeoResults<RedisGeoCommands.GeoLocation<String>> geoResults = redisTemplate.opsForGeo().radius(CITY, member, distance, args);
        return geoResults;
    }
}
