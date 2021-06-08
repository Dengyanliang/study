package com.deng.study.java.java8.lambda;

import com.deng.study.domain.Employee;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/9 21:04
 */
public class FilterEmployeeByAge implements MyPredicate<Employee> {
    @Override
    public boolean test(Employee employee) {
        return employee.getAge() >= 30;
    }
}
