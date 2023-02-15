package com.deng.study.java.thread;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @Desc: 生产者消费者
 * @Auther: dengyanliang
 * @Date: 2023/2/14 12:29
 */
public class WaitNotifyTest2 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(()->{
                messageQueue.put(new Message(id,"值"+id));
            },"生产者"+i).start();
        }

        new Thread(()->{
            while(true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                messageQueue.take();
            }
        },"消费者").start();
    }
}

@Slf4j
class MessageQueue{
    // 使用双向链表存储数据
    private LinkedList<Message> list = new LinkedList<>();
    // 队列容量
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    public Message take(){
        // 检查队列是否为空
        synchronized (list){
            while(list.isEmpty()){
                try {
                    log.debug("队列为空，消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 不空时，从队列头部获取消息并返回
            Message message = list.removeFirst();
            log.debug("已经消费消息：{}",message);
            // 不空后，唤醒其他线程
            list.notifyAll();
            return message;
        }
    }

    public void put(Message message){
        synchronized (list){
            while(list.size() == capcity){
                log.debug("队列已经满了，生产者线程等待");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 队列不满时，可以放入消息
            list.addLast(message);
            log.debug("已经生产消息：{}",message);
            // 不空后，唤醒其他线程
            list.notifyAll();;
        }
    }

}

@ToString
@Getter
@AllArgsConstructor
final class Message{
    private int id;
    private Object value;
}