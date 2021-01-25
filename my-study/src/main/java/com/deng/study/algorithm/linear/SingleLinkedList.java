package com.deng.study.algorithm.linear;

import lombok.extern.slf4j.Slf4j;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/1/23 22:11
 */
@Slf4j
public class SingleLinkedList {

    Node head;
    Node current;

    public SingleLinkedList(){
        this.head = current = new Node();
    }

    public static void main(String[] args) {
        Node n1 = new Node(3);
//        n1.append(4).append(5);

        n1 = n1.addFirst(6);
        n1 = n1.addFirst(7);
        n1.show();
        log.info("node append:{}",n1.next().next().getData());
        log.info("node isLast:{}",n1.isLast());
        n1.show();
        n1.delete(7);

        n1.show();

    }
}
