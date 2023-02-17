package com.deng.study.java.thread.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/17 09:37
 */
public class ForkjoinTest {
    public static void main(String[] args) {
        //
        ForkJoinPool pool = new ForkJoinPool(4);
//        System.out.println(pool.invoke(new AddTask(5)));

        System.out.println(pool.invoke(new AddTask2(1,5)));

    }
}

@Slf4j
class AddTask extends RecursiveTask<Integer> {

    private int n;

    public AddTask(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "{" + n + "}";
    }

    @Override
    protected Integer compute() {
        // 终止条件
        if(n == 1){
            log.debug("join() {}",n);
            return 1;
        }
        AddTask t1 = new AddTask(n-1);
        t1.fork();
        log.debug("fork() {} + {}", n, t1);

        int result = n + t1.join();
        log.debug("join() {} + {} = {}", n, t1, result);

        return result;
    }
}

@Slf4j
class AddTask2 extends RecursiveTask<Integer>{
    private int begin;
    private int end;

    public AddTask2(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + "，" + end + "}";
    }

    @Override
    protected Integer compute() {
        if(begin == end){
            log.debug("join() {}",begin);
            return begin;
        }
        if(end - begin == 1){
            log.debug("join() {} + {} = {}", begin, end, end + begin);
            return end + begin;
        }

        // 取中间值
        int middle = begin + (end - begin)/2;

        AddTask2 t1 = new AddTask2(begin,middle);
        t1.fork();

        AddTask2 t2 = new AddTask2(middle+1,end);
        t2.fork();

        log.debug("fork() {} + {} = ?", t1, t2);

        int result = t1.join() + t2.join();
        log.debug("join() {} + {} = {}", t1, t2, result);

        return result;
    }
}


