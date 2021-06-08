package com.deng.study.java.java8.lambda;

import com.deng.study.domain.Apple;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/11 11:38
 */
public class TestApple {

    @Test
    public void test() {
        // 创建集合
        List<Apple> apples = Arrays.asList(
                new Apple("red", 30, "中国"),
                new Apple("green", 43, "日本"),
                new Apple("yellow", 21, "美国")
        );

        // 使用策略设计模式
        apples.sort(new AppleComparator());
        System.out.println("1:" + apples);

        // 使用匿名内部类
        apples.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println("2:" + apples);

        // 使用lambda
        apples.sort((Apple o1, Apple o2) -> o1.getWeight().compareTo(o2.getWeight()));
        System.out.println("3:" + apples);

        // 简化
        apples.sort((o1, o2) -> o1.getWeight().compareTo(o2.getWeight()));
        System.out.println("4:" + apples);

        // 再简化
        apples.sort(Comparator.comparing((Apple a) -> a.getWeight()));
        System.out.println("5:" + apples);

        // 使用方法引用
        apples.sort(Comparator.comparing(Apple::getWeight));
        System.out.println("6:" + apples);

        // 逆序
        apples.sort(Comparator.comparing(Apple::getWeight).reversed());
        System.out.println("7:" + apples);

        // 逆序，如果两个苹果的重量一样大，则使用color进行比较
        apples.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        System.out.println("8:" + apples);

        Predicate<Apple> redApple = new Predicate<Apple>() {
            @Override
            public boolean test(Apple apple) {
                return apple.getColor().equals("red");
            }
        };

        // 以下是谓词操作 negate and or 同 离散中的 与、或、非  && ||  ！
        // 不是红苹果
        Predicate<Apple> notRedApple = redApple.negate();
        System.out.println("9:" + notRedApple);

        // 既是红苹果，又比较重
        Predicate<Apple> redAndHeavyApple = redApple.and(apple -> apple.getWeight() > 30);
        System.out.println("10:" + redAndHeavyApple);

        // 要么是30g的重苹果，要么是绿苹果
        Predicate<Apple> redAndHeavyOrGreenApple = redApple.and(apple -> apple.getWeight() > 30).
                or(apple -> apple.getColor().equals("green"));
        System.out.println("11:" + redAndHeavyOrGreenApple);
    }
}

