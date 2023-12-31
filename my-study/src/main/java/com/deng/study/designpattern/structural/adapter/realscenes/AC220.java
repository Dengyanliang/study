package com.deng.study.designpattern.structural.adapter.realscenes;

/**
 * @Desc: 220V交流电，被适配者
 * @Date: 2023/12/31 16:37
 * @Auther: dengyanliang
 */
public class AC220 {
    public int outputAC220V(){
        int output = 220;
        System.out.println("输出交流电"+output+"V");
        return output;
    }
}
