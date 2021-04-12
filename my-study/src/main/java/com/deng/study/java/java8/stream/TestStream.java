package com.deng.study.java.java8.stream;

import com.deng.study.java.java8.Employee;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream
 * 1、自己不会存储元素，
 * 2、不会改变源对象，会返回一个持有结果的stream
 * 3、操作是延迟执行的，也就是等到需要结果的结果的时候才会执行
 *
 * 三个步骤：
 * 1、创建Stream：数据源（数组、集合），获取一个流
 * 2、中间操作：一个中间操作链，对数据源的数据进行处理
 * 3、终止操作：执行中间操作链，并产生结果
 *
 *
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/4/11 19:42
 */
public class TestStream {

    // 创建集合
    List<Employee> employees = Employee.getEmployees();


    /**
     * 创建流
     * 1、集合获取流
     * 2、数组获取流
     * 3、静态方法获取流
     * 4、创建无限流：迭代 && 生成
     */
    @Test
    public void test1(){
        // 1、集合获取流，stream() 或者parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();
        System.out.println(stream);

        // 2、数组获取流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(employees);
        System.out.println(stream1);

        // 3、静态方法获取流
        Stream<String> stream2 = Stream.of("aa","bb","cc","ddd");
        System.out.println(stream2);

        // 4、创建无限流
        // 迭代
        Stream<Integer> stream3 = Stream.iterate(0,x->x+1);
        stream3.limit(10).forEach(System.out::println);

        // 生成
        Stream.generate(()-> Math.random())
                .limit(10)
                .forEach(System.out::println);
    }

    /**
     * 中间操作
     * 1、筛选与切片
     *      filter -- 过滤
     *      limit -- 限制，取前几个
     *      skip  -- 跳过元素，与limit互补
     *      distinct -- 去重，需要重写对象的equals()和hashcode()方法
     */
    @Test
    public void test2(){
        employees.stream().filter((e) ->e.getAge()> 30).forEach(System.out::println);
        System.out.println("----------");

        employees.stream().limit(2).forEach(System.out::println);
        System.out.println("**********");

        employees.stream().skip(3).forEach(System.out::println);
        System.out.println("===========");

        employees.stream().distinct().forEach(System.out::println);

        Stream<Employee> stream = employees.stream();
        Stream<String> stream1 = stream.map(Employee::getName);
        stream1.forEach(System.out::println);
    }


    /**
     * 中间操作
     * 2、映射
     *      map -- 接收lambda，将元素转换成其他形式或提取信息，接收一个函数作为参数，该函数会被映射到每个元素上，并将其映射成一个新的元素。
     *      flatMap -- 接收一个函数作为参数，将流中的每个值都转换成另一个流，然后把所有的流连在一起
     *      注意：这两个操作类似于add和addAll
     */
    @Test
    public void test3(){
        // map
        List<String> list = Arrays.asList("aa","bb","cc","ddd");
        list.stream().map((s) -> s.toUpperCase()).forEach(System.out::println);

        System.out.println("############");

        // 注意map 与 flatMap的区别
        list.stream().map(s->s).forEach(System.out::println); // aa bb cc ddd

        System.out.println("&&&&&&&&&&&&&&&");
        list.stream().flatMap((s) -> filterCharacter(s)).forEach(System.out::println); // a a b b c c d d d


        List list3 = new ArrayList();
        list3.add("232");
        list3.add("999");

        List<String> list2 = Arrays.asList("WW","GG");
        list3.add(list2); // 把list2整体添加到集合中
        System.out.println("----"+list3); // [232, 999, [WW, GG]]

        list3.addAll(list2); // 把list2中的元素取出来，然后再添加到集合中
        System.out.println("--->"+list3); // [232, 999, WW, GG]
    }

    private static Stream<Character> filterCharacter(String str){
        List<Character> result = new ArrayList<>();
        for (char c : str.toCharArray()) {
            result.add(c);
        }
        return result.stream();
    }



    /**
     * 中间操作
     * 3、排序
     *      sorted()--自然排序(Comparable)，按照字典排序
     *      sorted(Comparator com)-- 定制排序
     */
    @Test
    public void test4(){
        List<String> list = Arrays.asList("eee","ff","aa","cc","ddd");
        list.stream().sorted().forEach(System.out::println);

        System.out.println("----------");

        list.stream().sorted((x,y)->y.compareTo(x)).forEach(System.out::println); // 逆序排列
        System.out.println("********");
        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        System.out.println("===========");

        employees.stream().sorted((x,y)->{
            if(x.getAge() == y.getAge()){
                return x.getName().compareTo(y.getName()); // compareTo需要传入的是对象
            }else{
                return Integer.compare(x.getAge(),y.getAge()); // compare需要传入的是原始类型
            }
        }).forEach(System.out::println);

        System.out.println("^^^^^^^^^^");

        employees.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).forEach(System.out::println);
    }

    /**
     * 终止操作
     *   1、allMatch  检查是否匹配所有元素
     *      anyMatch  检查是否匹配任意元素
     *      noneMatch 检查是否不匹配所有元素
     *      findFirst 返回第一个
     *      findAny   返回任意一个值
     *      count     返回数量
     *      max       返回最大值
     *      min       返回最小值
     */
    @Test
    public void test5(){
        boolean flag = employees.stream().allMatch(x->x.getStatus() == Employee.Status.BUSY);
        System.out.println("flag:"+ flag);

        flag = employees.stream().anyMatch(x->x.getAge()==30);
        System.out.println("flag:"+ flag);

        flag = employees.stream().noneMatch(x->x.getName().equals("钱八"));
        System.out.println("flag:"+ flag);

        Optional<Employee> emp = employees.stream().filter(x->x.getStatus()== Employee.Status.BUSY).findFirst();
        System.out.println(emp);

        emp = employees.stream().filter(x-> x.getStatus().equals(Employee.Status.BUSY)).findAny();
        System.out.println(emp);


        long count = employees.stream().filter(x->x.getStatus().equals(Employee.Status.BUSY)).count();
        System.out.println("count:" + count);

        emp = employees.stream().max(Comparator.comparing(Employee::getAge));
        System.out.println(emp);

        emp = employees.stream().min(Comparator.comparing(Employee::getAge));
        System.out.println(emp);
    }

    /**
     * 终止操作
     * 2、reduce
     * 3、collectors
     */
    @Test
    public void test6(){

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Integer sum = list.stream().reduce(0,(x,y)->x+y);
        System.out.println(sum);

        Optional<Integer> sum2 = list.stream().reduce((x,y)->x+y);
        System.out.println(sum2.get());

        List ages = employees.stream().filter(x->x.getAge()>30).map(Employee::getName).collect(Collectors.toList());
        System.out.println(ages);

        Set names = employees.stream().map(Employee::getName).collect(Collectors.toSet());
        System.out.println(names);

        String s = employees.stream().map(Employee::getName).collect(Collectors.joining(",","***","==="));
        System.out.println(s);

        HashSet set = employees.stream().map(Employee::getName).collect(Collectors.toCollection(HashSet::new));
        System.out.println(set);

        // 分组
        Map<String,List<Employee>> ageEmployees = employees.stream().collect(Collectors.groupingBy(e->{
            if(e.getAge() > 30){
                return "青年";
            }else{
                return "中年";
            }
        }));
        System.out.print(ageEmployees+ " ");
        System.out.println();

        Map<Integer,List<Integer>> ageMap = employees.stream().map(Employee::getAge).collect(Collectors.groupingBy(x->{
            if(x > 20){
                return 1;
            }else{
                return 2;
            }
        }));
        System.out.println(ageMap);


        Map<String,List<String>> nameMap = employees.stream().map(Employee::getName).collect(Collectors.groupingBy(s1->{
            if(s1.equals("张三")){
                return "sss";
            }else{
                return "xxx";
            }
        }));
        System.out.println(nameMap);


        //分区：只有true 和 false
        Map<Boolean,List<Employee>> booleanListMap = employees.stream().collect(Collectors.partitioningBy((e)->e.getAge()>30));
        System.out.println(booleanListMap);

        long count = employees.stream().filter(e->e.getAge()>30).map(Employee::getName).collect(Collectors.counting());
        System.out.println("count:"+count);

        // 统计
        IntSummaryStatistics intSummaryStatistics = employees.stream().collect(Collectors.summarizingInt(Employee::getAge));
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getAverage());
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getMin());
        System.out.println(intSummaryStatistics.getSum());

        DoubleSummaryStatistics summaryStatistics2 = employees.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(summaryStatistics2.getAverage());

        // key=name, value=age
        Map<String,Integer> nameAgeMap =  employees.stream().collect(Collectors.toMap(Employee::getName,Employee::getAge,(e1,e2)->e1));
        System.out.println(nameAgeMap);

        // key=name, value=object
        // 去重，传入e1,e2，输出e2。不去重会报错
        Map<String,Employee> employeeMap = employees.stream().collect(Collectors.toMap(Employee::getName,e->e,(e1,e2)->e2));
        System.out.println(employeeMap);

        // key=name,value=object
        Map<String,Employee.Status> nameStatusMap = employees.stream().
                collect(Collectors.toMap(Employee::getName,Employee::getStatus,(e1,e2)->e2,TreeMap::new));
        System.out.println(nameStatusMap);


        Map<String,Employee.Status> nameStatusMap2 = employees.stream().
                collect(Collectors.toMap(Employee::getName,Employee::getStatus,(e1,e2)->e2,LinkedHashMap::new));
        System.out.println(nameStatusMap2);

    }



}
