package com.deng.study.zk;


import com.deng.common.constant.ZkConstant;
import com.deng.study.domain.User;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.util.List;

public class ZkClientTest {
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(ZkConstant.CONNECT_STRING, ZkConstant.SESSTION_TIMEOUT,
                ZkConstant.SESSTION_TIMEOUT, new SerializableSerializer());

        // 监听当前节点和下面子节点的新增、删除
        zkClient.subscribeChildChanges(ZkConstant.PATH,new IZkChildListener(){
            @Override
            public void handleChildChange(String path, List<String> currentChilds) throws Exception {
                System.out.println("path:" + path);
                System.out.println("currentChilds:" + currentChilds);
            }
        });

        // 监听 当前节点和子节点的内容修改、删除
        zkClient.subscribeDataChanges(ZkConstant.PATH,new IZkDataListener(){
            @Override
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println("变更的节点为:" + path + ", 变更内容为:" + data);
            }

            @Override
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("变更的节点为:" + path);
            }
        });

        User user = new User();
        user.setName("wangwu");
        user.setAge(12);

        // 创建目录节点
        zkClient.createEphemeral(ZkConstant.PATH);
        // 往目录下写数据
        zkClient.writeData(ZkConstant.PATH, user);

        // 从目录下读数据
        Object o = zkClient.readData(ZkConstant.PATH);
        System.out.println(o);

        // 读取所有的目录
        List children = zkClient.getChildren("/");
        System.out.println(children);
        for (Object child : children) {
            System.out.println("child:" + child);
        }
    }

}

