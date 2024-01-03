package com.deng.study.designpattern.behavioral.observer;

/**
 * @Desc:
 * @Date: 2023/12/30 10:55
 * @Auther: dengyanliang
 */
public class Test {
    public static void main(String[] args) {
        OCourse order = new OCourse("Java设计模式精讲");
        Teacher teacher1 = new Teacher("Kevin");
        Teacher teacher2 = new Teacher("Timu");

        // 给课程添加一个观察者，这里就是teacher观察
        order.addObserver(teacher1);
        order.addObserver(teacher2);

        // 产生问题
        Question question = new Question();
        question.setUserName("张三");
        question.setQuestionContent("Java代码如何优化");

        order.produceQuestion(question);

    }
}
