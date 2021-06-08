package com.deng.study.rpc;


import com.deng.study.domain.User;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

public class ZkClientTest {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(ZookeeperConstant.connectString, ZookeeperConstant.sesstionTimeOut,
                ZookeeperConstant.sesstionTimeOut, new SerializableSerializer());

        User user = new User();
        user.setName("wangwu");
        user.setAge(12);

        // 创建目录节点
        zkClient.createEphemeral(ZookeeperConstant.path);
        // 往目录下写数据
        zkClient.writeData(ZookeeperConstant.path, user);

        // 从目录下读数据
        Object o = zkClient.readData(ZookeeperConstant.path);
        System.out.println(o);

        // 读取所有的目录
        List children = zkClient.getChildren("/");
        System.out.println(children);
        for (Object child : children) {
            System.out.println("child:" + child);
        }
    }

}

