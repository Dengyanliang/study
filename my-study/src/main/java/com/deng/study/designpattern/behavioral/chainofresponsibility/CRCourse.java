package com.deng.study.designpattern.behavioral.chainofresponsibility;

import lombok.Data;

/**
 * @Desc: 责任链模式
 * @Date: 2023/12/30 11:20
 * @Auther: dengyanliang
 */
@Data
public class CRCourse {
    private String name; // 课程名称
    private String article; // 手记
    private String video; // 视频
}
