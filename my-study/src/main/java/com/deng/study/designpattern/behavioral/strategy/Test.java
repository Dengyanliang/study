package com.deng.study.designpattern.behavioral.strategy;

/**
 * @Desc:
 * @Date: 2023/12/29 15:12
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        // v1 版本
//        PromotionActivity promotionActivity618 = new PromotionActivity(new ManJianPromotionStrategy());
//        PromotionActivity promotionActivity1111 = new PromotionActivity(new FanXianPromotionStrategy());
//
//        promotionActivity618.execute();
//        promotionActivity1111.execute();

        // v2 版本，这里还是需要使用大量的if判断，代码不是很优雅
//        PromotionActivity promotionActivity = null;
//        String promotionKey = "ManJian";
//        if(StringUtils.equals(promotionKey,"ManJian")){
//            promotionActivity = new PromotionActivity(new ManJianPromotionStrategy());
//        }else if(StringUtils.equals(promotionKey,"FanXian")){
//            promotionActivity = new PromotionActivity(new FanXianPromotionStrategy());
//        }else{
//            throw new RuntimeException("类型不对");
//        }
//        promotionActivity.execute();

        // v3 版本 ，使用工厂模式和策略模式实现
        String promotionKey = PromotionKey.FanXian;
        // 获取对应的策略
        PromotionStrategy promotionStrategy = PromotionStrategyFactory.getPromotionStrategy(promotionKey);
        PromotionActivity promotionActivity = new PromotionActivity(promotionStrategy);
        // 由活动去执行相应的策略
        promotionActivity.execute();
    }


}
