package com.deng.study.zk;

import com.deng.study.common.constant.ZkConstant;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/21 12:49
 */
@Component
public class ZkClient {

    private ZooKeeper zooKeeper;

    @PostConstruct
    public void init(){
        // 获取连接，项目启动时
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            zooKeeper = new ZooKeeper(ZkConstant.CONNECT_STRING, ZkConstant.SESSTION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    Event.KeeperState state = event.getState();
                    if(Event.KeeperState.SyncConnected.equals(state) && Event.EventType.None.equals(event.getType())){
                        System.out.println("获取到了连接，" + state);
                        countDownLatch.countDown();
                    } else if(Event.KeeperState.Disconnected.equals(state)){
                        System.out.println("关闭连接...");
                    }
                }
            });
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PreDestroy
    public void destory() {
        // 容器销毁前释放连接
        try {
            if(Objects.nonNull(zooKeeper)){
                zooKeeper.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ZkDistributeLock getLock(String lockName){
        return new ZkDistributeLock(zooKeeper,lockName);
    }
}
