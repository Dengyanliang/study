package com.deng.study.algorithm.recursion;

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

    int max = 5;
    int[] array = new int[max];
    static int count = 0;
    static int jdudeCount = 0;

    public static void main(String[] args) {
        Queue8 queue8 = new Queue8();
        queue8.check(0);

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
            count++;
            return;
        }
        // 依次放入皇后，并判断是否冲突
        for(int i = 0;i < max; i++){
            // 先把这个皇后n，放在该行的第一列
            array[n] = i;
            // 判断当放置第n个皇后到i列时，校验是否冲突
            if(judge(n)){ // 不冲突
                check(n+1); // 接着放n+1个皇后，开始递归
            }
            // 不过冲突，则继续执行 array[n] = i
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

}
