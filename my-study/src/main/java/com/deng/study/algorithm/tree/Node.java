package com.deng.study.algorithm.tree;

import lombok.Data;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/**
 * @Desc:树的结点
 * @Auther: dengyanliang
 * @Date: 2021/2/7 15:01
 */
@Data
public class Node {
    private int no;
    private String name;
    private Node left;
    private Node right;


    public Node(int no, String name) {
        this.no = no;
        this.name = name;
    }


    /**
     * 前序遍历
     */
    public void preFront(){
        System.out.println(this);
        if(this.left != null){
            this.left.preFront();
        }
        if(this.right != null){
            this.right.preFront();
        }
    }

    /**
     * 中序遍历
     */
    public void midFront(){
        if(this.left != null){
            this.left.midFront();
        }
        System.out.println(this);
        if(this.right != null){
            this.right.midFront();
        }
    }

    /**
     * 后序遍历
     */
    public void lastFront(){
        if(this.left != null){
            this.left.lastFront();
        }
        if(this.right != null){
            this.right.lastFront();
        }
        System.out.println(this);
    }

    /**
     * 层序遍历
     * 借助队列，先把根节点加入队列，然后取出根节点，再判断根节点的左右节点，是否为空，如果不为空则加入队列末尾，再取出队列的值进行判断
     * @return
     */
    public void levelOrder(){
        Queue<Node> queue = new ArrayDeque();
        queue.add(this);
        Node node = queue.poll();
        while(node != null){
            System.out.println(node);
            if(node.left != null){
                queue.add(node.left);
            }
            if(node.right != null){
                queue.add(node.right);
            }
            node = queue.poll();
        }
    }


    /**
     * 前序查找
     * @param no
     * @return
     */
    public Node preSearch(int no){
        System.out.println("进入前序查找");
        // 比较当前节点是不是
        if(this.no == no){
            return this;
        }
        // 如果不是，进入左序遍历
        Node resultNode = null;
        if(this.left != null){
            resultNode = this.left.preSearch(no);
        }
        if(resultNode != null){
            return resultNode;
        }
        // 进入后序遍历
        if(this.right != null){
            resultNode = this.right.preSearch(no);
        }
        return resultNode;
    }


    public Node midSearch(int no){
        Node resultNode = null;
        if(this.left != null){
            resultNode = this.left.midSearch(no);
        }
        if(resultNode != null){
            return resultNode;
        }

        System.out.println("进入中序查找");
        if(this.no == no){
            return this;
        }

        if(this.right != null){
            resultNode = this.right.midSearch(no);
        }
        return resultNode;
    }

    public Node lastSearch(int no){
        Node resultNode = null;
        if(this.left != null){
            resultNode = this.left.lastSearch(no);
        }
        if(resultNode != null){
            return resultNode;
        }
        if(this.right != null){
            resultNode = this.right.lastSearch(no);
        }
        if(resultNode != null){
            return resultNode;
        }

        System.out.println("进入后序查找");
        if(this.getNo() == no){
            return this;
        }
        return resultNode;
    }

    /**
     * 使用先序遍历拷贝新树
     * @param node
     * @return
     */
    public Node copyNodeByPreFront(Node node){
        if(Objects.nonNull(node)){
            Node rootNode = new Node(node.getNo(),"pre-"+node.getName()); // 拷贝根节点
            Node leftNode = copyNodeByPreFront(node.getLeft());   // 拷贝左子树
            Node rightNode = copyNodeByPreFront(node.getRight()); // 拷贝右子树

            rootNode.setLeft(leftNode);
            rootNode.setRight(rightNode);

            return rootNode;
        }
        return null;
    }

    /**
     * 使用后续遍历拷贝新树
     * @param node
     * @return
     */
    public Node copyNodeByLastFront(Node node){
        if(Objects.nonNull(node)){
            Node leftNode = copyNodeByLastFront(node.getLeft());   // 拷贝左子树
            Node rightNode = copyNodeByLastFront(node.getRight()); // 拷贝右子树

            Node rootNode = new Node(node.getNo(),"last-"+node.getName()); // 创建根节点
            rootNode.setLeft(leftNode);
            rootNode.setRight(rightNode);
            return rootNode;
        }
        return null;
    }



    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
