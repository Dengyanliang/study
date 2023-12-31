package com.deng.study.designpattern.structural.adapter.realscenes;

/**
 * @Desc: 电源适配器
 * @Date: 2023/12/31 16:40
 * @Auther: dengyanliang
 */
public class PowerAdapter implements DC5{
    private AC220 ac220 = new AC220();

    @Override
    public int outputDC5V() {
        int adapterInput = ac220.outputAC220V();
        // 这是一个变压器
        int adapterOutput = adapterInput / 44;
        System.out.println("使用电源适配器PowerAdapter，输入AC:"+adapterInput+"V，输出DC:"+adapterOutput+"V");
        return adapterOutput;
    }
}
