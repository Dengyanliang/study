---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by yanliangdeng.
--- DateTime: 2024/1/5 下午4:43
---

print 'hello lua'

-- 用作限流的key
local key = 'my key'

-- 限流的最大阈值=2
local limit = 2

-- 当前的流量大小
local currentLimit = 2

-- 是否超过限流标准
if currentLimit + 1 > limit then
    print("reject")
    return false
else
    print("accept")
    return true
end