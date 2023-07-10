package com.deng.study.datastru_algorithm.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2020/9/26 20:04
 */
@Slf4j
public class NumberTest {


    @Test
    public void middleTest(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(2);
        list.add(4);
        list.add(6);

        Collections.sort(list);
        double middle = 0;
        if(list.size() % 2 == 1){ // 奇数
            middle = list.get(list.size()/2);
        }else{                    // 偶数
            // 加0.0是将计算int类型转换为浮点类型
            middle = (list.get((list.size() / 2)) + list.get((list.size() - 1) / 2) + 0.0) / 2;
        }
        System.out.println("middle:"+middle);
    }

    /**
     * 判断一个数是否为素数
     */
    @Test
    public void testPrime(){
        boolean prime = isPrime(9);
        log.info("prime:{}",prime);
    }

    /**
     * 判断一个数是否为素数，素数是值大于1的自然数中，除了1和它本身之外没有别的因数的自然数
     * @param n
     * @return
     */
    private static boolean isPrime(int n){
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
     * 进制转换
     */
    @Test
    public void testConvert(){
        String tenToTwo = tenToTwo(15);
        log.info("tenToTwo:{}",tenToTwo);

        String tenToEight = tenToEight(15);
        log.info("tenToEight:{}",tenToEight);

        int twoToTen = twoToTen(011);
        log.info("twoToTen:{}",twoToTen);
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
    private static int twoToTen(int n){
        int result = 0;
        int t = 0;         // 记录位数
        int remainder = 0; // 记录余数
        for(int i = n; i > 0 ; i /= 10){
            remainder = i % 10;
            log.info("i:{},remainder:{}",i,remainder);
            result += remainder * Math.pow(2,t); // 2的t次方
            t++;
        }

        return result;
    }


    private static void lg(int M){
        // log2^n < M n < 2^M
        for(int i = 0; 2*i < M; i++ ){

        }
    }

    /**
     * 倒序输出
     */
    @Test
    public void testReverse(){
        int result = reverse3(18213);
        System.out.println(result);
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

        int result = Integer.parseInt(temp);
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

        int result = Integer.parseInt(temp);
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

        int result = Integer.parseInt(temp);
        if(flag){
            result = result * -1;
        }
        return result;
    }

    /**
     * 求唯一值
     */
    @Test
    public void testUnique(){
        int[] nums = {4,4,2,2,1};
        unique2(nums);
        System.out.println(unique(nums));
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
            if(!list.contains(nums[i])){
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

    @Test
    public void test最小公倍数(){
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int a = in.nextInt();
            int b = in.nextInt();
            int result = a*b/gcd(a,b);

            System.out.println(result);
        }
    }


    /**
     * a>b时，是4步骤；a<b时，是5步骤，第一步不同外，后面4步和(a>b时)相同
     * 第一种情况：初始值 a=12 b=8
     * 1、a=12 b=8 gcd(8,a%b)=gcd(8,12%8)=gcd(8,4)
     * 2、a=8 b=4  gcd(4,a%b)=gcd(4,8%4)=gcd(4,0)
     * 3、a=4 b=0
     * 4、结果为4
     *
     * 第二种情况：初始值 a=8 b=12
     * 1、a=8 b=12 gcd(12,a%b)=gcd(12,8%12)=gcd(12,8)
     * 2、a=12 b=8 gcd(8,a%b)=gcd(8,12%8)=gcd(8,4)
     * 3、a=8 b=4 gcd(4,a%b)=gcd(4,8%4)=gcd(4,0)
     * 4、a=4 b=0
     * 5、结果为4
     *
     * @param a
     * @param b
     * @return
     */
    private static int gcd(int a,int b){
        return b==0? a:gcd(b,a%b);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] nums = new int[nums1.length+nums2.length];

        List list1 = Collections.singletonList(nums1);
        List list2 = Collections.singletonList(nums2);
        list1.addAll(list2);
        Collections.sort(list1);

        return 0;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String s = scanner.next();
//            ipToNumber(s);
        }

    }


    /**
     * 查找组成一个偶数最接近的两个素数
     */
    @Test
    public void testDiffPrime(){
        int num = 20;
        int[] result = new int[2];
        int diff = Integer.MAX_VALUE;
        int temp = 0;
        for (int i = 2; i < num; i++) {
            if(isPrime(num-i) && isPrime(i)){
                temp = Math.abs(num - i - i);
                if(temp < diff){
                    diff = temp;
                    result[0] = i; // 由于i>=num/2作为结束条件，所以这里肯定是值比较小的数
                    result[1] = num-i; // 这里是值比较大的数字
                }
            }
            // 当大于num/2时，直接返回
            if(i >= num/2){
                break;
            }
        }
        System.out.println(result[0] + "," + result[1] + "," + diff);
    }

    @Test
    public void testIpAndNumberConvert(){
        String ip = "10.0.3.193";
        long number = 167773121;

        ipNumberConvert1(ip,number);
        System.out.println("-------------");
        ipNumberConvert2(ip,number);
    }

    /**
     * 方法1：ip和数字之间的转换，通过二进制进行过渡
     *
     * @param ip 给定的ip
     * @param number 给定的数字
     */
    private void ipNumberConvert1(String ip,long number){
        long returnNumber = ipToNumber(ip);
        System.out.println(returnNumber);

        String returnIp = numberToIp(number);
        System.out.println(returnIp);
    }

    /**
     * 方法2：将10进制转换为256进制
     * 因为每个ip地址实际上是256进制下的4个数字，所以直接转换即可
     * @param ip
     * @param number
     */
    private void ipNumberConvert2(String ip,long number){
        long returnNumber = ipToNumber2(ip);
        System.out.println(returnNumber);

        String returnIp = numberToIp2(number);
        System.out.println(returnIp);
    }


    private long ipToNumber(String ip){
        // 将ip地址通过.进行分割
        String[] ips = ip.split("\\.");
        StringBuilder builder= new StringBuilder();
        for (int i = 0; i < 4; i++) {
            // 将分割后的每个数字转换为2进制
           StringBuilder binaryNum = new StringBuilder(Integer.toBinaryString(Integer.parseInt(ips[i])));
            // 如果转换后的二进制长度小于8，则进行前面补充0
            while(binaryNum.length()<8){
                binaryNum.insert(0, "0");
            }
            builder.append(binaryNum);
        }
        // parseLong(String s, int radix) 第二个参数是将s对应的radix格式转换为10进制，在这里就是将s对应的2进制格式转换为10进制
        // 将二进制转换为10进制。
        return Long.parseLong(builder.toString(),2);
    }

    private String numberToIp(long number){
        // 将数字转换为2进制
        String binaryNum = Long.toBinaryString(number);
        // 一个ip地址通过.分割后是4段，每段数字转换为2进制是8位，所以总的是32位
        // 如果二进制的长度小于32（每一个ip地址是4个），则前面补充0
        while (binaryNum.length() < 32){
            binaryNum = "0" + binaryNum;
        }

        String[] ips = new String[4];
        for (int i = 0; i < 4; i++) {
            // 将32位数字每8位进行拆分
            String tempStr = binaryNum.substring(8 * i, 8 * i + 8);
            // 拆分后转换为10进制
            String tenNum = Integer.toString(Integer.parseInt(tempStr,2));
            // 把10进制数放入到数组中
            ips[i] = tenNum;
        }
        // 将数组里面的每个元素通过.进行拼接起来
        return String.join(".", ips);
    }

    /**
     *
     * @param ip
     * @return
     */
    public Long ipToNumber2(String ip){
        String[] ips = ip.split("\\.");
        Long ans = 0L;
        for (int i = 0; i < 4; i++) {
            ans = ans * 256 + Long.parseLong(ips[i]);
        }
        return ans;
    }

    public String numberToIp2(long number){
        String[] ips = new String[4];
        for (int i = 3; i >=0 ; i--) {
            ips[i] = Long.toString(number % 256);
            number = number / 256;
        }
        return String.join(".",ips);
    }
}
