package com.deng.study.java;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/2/23 16:13
 */
public class SuperTest {


    // 先静态，后动态；先父类，后子类
    public static void main(String[] args) {
//        new Child();
        new Son();
    }
    static class Parent {
        static {
            System.out.println(1);
        }
        public Parent() {
            System.out.println(2);
        }
    }

    static class Child extends Parent {
        static {
            System.out.println(3);
        }
        public Child() {
            System.out.println(4);
        }
    }

}

class Grandpa {
    static {
        System.out.println("this is static code");
    }

    public Grandpa() {
        System.out.println("this is grandpa.");
    }

    public Grandpa(int age) {
        System.out.println("grandpa is " + age + " years old.");
    }

    private Height height = new Height("grandpa",1.5f);

    public static Gender gender = new Gender(true, "grandpa");
}

class Father extends Grandpa {

    public Father() {
        System.out.println("this is father.");
    }

    public Father(int age) {
        System.out.println("father is " + age + " years old.");
    }

    private Height height = new Height("father",1.6f);

    public static Gender gender = new Gender(true, "father");
}

class Son extends Father {

    public Son() {
        super(50);
        System.out.println("this is son.");
    }

    public Son(int age) {
        System.out.println("son is " + age + " years old.");
    }

    private Height height = new Height("son",1.8f);

    public static Gender gender = new Gender(true, "son");
}

class Height {
    public Height(String name, float height) {
        System.out.println("initializing "+ name +" height " + height + " meters.");
    }
}

class Gender {
    public Gender(boolean isMale) {
        if (isMale) {
            System.out.println("this is a male.");
        } else {
            System.out.println("this is a female.");
        }
    }

    public Gender(boolean isMale, String identify) {
        if (isMale) {
            System.out.println(identify + " is a male.");
        } else {
            System.out.println(identify + " is a female.");
        }
    }
}