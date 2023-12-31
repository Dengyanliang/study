package com.deng.study.designpattern.principle.liskovsubstitution;

/**
 * @Desc: 里氏替换原则
 * @Date: 2023/12/29 10:58
 * @Auther: dengyanliang
 */
public class Test {
    public static void resize(Rectangle rectangle){
        while (rectangle.getWidth() <= rectangle.getLength()){
            rectangle.setWidth(rectangle.getWidth()+1);
            System.out.println("宽度："+rectangle.getWidth()+",长度："+rectangle.getLength());
        }
        System.out.println("resize 方法结束 宽度："+rectangle.getWidth()+",长度："+rectangle.getLength());
    }


    public static void main(String[] args) {

        /***** v1 版本 begin *******/
//        Rectangle rectangle = new Rectangle();
//        rectangle.setLength(20);
//        rectangle.setWidth(10);
//        resize(rectangle);
//
//        Square square = new Square();
//        square.setSideLength(10);
//        resize(square);

        /***** v1 版本 end *******/

        /***** v2 版本 begin *******/
        // 这里会报错的原因是因为Square不适用与resize方法，
        // 因此也就没有破坏里氏替换原则
//        Square square = new Square();
//        square.setSideLength(10);
//        resize(square);
    }
}
