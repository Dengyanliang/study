package com.deng.study.designpattern.principle.singleresponsiblity;

import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: dengyanliang
 * @Date: 2023/12/28 22:25
 * @Desc:
 */
public class Bird {
    public void mainMoveMode(String birdName){
        if(StringUtils.equals(birdName,"鸵鸟")){
            System.out.println(birdName + "用脚走");
        }else{
            System.out.println(birdName + "用翅膀飞");
        }
    }
}
