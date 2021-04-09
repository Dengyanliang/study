package com.deng.study.java.java8;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/9 21:03
 */
public class FilterEmployeeBySalary implements MyPredicate<Employee>{
    @Override
    public boolean test(Employee employee) {
        return employee.getSalary() >= 5000;
    }
}
