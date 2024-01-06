package com.deng.study.redis.common;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.UnknownHostException;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/10/3 16:29
 */
@Slf4j
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWait;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private int minIdle;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // json序列化配置
//        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 设置任何字段可见
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 存入到redis的对象是有类型的，这样方便从redis中取出来的时候，直接转换成对应的对象
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jsonRedisSerializer.setObjectMapper(objectMapper);

        // string的key采用string的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // hash的key也采用string的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());

        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // string的value序列化方式采用json
        template.setValueSerializer(jsonRedisSerializer);
        // hash的value序列化方式采用json
        template.setHashValueSerializer(jsonRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 创建执行lua脚本的类，DefaultRedisScript的接口是RedisScript
     * @return
     */
    @Bean
    public DefaultRedisScript loadRedisScript(){
        DefaultRedisScript redisScript = new DefaultRedisScript();
        // 设置加载lua脚本的位置
        redisScript.setLocation(new ClassPathResource("ratelimiter.lua"));
        // 设置返回类型
        redisScript.setResultType(java.lang.Boolean.class);
        return redisScript;
    }

    @Bean
    public Redisson redisson(){
        Config config = new Config();
        String address = "redis://" + host + ":" + port;
        config.useSingleServer() // 单机模式
                .setAddress(address) // redis服务器地址
                .setDatabase(0) // 指定的数据库编号
                .setPassword(password); // 密码

        return (Redisson)Redisson.create(config);
    }

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setMaxTotal(maxActive);
        config.setMinIdle(minIdle);

        JedisPool jedisPool = new JedisPool(config,host,port,timeout,null);
        log.info("jedisPool创建成功，地址：{}，端口：{}",host,port);
        return jedisPool;
    }
}
