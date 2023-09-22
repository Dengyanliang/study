package com.deng.study.java.thread;

import com.deng.common.util.MyThreadUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @Desc: 生产者消费者
 * @Auther: dengyanliang
 * @Date: 2023/9/22 15:16
 */
public class ProducerAndConsumer {

    public static void main(String[] args) {
        LanziQueue queue = new LanziQueue(3);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                queue.put(new Mantou("mantou-" + id));
            }, "t1").start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                queue.take();
            }, "t2").start();
        }

//        int count = 1;
//        new Thread(() -> {
//            int finalCount = count;
//            while (true){
//                queue.put(new Mantou("mantou-" + finalCount));
//                finalCount++;
//            }
//        }, "t1").start();
//
//        new Thread(() -> {
//            while (true){
//                queue.take();
//            }
//        }, "t2").start();

    }
}

@Slf4j
class LanziQueue {
    // 存放馒头的集合，用使用双向链表的原因是因为从链表中取出数据的时候可以返回
    private LinkedList<Mantou> list = new LinkedList<>();
    // 容量，最多可以存放多少个馒头
    private int capicaty;

    public LanziQueue(int capicaty) {
        this.capicaty = capicaty;
    }

    public Mantou take(){
        synchronized (list){
            // 如果篮子为空，则等待
            while (list.isEmpty()){
                try {
                    list.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            MyThreadUtil.sleep(200);
            // 不空，则取出一个
            Mantou mantou = list.removeFirst();
            log.info("{} 取到了馒头：{}", Thread.currentThread().getName(),mantou);
            list.notifyAll();
            return mantou;
        }
    }

    public void put(Mantou mantou){
        synchronized (list){
            // 如果篮子容量已经达到最大，则等待
            while (list.size() == capicaty){
                try {
                    list.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            MyThreadUtil.sleep(200);
            // 没有达到，则存放
            log.info("{} 存放馒头：{}", Thread.currentThread().getName(),mantou);
            list.addLast(mantou);
            // 通知其他线程
            list.notifyAll();
        }
    }


}

@Data
@AllArgsConstructor
class Mantou{
    private String id;
}




