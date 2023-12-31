package com.deng.study.designpattern.behavioral.templatemethod;

/**
 * @Desc:
 * @Date: 2023/12/30 10:21
 * @Auther: dengyanliang
 */
public abstract class ACourse {

    /**
     * 这里一定要声明为final类型，避免子类重写该方法，导致更改结构
     */
    protected final void makeCourse(){
        makePPT();
        makeVedio();
        if(needWriteArticle()){
            writeArticle();
        }
        packageCourse();
    }
    final void makePPT(){
        System.out.println("制作PPT");
    }

    final void makeVedio(){
        System.out.println("制作视频");
    }
    final void writeArticle(){
        System.out.println("*****编写手记*****");
    }

    /**
     * 是否需要编写手记，这是一个钩子方法，可以让子类更改该值
     * @return
     */
    protected boolean needWriteArticle(){
        return false;
    }
    protected abstract void packageCourse();

}
