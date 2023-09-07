package com.deng.study.java.thread;


import com.deng.common.util.MyThreadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/15 17:38
 */
public interface Account {

    /**
     * 获取余额
     * @return
     */
    Integer getBalance();

    /**
     * 取款
     * @param amount
     */
    void withdraw(Integer amount);

    /**
     * keypoint 接口内也可以写方法 jdk1.8
     * 方法内会自动启动1000个线程，每个线程做 -10的操作
     * 如果初始余额是10000，那么正确的结果应该是0
     */
    static void demo(Account account){
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(()->{
                account.withdraw(10);
            }));
        }
        long start = System.currentTimeMillis();

        threads.forEach(Thread::start);
        threads.forEach(MyThreadUtil::join);

        long end = System.currentTimeMillis();
        System.out.println("余额：" +account.getBalance() + "，耗时：" + (end-start));
    }


}
