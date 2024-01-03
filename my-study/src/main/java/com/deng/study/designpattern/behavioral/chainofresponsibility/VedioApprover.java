package com.deng.study.designpattern.behavioral.chainofresponsibility;

import org.apache.commons.lang3.StringUtils;

/**
 * @Desc:
 * @Date: 2023/12/30 11:24
 * @Auther: dengyanliang
 */
public class VedioApprover extends Approver{

    @Override
    public void deploy(CRCourse order) {
        if(StringUtils.isNotBlank(order.getVideo())){
            System.out.println(order.getName()+"含有视频，批准");
            if(approver != null){
                approver.deploy(order);
            }
        }else{
            System.out.println(order.getName()+"不含有视频，不予批准，流程结束");
        }
    }
}
