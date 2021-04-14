package com.deng.study.java.java8.optional;

import com.deng.study.java.java8.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

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

//        Optional<Employee> optional = Optional.empty();
//        System.out.println("optional:"+optional);
        Optional<Employee> optional = Optional.of(new Employee());
        System.out.println("optionalEmployee:"+optional);

        Optional<Employee> optionalEmployee1 = Optional.ofNullable(new Employee()); // empty 和 of 结合起来
        System.out.println("optionalEmployee1:"+optionalEmployee1);

//        Employee employee1 = optional.get();
//        Employee employee1 = optional.isPresent();
//        System.out.println("employee1:"+employee1);

//        Employee employee = optional.orElseThrow(()->new RuntimeException("error"));
//        System.out.println("===employee:"+employee);

        boolean flag = optional.isPresent(); // 和 ifPresent TODO
        System.out.println("flag:" + flag);

        if(optional.isPresent()){ // 将在optional存在的时候返回true，否则返回false

        }

        // 会在值存在的时候，执行给定的代码块
        optional.ifPresent((e)->System.out.println(e.getName()+"*****"));

        // orElse(T other) 会在值存在的时候返回值，否则返回一个默认值；如果执行方法，不论原值是否存在，都会执行默认
        Employee employee1 = optional.orElse(new Employee("刘备",42)); // orElseGet的区别
        System.out.println("--employee1:"+employee1);

        // orElseGet(T other) 是orElse的延迟加载版，只有值不存在的时候才会执行方法体内的内容。效率高
        Employee employee2 = optional.orElseGet(()->new Employee("关羽",40));
        System.out.println("--employee2:"+employee2);

        optional = Optional.of(employee1);
        // 在optional为空的时候，会抛出异常
        String names = optional.filter(e->e.getAge()>30).map(Employee::getName).orElseThrow(()->new RuntimeException("error"));
        System.out.println("names:" + names);
    }


    @Test
    public void test2(){
        /**
         * a执行了方法
         * b执行了方法
         * 1
         * 1
         */
        List<Integer> list = new ArrayList<>();
        Integer sum = list.stream().reduce(Integer::sum).orElse(get("a"));
        Integer sum2 = list.stream().reduce(Integer::sum).orElseGet(()->get("b"));
        System.out.println(sum);
        System.out.println(sum2);

        System.out.println("-------------");

        /**
         * a 执行了方法
         * 1
         * 1
         */
        list = new ArrayList<>();
        list.add(1);
        sum = list.stream().reduce(Integer::sum).orElse(get("a"));
        sum2 = list.stream().reduce(Integer::sum).orElseGet(()->get("b"));
        System.out.println(sum);
        System.out.println(sum2);
    }

    private static int get(String name){
        System.out.println(name + " 执行了方法");
        return 1;
    }


    @Test
    public void test5(){
        Trade trade1 = new Trade("张三","上海");
        Trade trade2 = new Trade("李四","北京");
        Trade trade3 = new Trade("王五","上海");
        Trade trade4 = new Trade("赵六","上海");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(trade4, 2011, 300),
                new Transaction(trade1, 2012, 1000),
                new Transaction(trade1, 2011, 400),
                new Transaction(trade2, 2012, 710),
                new Transaction(trade2, 2012, 700),
                new Transaction(trade3, 2012, 950)
                );

        // 找出2011年发生的交易，并按交易排序（从低到高）
        List transactionsList = transactions.stream().filter(t -> t.getYear()==2011).
                sorted(Comparator.comparing(transaction -> transaction.getValue())).collect(Collectors.toList());
        System.out.println(transactionsList);

        // 交易员都在哪些城市工作过
        List<String> cityList = transactions.stream().map(t->t.getTrade().getCity()).distinct().collect(Collectors.toList());
        System.out.println(cityList);

        // 查找所有来自上海的交易员，并按照姓名排序
        List<Trade> tradesList = transactions.stream().filter(t->t.getTrade().getCity().equals("上海")).
                map(transaction -> transaction.getTrade()).distinct().
                sorted(Comparator.comparing(trade -> trade.getName())).collect(Collectors.toList());
        System.out.println(tradesList);

        // 返回所有交易员的姓名字符串，按字母排序


        // 有没有交易员在北京工作的

        // 打印生活在上海的交易员的所有交易额

        // 所有交易中，最高的交易额是多少

        // 找到交易额最小的交易

    }
}
