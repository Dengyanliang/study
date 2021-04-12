package com.deng.study.java.java8.lambda;


import com.deng.study.java.java8.Employee;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/9 20:12
 */
public class Test {

    private static void test1(){
        // 匿名内部类实现
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
             return Integer.compare(o1,o2);
            }
        };
        TreeSet<Integer> treeSet = new TreeSet(comparator);


        // 使用lambda表达式
        Comparator<Integer> comparator1 = (o1,o2) -> Integer.compare(o1,o2);
        TreeSet<Integer> treeSet1 = new TreeSet<>(comparator1);
    }

    /******* 获取员工信息：第一种做法，针对每个业务写一个方法，进行过滤  ********/
    // 获取当前公司中员工年龄大于30岁的员工信息
    private static List<Employee> filterEmployeeByAge(List<Employee> list){
        List<Employee> result = new ArrayList<>();
        for (Employee employee : list) {
            if(employee.getAge() >= 30){
                result.add(employee);
            }
        }
        return result;
    }

    // 获取当前公司中员工工资大于50千的员工信息
    private static List<Employee> filterEmployeeBySalary(List<Employee> list){
        List<Employee> result = new ArrayList<>();
        for (Employee employee : list) {
            if(employee.getSalary() >= 5000){
                result.add(employee);
            }
        }
        return result;
    }


    /******* 获取员工信息：第二种做法，使用策略设计模式，进行过滤  ********/
    private static List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> myPredicate){
        List<Employee> result = new ArrayList<>();
        for (Employee employee : list) {
            if(myPredicate.test(employee)){
                result.add(employee);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        // 创建集合
        List<Employee> employees = Employee.getEmployees();

        System.out.println("第一种，单独为创建多个方法的方式：");
        List<Employee> result1 = filterEmployeeByAge(employees);
        System.out.println("result1:"+result1);

        List<Employee> result2 = filterEmployeeBySalary(employees);
        System.out.println("result2:" + result2);

        System.out.println("第二种，使用策略模式的方式：");
        List<Employee> result3 = filterEmployee(employees,new FilterEmployeeByAge());
        System.out.println("result3:" + result3);

        List<Employee> result4 = filterEmployee(employees,new FilterEmployeeBySalary());
        System.out.println("result4:" + result4);

        System.out.println("第三种，使用匿名内部类的方式：");
        List<Employee> result5 = filterEmployee(employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge() < 30;
            }
        });
        System.out.println("result5:" + result5);

        System.out.println("第四种，使用lambda表达式的方式：");
        List<Employee> result6 = filterEmployee(employees,(employee) -> employee.getAge() >= 30);
        System.out.println("result6:" + result6);

        System.out.println("第五种，使用stream的方式：");
        List<Employee> result7 = employees.stream().filter(employee -> employee.getAge() >= 30)
                .limit(2).collect(Collectors.toList());
        System.out.println("result7:" + result7);
    }



}
