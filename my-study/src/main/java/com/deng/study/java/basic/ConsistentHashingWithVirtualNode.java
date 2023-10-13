package com.deng.study.java.basic;

import java.util.*;

/**
 * @Desc: 带虚拟节点的一致性hash算法
 * @Auther: dengyanliang
 * @Date: 2023/10/13 20:42
 */
public class ConsistentHashingWithVirtualNode {

    /**
     * 待加入hash环的服务器列表
     */
    private static String[] servers = {"192.168.0.1:111","192.168.0.2:111","192.168.0.3:111","192.168.0.4:111"};

    /**
     * 真实节点列表，考虑到线上服务器上线、下线的场景，也就是添加、删除的场景比较频繁，这里使用LinkedList比较好
     */
    private static List<String> realNodes = new LinkedList<>();

    /**
     * key：虚拟节点的服务器的hash值
     * value：虚拟节点的服务器名称
     */
    private static SortedMap<Integer,String> virtualSortedMap = new TreeMap<>();

    /**
     * 虚拟节点的数目，这里表示一个真实节点对应5个虚拟节点，暂时写死
     */
    private static final int virtual_nodes = 5;

    private static final String _NN = "&&VN";

    /**
     * 初始化程序，将所有的服务器放入sortedMap中
     */
    static{
        // 先将原始服务器添加到真实节点列表中
        realNodes.addAll(Arrays.asList(servers));

        // 添加虚拟节点
        for (String realNode : realNodes) {
            for (int j = 0; j < virtual_nodes; j++) {
                // 将真实节点和后缀进行拼接
                String virtualNodeName = realNode + _NN + j;
                int hash = getHash(virtualNodeName);
                System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
                virtualSortedMap.put(hash,virtualNodeName);
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------");
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     */
    private static int getHash(String str)
    {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for (int i = 0; i < str.length(); i++)
            hash = (hash ^ str.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    private static String getServer(String node){
        // 得到待路由的节点的hash值
        int hash = getHash(node);
        // 得到大于该hash值的所有Map
        SortedMap<Integer, String> subMap = virtualSortedMap.tailMap(hash);
        // 第一个key就是顺时针过去离node最近的那个节点
        Integer key = subMap.firstKey();
        // 返回对应的虚拟节点名称
        String virtualNode = subMap.get(key);
        // 对虚拟节点进行截取，返回真实节点
        return virtualNode.substring(0,virtualNode.indexOf(_NN));
    }

    public static void main(String[] args) {
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (int i = 0; i < nodes.length; i++)
            System.out.println("[" + nodes[i] + "]的hash值为" +
                    getHash(nodes[i]) + ", 被路由到结点[" + getServer(nodes[i]) + "]");
    }
}
