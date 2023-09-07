package com.deng.study.zk;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import static com.deng.study.common.constant.ZkConstant.*;

/**
 * @Desc: 自定义zk分布式锁
 * @Auther: dengyanliang
 * @Date: 2023/7/21 12:48
 */
public class ZkDistributeLock implements Lock {

    private ZooKeeper zooKeeper;
    private String lockName;
    private String currentNodePath;
    private static final ThreadLocal<Integer> THREAD_LOCAL = new ThreadLocal<>();

    public ZkDistributeLock(ZooKeeper zooKeeper, String lockName) {
        this.zooKeeper = zooKeeper;
        this.lockName = lockName;
        try {
            // 如果根节点不存在，就创建一个持久节点
            if(zooKeeper.exists(PATH,false) == null){
                zooKeeper.create(PATH,null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            // 判断threadLocal中是否已经有锁，有锁直接重入（+1）
            Integer count = THREAD_LOCAL.get();
            if(Objects.nonNull(count) && count > 0){
                THREAD_LOCAL.set(count + 1);
                return true;
            }

            // 创建znode节点过程：为了防止zk客户端程序获取到锁之后，服务器宕机带来的死锁问题，这里创建了临时顺序节点
            currentNodePath = this.zooKeeper.create(PATH + OBLIQUE_LINE + lockName + "-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 获取前置节点，如果前置节点为空，则获取锁成功，否则则监听前置节点
            String preNode = this.getPreNode();
            // 前置节点不空
            if(preNode != null){
                CountDownLatch countDownLatch = new CountDownLatch(1);
                // 因为获取前置节点这个操作，不具备原子性，所以需要再次判断zk的前置节点是否存在
                Stat exists = this.zooKeeper.exists(PATH + OBLIQUE_LINE + preNode, new Watcher() {
                    @Override
                    public void process(WatchedEvent event) {
                        countDownLatch.countDown();
                    }
                });
                if(Objects.isNull(exists)){
                    THREAD_LOCAL.set(1);
                    return true;
                }else{
                    countDownLatch.await();
                }
            }

            THREAD_LOCAL.set(1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
//            MyThreadUtil.sleep(100);
//            this.tryLock(); // 这里是自旋，效果不好，浪费cpu资源，应该换种方式
        }
        return false;
    }

    private String getPreNode() {
        try {
            List<String> children = this.zooKeeper.getChildren(PATH, false);
            if(CollectionUtils.isEmpty(children)){
                throw new IllegalMonitorStateException("非法操作！");
            }
            // 获取和当前节点同一资源的锁
            List<String> nodes = children.stream().filter(node -> StringUtils.startsWith(node,lockName + "-")).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(nodes)){
                throw new IllegalMonitorStateException("非法操作！");
            }
            // 排好序
            Collections.sort(nodes);
            // 获取当前节点
            String currentNode = StringUtils.substringAfterLast(currentNodePath, OBLIQUE_LINE);
            // 获取当前节点的下标
            int index = Collections.binarySearch(nodes, currentNode);
            if(index < 0){
                throw new IllegalMonitorStateException("非法操作！");
            }else if(index > 0){
                return nodes.get(index-1); // 获取前置节点
            }else{
                return null; // 如果当前节点就是第一个节点，则返回null
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalMonitorStateException("非法操作！");
        }

    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        try {
            // 先减一
            THREAD_LOCAL.set(THREAD_LOCAL.get()-1);
            if(THREAD_LOCAL.get() == 0){
                // 删除znode节点的过程
                this.zooKeeper.delete(currentNodePath,-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
