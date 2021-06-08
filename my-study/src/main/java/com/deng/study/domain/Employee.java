package com.deng.study.domain;

import lombok.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/9 20:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String name;
    private int age;
    private Double salary;
    private Status status;


    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static List<Employee> getEmployees(){
        // 创建集合
        List<Employee> employees = Arrays.asList(
                new Employee("张三",30,3333.33,Status.FREE),
                new Employee("李四",43,9999.99,Status.BUSY),
                new Employee("王五",21,2222.22,Status.VOCATION),
                new Employee("赵六",15,1555.55,Status.FREE),
                new Employee("田七",53,5555.55,Status.BUSY),
                new Employee("田七",53,5555.55,Status.BUSY),
                new Employee("田七",53,5555.55,Status.BUSY)
        );
        return employees;
    }


    public enum Status{
        FREE,
        BUSY,
        VOCATION
        ;
    }

}
