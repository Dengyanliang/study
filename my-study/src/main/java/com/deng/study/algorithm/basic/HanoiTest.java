package com.deng.study.algorithm.basic;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/1/25 11:12
 */
public class HanoiTest {

    public static void main(String[] args) {
        hanoi(3,'A','B','C');
    }

    /**
     *
     * @param n      共有n个元素
     * @param from   开始柱子
     * @param middle 中间柱子
     * @param to     目标柱子
     */
    public static void hanoi(int n,char from ,char middle, char to){
        if(n == 1){   // 只有一个元素
            System.out.println("第"+n+"个元素从"+from+"移动到" + to);
        }else{       // 不论有多少个元素，都认为只有两个，上面所有元素和最下面一个元素
            // 把上面所有元素移动到中间位置
            hanoi(n-1,from,to,middle);
            System.out.println("第"+n+"个元素从"+from+"移动到" + to);
            // 把上面所有元素从中间位置移动到目标位置
            hanoi(n-1,middle,from,to);
        }
    }
}
