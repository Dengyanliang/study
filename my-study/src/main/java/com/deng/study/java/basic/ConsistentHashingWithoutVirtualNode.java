package com.deng.study.java.basic;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Desc: 不带虚拟节点的一致性hash算法
 * @Auther: dengyanliang
 * @Date: 2023/10/13 20:42
 */
public class ConsistentHashingWithoutVirtualNode {

    /**
     * 待加入hash环的服务器列表
     */
    private static String[] servers = {"192.168.0.1:111","192.168.0.2:111","192.168.0.3:111","192.168.0.4:111"};

    /**
     * key：服务器的hash值
     * value：服务器名称
     */
    private static SortedMap<Integer,String> sortedMap = new TreeMap<>();

    /**
     * 初始化程序，将所有的服务器放入sortedMap中
     */
    static{
        for (int i = 0; i < servers.length; i++) {
            String server = servers[i];
            int hash = getHash(server);
            System.out.println("[" + server + "]加入集合中, 其Hash值为" + hash);
            sortedMap.put(hash,server);
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

        // String的hashCode()方法却会产生负数（"192.168.1.0:1111"试试看就知道了）。
        // 所以如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

    private static String getServer(String node){
        // 得到待路由的节点的hash值
        int hash = getHash(node);
        // 得到大于该hash值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        // 第一个key就是顺时针过去离node最近的那个节点
        Integer key = subMap.firstKey();
        // 返回对应的服务器名称
        return subMap.get(key);
    }

    public static void main(String[] args) {
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        for (int i = 0; i < nodes.length; i++)
            System.out.println("[" + nodes[i] + "]的hash值为" +
                    getHash(nodes[i]) + ", 被路由到结点[" + getServer(nodes[i]) + "]");
    }
}
