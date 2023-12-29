package com.deng.study.design_pattern.strategy;

/**
 * @Desc:
 * @Date: 2023/12/29 15:09
 * @Auther: dengyanliang
 */
public class PromotionActivity {
    private PromotionStrategy promotionStrategy;

    public PromotionActivity(PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void execute(){
        promotionStrategy.doPromotion();
    }
}
