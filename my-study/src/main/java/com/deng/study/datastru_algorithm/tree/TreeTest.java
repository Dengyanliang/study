package com.deng.study.datastru_algorithm.tree;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

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
        tree.levelOrder();

    }

    @Test
    public void testHuffmanTree(){
        String str = "abcdefgh";
//        double[] weight = {0.07,0.19,0.02,0.06,0.32,0.03,0.21,0.10};
        double[] weight = {7,19,2,6,32,3,21,10};
        HuffmanTree huffmanTree = new HuffmanTree(str,weight);

        huffmanTree.createHuffmanTree();
        huffmanTree.createHuffmanCode();
        huffmanTree.displayHufuffman();
    }


    /**
     * 判断是否为镜像二叉树
     */
    @Test
    public void testIsSymmetrical(){
        BTree tree1 = new BTree();
        BTNode node1 = getNode1();
        tree1.setRootNode(node1);
        tree1.levelOrder();
        // 判断一棵二叉树是不是镜像二叉树
        System.out.println(isSymmetricalCore(node1.lchild,node1.rchild));

        BTree tree2 = new BTree();
        BTNode node2 = getNode2();
        tree2.setRootNode(node2);
        tree2.levelOrder();

        // 判断两个二叉树是不是镜像二叉树
        System.out.println(isSymmetricalCore(node1,node2));
    }

    private boolean isSymmetricalCore(BTNode left,BTNode right){
        if(left == null && right == null){
            return true;
        }
        if(left == null || right == null){
            return false;
        }
        if(left.data != right.data){
            return false;
        }
        return isSymmetricalCore(left.lchild,right.rchild) && isSymmetricalCore(left.rchild,right.lchild);
    }


    private BTNode getNode1(){
        BTNode rootNode = new BTNode("a");
        BTNode node1 = new BTNode("b");
        BTNode node2 = new BTNode("b");

        BTNode node3 = new BTNode("d");
        BTNode node4 = new BTNode("c");

        BTNode node5 = new BTNode("c");
        BTNode node6 = new BTNode("d");

        rootNode.lchild = node1;
        rootNode.rchild = node2;

        node1.lchild = node3;
        node1.rchild = node4;

        node2.lchild = node5;
        node2.rchild = node6;


        return rootNode;
    }

    private BTNode getNode2(){
        BTNode rootNode = new BTNode("a");
        BTNode node1 = new BTNode("c");
        BTNode node2 = new BTNode("b");

        BTNode node3 = new BTNode("g");
        BTNode node4 = new BTNode("f");
        BTNode node5 = new BTNode("e");
        BTNode node6 = new BTNode("d");

        rootNode.lchild = node1;
        rootNode.rchild = node2;

        node1.lchild = node3;
        node1.rchild = node4;

        node2.lchild = node5;
        node2.rchild = node6;

        return rootNode;
    }

    /**
     * 创建镜像二叉树
     * 1、先对原始树进行拷贝出一棵新树
     * 2、对新树进行递归调用，并把左右节点进行调换即可
     */
    @Test
    public void testCreateSymmetrical(){
        BTNode oldNode = getNode1();
        BTree oldTree = new BTree();
        oldTree.setRootNode(oldNode);
        oldTree.levelOrder();

        BTNode newNode = createSymmetrical(oldNode);
        BTree newBTree = new BTree();
        newBTree.setRootNode(newNode);
        newBTree.levelOrder();
    }

    private BTNode createSymmetrical(BTNode rootNode){
        if(rootNode == null){
            return null;
        }

        BTNode newNode = new BTNode();
        BeanUtils.copyProperties(rootNode,newNode);

        createSymmetricalCore(newNode);
        return newNode;
    }

    private void createSymmetricalCore(BTNode newNode){
        if(newNode != null){
            BTNode temp = newNode.lchild;
            newNode.lchild = newNode.rchild;
            newNode.rchild = temp;

            // 递归左节点
            createSymmetricalCore(newNode.lchild);
            // 递归右节点
            createSymmetricalCore(newNode.rchild);
        }
    }
}
