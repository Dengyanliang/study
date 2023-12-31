package com.deng.study.designpattern.creational.simplefactory;

/**
 * @Desc:
 * @Date: 2023/12/31 14:15
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        // v1 很依赖对应的实现类
//        Video vedio = new JavaVideo();
//        vedio.produce();

        // v2 使用简单工厂创建
        Video video = VideoFactory.getVideo("java");
        video.produce();

        Video video2 = VideoFactory.getVideo(JavaVideo.class);
        video2.produce();

    }
}
