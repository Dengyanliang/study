package com.deng.study.datastru_algorithm.recursion;

/**
 * @Desc:八皇后问题
 * 1、第一个皇后先放第一行第一列
 * 2、第二个皇后放在第二行第一列，如果不行，继续放在第二列、第三列，依次把所有列都放完，找到一个合适位置
 * 3、继续，从第三个直到第八个皇后位置，算是找到一个正确解
 * 4、当得到一个正解时，在栈回退到上一个栈时，就开始回溯，即将第一个皇后，放到第一列的所有正确解，全部得到
 * 5、然后回头继续第一个皇后放第二列，后面继续1、2、3、4的步骤
 *
 * @Auther: dengyanliang
 * @Date: 2021/2/5 17:13
 */
public class Queue8 {

    private int max = 4; // 一共有多少个皇后
    /**
     * 放置皇后的位置，
     * 理论上讲，棋盘是一个二维数组，但是用一个一维数组就可以解决：
     * 下标：表示棋盘中的第几行
     * 对应的值：表示放在该行的那一列上
     * 比如array[1]=2，则表示放在第二行的第3列上
     */
    private int[] array = new int[max];
    private static int count = 0; // 总共有几种解法
    private static int jdudeCount = 0; // 比较的次数

    public static void main(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.check(0); // 从第一行开始

        System.out.println("一共有"+count+"中解法");
        System.out.println("一共回溯了"+jdudeCount+"次");
    }

    /**
     *
     * @param n
     */
    private void check(int n){
        if(n == max){
            print();
            print2();
            count++;
            return;
        }
        // 依次放入皇后，并判断是否冲突
        for(int i = 0;i < max; i++){
            // 在数组第n行的第i列上放置一个皇后
            array[n] = i;
            // 判断当放置第n个皇后到i列时，校验是否冲突
            boolean judge = judge(n);
            System.out.println("---->n:" + n + ",array[" + n + "]:" + i+",judge:"+judge);
            if(judge){ // 不冲突
                check(n+1); // 接着放n+1个皇后，开始递归
            }
            // 如果冲突，则继续执行 i++;array[n] = i，即将皇后房子下一列上
        }

    }

    /**
     * 校验该皇后是否和前面已经摆放的皇后冲突
     * @param n 第n个皇后
     * @return
     */
    private boolean judge(int n){
        jdudeCount++;
        for(int i = 0; i < n; i++){
            // array[i] == array[n] 表示判断第n个皇后是否和前面的n-1个皇后在同一列
            // Math.abs(n-i) == Math.abs(array[n]- array[i] 表示判断第n个皇后是否和前面的n-1个皇后在同一斜线
            // 不用校验是否在同一行，没有必要，一定冲突
            if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n]- array[i])){
               return false;
            }
        }
        return true;
    }


    /**
     * 将皇后摆放的位置打印出来
     */
    public void print(){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public void print2(){
        for(int row = 0; row < array.length; row++){
            for (int column = 0; column < array.length; column++) {
                if(array[row] == column){
                    System.out.print("Q ");
                }else{
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

}
