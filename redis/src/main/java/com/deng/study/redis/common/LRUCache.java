package com.deng.study.redis.common;

import java.util.HashMap;

/**
 * @Desc: 最近最少使用算法，使用双向链表实现
 * @Auther: dengyanliang
 * @Date: 2023/7/31 14:13
 */
public class LRUCache {
    class DLinkedNode{
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;

        public DLinkedNode() {
        }

        public DLinkedNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private HashMap<Integer,DLinkedNode> cache = new HashMap<>();
    private int size;
    private int capacity;
    private DLinkedNode head,tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        // 从缓存中获取，如果没有获取到，则返回-1
        DLinkedNode node = cache.get(key);
        if(node == null){
            return -1;
        }
        // 获取到了，则先通过哈希表定位，然后再移动到头部
        moveToHead(node);
        return node.value;
    }

    private void moveToHead(DLinkedNode node) {
        // 先删除节点
        removeNode(node);
        // 再添加到头部
        addToHead(node);
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;

        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        // 如果节点不存在，则构建节点，并添加到链表头部
        if(node == null){
            DLinkedNode newNode = new DLinkedNode(key,value);
            cache.put(key,newNode);
            addToHead(newNode);
            size++;
            // 如果超出容量，删除双向链表的尾部节点
            if(size > capacity){
                DLinkedNode tail = removeTail();
                // 删除哈希表中尾部节点
                cache.remove(tail.key);
                size--;
            }

        }else{ // 如果节点存在，则先通过哈希表定位，再修改value，并移动到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private DLinkedNode removeTail() {
        DLinkedNode tailPrev = tail.prev;
        removeNode(tailPrev);
        return tailPrev;
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));

        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，因为1在前面查询过，所以缓存中存在的是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));

        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，因为2在前面查询过，所以缓存中存在的是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));
        System.out.println(lRUCache.get(3));
        System.out.println(lRUCache.get(4));
    }
}

