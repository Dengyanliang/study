package com.deng.study.algorithm.tree;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/2/7 15:11
 */
public class TreeTest {

    public static void main(String[] args) {
        Node rootNode = new Node(1,"刘备");
        Node node1 = new Node(2,"关羽");
        Node node2 = new Node(3,"张飞");
        Node node3 = new Node(4,"关平");
        Node node4 = new Node(5,"周仓");

        rootNode.setLeft(node1);
        rootNode.setRight(node2);

        node1.setLeft(node3);
        node1.setRight(node4);


        Tree tree = new Tree(rootNode);
//        tree.preFront();
//        tree.midFront();
//        tree.lastFront();
//
//        tree.preSearch(1);
//        tree.midSearch(1);
//        tree.lastSearch(1);

        tree.levelOrder();

    }
}
