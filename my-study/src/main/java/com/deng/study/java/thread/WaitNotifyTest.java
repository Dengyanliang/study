package com.deng.study.java.thread;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Desc: 测试保护性线程
 * @Auther: dengyanliang
 * @Date: 2023/2/14 09:30
 */
@Slf4j
public class WaitNotifyTest {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new People().start();
        }
        Thread.sleep(2000);

        for (Integer id : MailBoxs.getIds()) {
            new PostMan(id,"内容"+id).start();
        }
    }

    private static void test1() {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(() -> {
            log.debug("等待结果。。。");
            Object result = guardedObject.get(1000);
            log.debug("结果大小是：{}", result);
        }, "t1").start();

        new Thread(() -> {
            log.debug("执行下载。。。");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<String> list = new ArrayList<>(3);
            list.add("1");
            list.add("2");
            list.add("3");

            guardedObject.complete(list);
        }, "t2").start();
    }
}
@Slf4j(topic = "people")
class People extends Thread{

    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxs.createGuardedObject();
        log.debug("开始收信，id：{}",guardedObject.getId());
        Object mail = guardedObject.get(5000);
        log.debug("收到信，id:{},内容：{}",guardedObject.getId(),mail);
    }
}
@Slf4j(topic = "postMan")
class PostMan extends Thread{

    private int id;
    private String mail;

    public PostMan(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }


    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxs.getGuardedObject(id);
        log.debug("开始送信，id：{}，内容：{}",id,mail);
        guardedObject.complete(mail);
//        log.debug("送信完成，id：{}，内容：{}",id,mail);
    }


}


class MailBoxs {

    // 每个GuardedObject 对应一个唯一的id
    private static Map<Integer, GuardedObject> boxMap = new ConcurrentHashMap<>();


    private static int id = 1;

    /**
     * 产生唯一的id
     * @return
     */
    private static synchronized int generateId(){
        return id++;
    }

    /**
     * 通过id获取对应的GuardedObject，获取到之后，从boxMap中删除
     * @param id
     * @return
     */
    public static GuardedObject getGuardedObject(int id){
        return boxMap.remove(id);
    }

    public static GuardedObject createGuardedObject(){
        GuardedObject guardedObject = new GuardedObject(generateId());
        boxMap.put(guardedObject.getId(),guardedObject);
        return guardedObject;
    }

    public static Set<Integer> getIds(){
        return boxMap.keySet();
    }
}

/**
 * @Desc: 测试保护性线程
 * @Auther: dengyanliang
 * @Date: 2023/2/14 09:46
 */
@Slf4j
class GuardedObject {

    // 用来标识不同的GuradedObject
    private int id;

    public GuardedObject() {

    }

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private Object response;

    /**
     * 一直等待下去
     * @return
     */
    public Object get(){
        synchronized (this){
            while (response == null){
                try {
                    log.debug("waiting....");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    /**
     * 增加超时时间控制
     * @param timeout
     * @return
     */
    public Object get(long timeout){
        synchronized (this){
            long begin = System.currentTimeMillis();
            long passedTime = 0;
            while (response == null){
                // 还需等待的时间
                long waitTime = timeout - passedTime;
                // 如果等待时间超过了最大等待时间，则退出循环
                if(waitTime <= 0){
                    break;
                }
                try {
                    log.debug("waiting....{}",Thread.currentThread().getName());
                    this.wait(waitTime);
                    log.debug("等待结束....{}",Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passedTime = System.currentTimeMillis() - begin;
            }
            return response;
        }
    }

    public void complete(Object response){
        synchronized(this){
            this.response = response;
            this.notifyAll();
        }
    }
}

