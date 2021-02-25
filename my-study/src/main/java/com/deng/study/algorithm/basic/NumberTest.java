package com.deng.study.algorithm.basic;

import com.deng.study.source.StdOut;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/9/26 20:04
 */
@Slf4j
public class NumberTest {

    /**
     * 判断一个数是否为素数，素数是值大于1的自然数中，除了1和它本身之外没有别的因数的自然数
     * @param n
     * @return
     */
    private static boolean isPrime(int n){
        if(n <= 3)
            return n > 1;
        for(int i = 2; i * i <= n; i++){    // 比从2开始比较到N高效
            if(n % i ==0){
                return false;
            }
        }
        return true;
    }

    /**
     * 计算平方根
     * @param a
     * @param b
     * @return
     */
    private static double hypotenuse(double a,double b){
        return Math.sqrt(a * a + b * b);
    }


    /**
     * 将10进制转换为2进制，转化为其他进制也一样
     * @param n
     * @return
     */
    private static String tenToTwo(int n){
        // 先除以2，得到余数
        String s = "";
        for(int i = n; i > 0; i /= 2){
            log.info("i:{},i%2:{}", i, i % 2);
            s =( i % 2) + s;
        }

//        String s = Integer.toBinaryString(n);  // 转化为2进制

        return s;
    }

    /**
     * 将10进制转换为2进制，转化为其他进制也一样
     * @param n
     * @return
     */
    private static String tenToEight(int n){
        // 先除以8，得到余数
//        String s = "";
//        for(int i = n; i > 0; i /= 16){
//            log.info("i:{},i%8:{}", i, i % 16);
//            s =( i % 16) + s;
//        }
//        log.info("s:{}",s);

//        String s = Integer.toHexString(n);  // 转化为16进制
        String s = Integer.toOctalString(n);  // 转化为8进制

        return s;
    }


    /**
     * 二进制转十进制
     * @param n
     */
    private static void twoToTen(int n){
        int result = 0;
        int t = 0;         // 记录位数
        int remainder = 0; // 记录余数
        for(int i = n; i > 0 ; i /= 10){
            remainder = i % 10;
            log.info("i:{},remainder:{}",i,remainder);
            result += remainder * Math.pow(2,t); // 2的t次方
            t++;
        }
        log.info("result:{}",result);

    }


    private static void lg(int M){
        // log2^n < M n < 2^M
        for(int i = 0; 2*i < M; i++ ){

        }
    }

    /**
     * 将一个数倒序输出：第一种方法
     * @param i
     * @return
     */
    private static int reverse(int i){
        if(i == 0)
            return i;

        String temp = "";
        boolean flag = false;

        if(i < 0){
            flag = true;
            i = Math.abs(i); // 对于小于0的数字，求解其对应的绝对值
        }
        int a;
        while(i != 0){
            a = i % 10;
            temp = temp + a;
            i = i / 10;
        }

        int result = Integer.valueOf(temp);
        if(flag){
            result = result * -1;
        }
        return result;
    }

    /**
     * 将一个数倒序输出：第二种方法
     * @param i
     * @return
     */
    private static String temp = "";
    private static boolean flag;
    private static int reverse2(int i){
        if(i == 0)
            return i;

        if(i < 0){
            flag = true;
            i = Math.abs(i);
        }
        int a = i % 10;
        temp = temp + a;
        i = i / 10;
        reverse2(i);

        int result = Integer.valueOf(temp);
        if(flag){
            result = result * -1;
        }
        return result;
    }

    /**
     * 将一个数倒序输出：第三种方法
     * @param i
     * @return
     */
    private static int reverse3(int i){
        if(i == 0)
            return i;

        if(i < 0){
            flag = true;
            i = Math.abs(i);
        }

        String temp = "";
        String str = String.valueOf(i);
        char[] ch = str.toCharArray(); // 将字符串转化为字符数组
        for(int index = ch.length; index > 0 ; index--){ // 从后往前倒序输出
            temp = temp + ch[index-1];
        }

        int result = Integer.valueOf(temp);
        if(flag){
            result = result * -1;
        }
        return result;
    }

    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 示例1：输入: [2,2,1]
     * 输出：1
     *
     * 示例2：输入: [4,1,2,1,2]
     * 输出：4
     * @param nums
     */
    private static int unique(int[] nums){
        List<Integer> list = new ArrayList<>();

        for(int i = 0; i < nums.length; i++){
            if(list.contains(nums[i]) == false){
                list.add(nums[i]);
            }else{
                // 如果不用Integer转化的话，则是根据索引进行删除；如果使用Integer进行转化，则是根据对象进行删除
                list.remove((Integer)nums[i]);
            }
        }
        return list.get(0);
    }

    /**
     * 第二种办法：使用异或运算进行操作
     * a ^ a = 0
     * a ^ b = b ^ a
     * a ^ b ^ a = b
     * @param nums
     * @return
     */
    private static int unique2(int[] nums){
        int result = 0;
        for(int i : nums){
            result = result ^ i;
            System.out.println("-->"+result);
        }
        return result;
    }

    public static void main(String[] args) {
//        int result = reverse3(18213);
        int[] nums = {4,4,2,2,1};
        unique2(nums);
        System.out.println(unique(nums));


//        boolean prime = isPrime(9);
//        log.info("prime:{}",prime);
//
//        double hypotenuse = hypotenuse(5,4);
//        log.info("hypotenuse:{}",hypotenuse);
//
//        String tenToTwo = tenToTwo(15);
//        log.info("tenToTwo:{}",tenToTwo);
//
//        String tenToEight = tenToEight(15);
//        log.info("tenToEight:{}",tenToEight);
//
//        twoToTen(011);

    }
}
