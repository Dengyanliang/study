package com.deng.study.design_pattern.strategy;

/**
 * @Desc:
 * @Date: 2023/12/29 15:07
 * @Auther: dengyanliang
 */
public class FanXianPromotionStrategy implements PromotionStrategy{
    @Override
    public void doPromotion() {
        System.out.println("返现促销");
    }
}
