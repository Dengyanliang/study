package com.deng.study.algorithm.tree;

import java.util.Objects;

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

    public void preFront(Node node){
        if(Objects.nonNull(node)){
            System.out.println(node.toString());  // 先遍历根节点
            preFront(node.getLeft());             // 再遍历左子树
            preFront(node.getRight());            // 再遍历右子树
        }
    }

    public void preFront(){
        System.out.println("前序遍历：");
        rootNode.preFront();
    }

    public void midFront(Node node){
        if(Objects.nonNull(node)){
            midFront(node.getLeft());  // 先遍历左子树
            System.out.println(node);  // 再遍历根节点
            midFront(node.getRight()); // 再遍历右子树
        }
    }

    public void midFront(){
        System.out.println("中序遍历：");
        rootNode.midFront();
    }

    public void lastFront(Node node){
        if(Objects.nonNull(node)){
            lastFront(node.getLeft());  // 先遍历左子树
            lastFront(node.getRight()); // 再遍历右子树
            System.out.println(node);   // 再遍历根节点
        }
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

    /**
     * 求树的节点总数
     * @param node
     * @return
     */
    public int nodeCount(Node node){
        if(Objects.nonNull(node)){
            return nodeCount(node.getLeft()) + nodeCount(node.getRight()) + 1;
        }
        return 0;
    }

    /**
     * 求树的高度
     * @return
     */
    public int height(Node node){
        if(Objects.nonNull(node)){
            return Math.max(height(node.getLeft()),height(node.getRight())) + 1;
        }
        return 0;
    }

    public Tree copyTreeByPreFront(Tree tree){
        Node node = rootNode.copyNodeByPreFront(tree.getRootNode());
        return new Tree(node);
    }

    public Tree copyTreeByLastFront(Tree tree){
        Node node = rootNode.copyNodeByLastFront(tree.getRootNode());
        return new Tree(node);
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

}
