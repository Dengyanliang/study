package com.deng.study.algorithm.tree;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/2/7 15:09
 */
public class Tree {
    private Node rootNode;

    public Tree(Node rootNode) {
        this.rootNode = rootNode;
    }

    public void preFront(){
        System.out.println("前序遍历：");
        rootNode.preFront();
    }
    public void midFront(){
        System.out.println("中序遍历：");
        rootNode.midFront();
    }
    public void lastFront(){
        System.out.println("后续遍历：");
        rootNode.lastFront();
    }

    public void levelOrder(){
        System.out.println("层序遍历：");
        rootNode.levelOrder();
    }

    public Node preSearch(int no){
        Node node = rootNode.preSearch(no);
        System.out.println("前序查找编号：" + no + ",返回值：" + node);
        return node;
    }

    public Node midSearch(int no){
        Node node = rootNode.midSearch(no);
        System.out.println("中序查找编号：" + no + ",返回值：" + node);
        return node;
    }

    public Node lastSearch(int no){
        Node node = rootNode.lastSearch(no);
        System.out.println("后序查找编号：" + no + ",返回值：" + node);
        return node;
    }


}
