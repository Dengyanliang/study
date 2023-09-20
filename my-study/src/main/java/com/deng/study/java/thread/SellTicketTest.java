package com.deng.study.java.thread;


import com.deng.common.util.MyThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/17 18:36
 */
//@Slf4j
public class SellTicketTest{
    private static Random random = new Random();

    public static void main(String[] args) {
        TicketWindow window = new TicketWindow(10000);

        List<Thread> threadList = new ArrayList<>();
        List<Integer> countList = new Vector<>(); // keypoint 这里需要线程安全，所以不能用ArrayList

        for (int i = 0; i < 4000; i++) {
            Thread thread = new Thread(() -> {
                int sellCount = window.sell(randomCount());
                countList.add(sellCount);
            });
            thread.start();
            threadList.add(thread);
        }

        threadList.forEach(MyThreadUtil::join);

        int sellSum = countList.stream().mapToInt(i -> i).sum();
        int availCount = window.getCount();

        System.out.println("余票：" + availCount + "，已卖出的票：" + sellSum + "，合计：" + (sellSum + availCount));

//        log.debug("余票：{}",window.getCount());
//        log.debug("卖出的票数：{}",countList.stream().mapToInt(i -> i).sum());
//        log.debug("总数：{}",sellSum + availCount);
    }


    public static int randomCount(){
        return random.nextInt(3) + 1;
    }


}

/**
 * 售票窗口
 */
class TicketWindow{
    // 票的数量
    private int count;

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    /**
     * 售票
     * @param sellCount 售票数量
     * @return
     */
    public int sell(int sellCount) {
        synchronized (this) {
            if (this.count >= sellCount) {
                this.count -= sellCount;
                return sellCount;
            } else {
                return 0;
            }
        }
    }
}
