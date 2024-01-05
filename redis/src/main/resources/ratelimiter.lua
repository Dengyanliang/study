---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by yanliangdeng.
--- DateTime: 2024/1/5 下午4:43
---

-- 用作限流的key，获取方法签名
local methodKey = KEYS[1]
redis.log(redis.LOG_DEBUG,'key is',methodKey)

-- 调用脚本传入的限流大小，即限流的最大阈值
local limit = tonumber(ARGV[1])

-- 获取当前的流量大小，如果没有则返回默认值0
local count = tonumber(redis.call('get',methodKey) or "0")

-- 是否超过限流阈值
if count + 1 > limit then
    -- 拒绝访问服务
    return false
else
    -- 没有超过限流阈值
    -- 设置当前访问的数量+1
    redis.call("INCRBY",methodKey,1)
    -- 设置过期时间，这里为1秒
    redis.call("EXPIRE",methodKey,1)
    return true
end