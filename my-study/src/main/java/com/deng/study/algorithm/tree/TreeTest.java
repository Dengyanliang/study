package com.deng.study.algorithm.tree;

import org.junit.Test;

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
        System.out.println("前序遍历:");
        tree.preFront(tree.getRootNode());

        System.out.println("中序遍历：");
        tree.midFront(tree.getRootNode());

        System.out.println("后续遍历：");
        tree.lastFront(tree.getRootNode());

        int count = tree.nodeCount(tree.getRootNode());
        System.out.println("树的节点总数：" + count);

        int height = tree.height(tree.getRootNode());
        System.out.println("树的高度：" + height);

        Tree newTree = tree.copyTreeByPreFront(tree);
        System.out.println("使用先序遍历拷贝新树：");
        tree.lastFront(newTree.getRootNode());

        Tree newTree2 = tree.copyTreeByLastFront(tree);
        System.out.println("使用后序遍历拷贝新树：");
        tree.lastFront(newTree2.getRootNode());

//
//        tree.preFront();
//        tree.midFront();
//        tree.lastFront();
//
//        tree.preSearch(1);
//        tree.midSearch(1);
//        tree.lastSearch(1);
//
//        tree.levelOrder();

    }

    @Test
    public void testTree(){



    }

}
