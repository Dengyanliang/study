package com.deng.study.designpattern.creational.simplefactory;

import org.apache.commons.lang3.StringUtils;

/**
 * @Desc: 简单工厂，又叫静态工厂
 * @Date: 2023/12/31 14:17
 * @Auther: dengyanliang
 */
public class VideoFactory {

    /**
     * 根据传入的类型，创建相应的对象
     * keypoint 这里符合开闭原则
     * @param clazz
     * @return
     */
    public static Video getVideo(Class clazz){
        try {
            Video o = (Video)Class.forName(clazz.getName()).newInstance();
            return o;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 创建对应的对象
     * keypoint 不符合开闭原则，每次新增一种类型的时候，还是需要修改这里的if代码
     * @param type
     * @return
     */
    public static Video getVideo(String type){
        if (StringUtils.equalsIgnoreCase(type,"java")) {
            return new JavaVideo();
        }else if(StringUtils.equalsIgnoreCase(type,"python")){
            return new PythonVideo();
        }else{
            throw new RuntimeException("没有找到对应的类型");
        }
    }
}
