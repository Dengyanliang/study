package com.deng.study.design_pattern.strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc: 使用工厂模式和策略模式实现
 * @Date: 2023/12/29 15:20
 * @Auther: dengyanliang
 */
public class PromotionStrategyFactory {
    private static Map<String,PromotionStrategy> map = new HashMap<>();
    static {
        map.put(PromotionKey.FanXian,new FanXianPromotionStrategy());
        map.put(PromotionKey.ManJian,new ManJianPromotionStrategy());
        map.put(PromotionKey.LiJian,new LiJianPromotionStrategy());
    }
    //
    private static final PromotionStrategy NON_PROMOTION = new EmptyPromotionStrategy();

    private PromotionStrategyFactory(){

    }

    public static PromotionStrategy getPromotionStrategy(String promotionKey){
        PromotionStrategy promotionStrategy = map.get(promotionKey);
        // 第一种写法，还是需要针对null的情况去new对象
//        return promotionStrategy == null ? new EmptyPromotionStrategy() : promotionStrategy;
        // 第二种写法，定义static final类型的空PromotionStrategy的实例
        return promotionStrategy == null ? NON_PROMOTION : promotionStrategy;
    }
}
