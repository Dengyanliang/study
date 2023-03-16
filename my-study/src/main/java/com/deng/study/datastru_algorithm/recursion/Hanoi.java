package com.deng.study.datastru_algorithm.recursion;

/**
 * @Desc:汉诺塔问题
 * @Auther: dengyanliang
 * @Date: 2021/1/25 11:12
 */
public class Hanoi {

    public static void main(String[] args) {
        hanoi(2,'A','B','C');
    }

    /**
     *  将问题拆分为大问题和小问题：前n-1个元素移动为大问题，第n个元素移动为小问题.
     *  不论有多少个元素，都认为只有两个，上面所有元素和最下面一个元素。
     *  当只有两个元素时，直接从开始柱子移动的目标柱子即可
     *
     * @param n      共有n个元素
     * @param from   开始柱子
     * @param middle 中间柱子
     * @param to     目标柱子
     */
    public static void hanoi(int n,char from ,char middle, char to){
        if(n == 1){   // 只有一个元素，直接从开始柱子移动到目标柱子
            move(n, from, to);
        }else{       // 不论有多少个元素，都认为只有两个，上面所有元素和最下面一个元素，
            // 把n-1个元素从开始柱子借助于目标柱子移动到中间柱子
            hanoi(n-1,from,to,middle);
            // 将第n个元素从开始柱子移动到目标柱子
            move(n, from, to);
            // 把n-1个元素从中间柱子借助于开始柱子移动到目标柱子
            hanoi(n-1,middle,from,to);
        }
    }

    private static void move(int n, char from, char to) {
        System.out.println("第"+n+"个元素从"+from+"移动到" + to);
    }
}
