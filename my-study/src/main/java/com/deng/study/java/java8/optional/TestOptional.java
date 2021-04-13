package com.deng.study.java.java8.optional;

import com.deng.study.java.java8.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/12 23:24
 */
public class TestOptional {
    // 创建集合
    List<Employee> employees = Employee.getEmployees();

    @Test
    public void test1() {

        Optional<Employee> optional = Optional.empty();
        System.out.println("optional:"+optional);
//
        Optional<Employee> optionalEmployee = Optional.of(new Employee());
        System.out.println("optionalEmployee:"+optionalEmployee);

        Optional<Employee> optionalEmployee1 = Optional.ofNullable(new Employee()); // empty 和 of 结合起来
        System.out.println("optionalEmployee1:"+optionalEmployee1);

//        Employee employee1 = optional.get();
//        Employee employee1 = optional.isPresent();
//        System.out.println("employee1:"+employee1);

//        Employee employee = optional.orElseThrow(()->new RuntimeException("error"));
//        System.out.println("===employee:"+employee);

        boolean flag = optional.isPresent(); // 和 ifPresent TODO
        System.out.println("flag:" + flag);

        if(optional.isPresent()){

        }

        optional.ifPresent((e)->System.out.println(e.getName()+"*****"));

        Employee employee1 = optional.orElse(new Employee("刘备",42)); // orElseGet的区别
        System.out.println("--employee1:"+employee1);

        Employee employee2 = optional.orElseGet(()->new Employee("关羽",40));
        System.out.println("--employee2:"+employee2);

        optional = Optional.of(employee1);
        String names = optional.filter(e->e.getAge()>30).map(Employee::getName).orElseThrow(()->new RuntimeException("error"));
        System.out.println("names:" + names);
    }
}
