package com.deng.study.designpattern.behavioral.chainofresponsibility;

import org.apache.commons.lang3.StringUtils;

/**
 * @Desc:
 * @Date: 2023/12/30 11:24
 * @Auther: dengyanliang
 */
public class VedioApprover extends Approver{

    @Override
    public void deploy(CRCourse course) {
        if(StringUtils.isNotBlank(course.getVideo())){
            System.out.println(course.getName()+"含有视频，批准");
            if(approver != null){
                approver.deploy(course);
            }
        }else{
            System.out.println(course.getName()+"不含有视频，不予批准，流程结束");
        }
    }
}
