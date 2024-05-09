package com.deng.study.rpc.loadbalance;

import org.apache.commons.collections4.CollectionUtils;
import java.util.*;

/**
 * @Desc: 轮询接口
 * @Date: 2024/4/25 22:53
 * @Auther: dengyanliang
 */
public class WeightRobinLoadBalance {

    private int index;
    private final List<ServerNode> serverNodeList;
    private final int totalWeight;

    public WeightRobinLoadBalance(List<ServerNode> serverNodeList) {
        this.index = 0;
        this.serverNodeList = serverNodeList;
        this.totalWeight = serverNodeList.stream().mapToInt(ServerNode::getWeight).sum();
    }

    /**
     * 平滑加权选择
     * @return 服务器ip
     */
    public synchronized String smoothSelectorServer(){
        if(CollectionUtils.isEmpty(serverNodeList)){
            return null;
        }

        ServerNode maxServerNode = serverNodeList.stream().max(Comparator.comparingInt(ServerNode::getCurrentWeight)).orElse(null);

        serverNodeList.forEach(serverNode -> {
            serverNode.setCurrentWeight(serverNode.getCurrentWeight()+serverNode.getWeight());
            if(serverNode.equals(maxServerNode)){
                serverNode.setCurrentWeight(serverNode.getCurrentWeight() - totalWeight);
            }
        });

        return maxServerNode.getIp();
    }


    public synchronized String randomSelector(){
        if(CollectionUtils.isEmpty(serverNodeList)){
            return null;
        }
        // 这种效率极低，双层循环
        List<String> nodeList = new ArrayList<>();
        serverNodeList.forEach(serverNode -> {
            Integer weight = serverNode.getWeight();
            for (int i = 0; i < weight; i++) {
                nodeList.add(serverNode.getIp());
            }
        });
        Random random = new Random();
        int nextInt = random.nextInt(totalWeight);

        return nodeList.get(nextInt);

//        Random random = new Random();
//        int nextInt = random.nextInt(totalWeight);

    }







}
