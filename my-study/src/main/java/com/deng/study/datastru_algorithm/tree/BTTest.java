package com.deng.study.datastru_algorithm.tree;

import com.deng.common.enums.ThreadClassEnum;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-11-20 18:27:58
 */
public class BTTest {

    /**
     *          A
     *        /  \
     *       B    C
     *      /    / \
     *     D    E   F
     *     \
     *      G
     *
     * @param args
     */
    public static void main(String[] args) {
        String str = "A(B(D(,G)),C(E,F))";

        BTree bTree = new BTree();
        bTree.createBTree(str);
//
//        String nodeStr = bTree.getNodeStr(bTree.getRootNode());
//        System.out.println(nodeStr);
////
////        int level = bTree.getLevel2(bTree, 'G');
////        System.out.println("节点所在层次：" + level);
////
////        bTree.preOrder();
//        String s = bTree.preOrderSeq();
//        System.out.println("序列化后的字符串为："+ s);
//
//        BTree treeBySeq = bTree.createTreeBySeq(s);
//        treeBySeq.preOrder();

//        String ancestor = bTree.getAncestor(bTree, 'G');
//        System.out.println("第一种办法获取节点的祖先：" + ancestor);

//        String ancestor2 = bTree.getAncestor2(bTree, 'G');
//        System.out.println("第二种办法获取节点的祖先：" + ancestor2);

//        String ancestor3 = bTree.getAncestor3(bTree, 'G');
//        System.out.println("第三种办法获取节点的祖先：" + ancestor3);

//        String ancestor4 = bTree.getAncestor4(bTree, 'G');
//        System.out.println("第四种办法获取节点的祖先：" + ancestor4);
//
//        String ancestor5 = bTree.getAncestor5(bTree, 'G');
//        System.out.println("第五种办法获取节点的祖先：" + ancestor5);

//
//        bTree.noRecursionPreOrder();
//        bTree.noRecursionPreOrder2();
        bTree.noRecursionMiddleOrder();
//        bTree.noRecursionPostOrder();
//        bTree.levelOrder();

//        int width = bTree.getWidth(bTree);
//        System.out.println("width：" + width);
//
//        for (int i = 1; i < 6; i++) {
//            System.out.println("第" + (i) + "层的节点个数为：" + bTree.getKCount(bTree, i));
//        }
//        System.out.println("---------------------");
//        for (int i = 1; i < 6; i++) {
//            System.out.println("第" + (i) + "层的节点个数为：" + bTree.getKCount2(bTree, i));
//        }
//        for (int i = 1; i < 6; i++) {
//            System.out.println("第" + (i) + "层的节点个数为：" + bTree.getKCount4(bTree, i));
//        }
    }

    @Test
    public void test(){
//        String pre = "ABDGCEF";
//        String middle = "DGBAECF";
//        BTree bTree = new BTree();
//        bTree = bTree.createTreeByPreAndMiddleOrder(pre, middle);
//        System.out.println(bTree);
//        bTree.preFront();

        String middle = "DGBAECF";
        String last = "GDBEFCA";
        BTree bTree = new BTree();
        bTree = bTree.createTreeByMidAndLastOrder(last,middle);
        System.out.println(bTree);
        bTree.postOrder();
    }

    /**
     *          A
     *        /  \
     *       B    C
     *      /    / \
     *     D    E   F
     *     \       /
     *      G     H
     */
    @Test
    public void testThread(){
        BTNode<Character> rootNode = new BTNode<>('A');
        BTNode<Character> node1 = new BTNode<>('B');
        BTNode<Character> node2 = new BTNode<>('C');
        BTNode<Character> node3 = new BTNode<>('D');
        BTNode<Character> node4 = new BTNode<>('E');
        BTNode<Character> node5 = new BTNode<>('F');
        BTNode<Character> node6 = new BTNode<>('G');
        BTNode<Character> node7 = new BTNode<>('H');

        rootNode.setLchild(node1);
        rootNode.setRchild(node2);
        node1.setLchild(node3);
        node2.setLchild(node4);
        node2.setRchild(node5);
        node3.setRchild(node6);
        node5.setRchild(node7);

//        rootNode.preOrder(rootNode);
//        System.out.println("---------------");

//        rootNode.middleOrder(rootNode);
//        System.out.println("---------------");
//
        rootNode.postOrder(rootNode);
        System.out.println("---------------");

        ThreadClass threadClass = new ThreadClass();
        threadClass.setThreadClassType(ThreadClassEnum.POST);
        threadClass.setRoot(rootNode);
        threadClass.createThread();
        threadClass.threadOrder();
    }

    @Test
    public void 二叉搜索树中第K小的元素(){
        BTNode<Integer> rootNode = new BTNode<>(3);
        BTNode<Integer> node1 = new BTNode<>(1);
        BTNode<Integer> node2 = new BTNode<>(4);
        BTNode<Integer> node3 = new BTNode<>(2);

        rootNode.setLchild(node1);
        rootNode.setRchild(node2);

        node1.setRchild(node3);

        System.out.println(get二叉搜索树中第K小的元素(rootNode,2));
    }

    private int get二叉搜索树中第K小的元素(BTNode root,int k){
        Deque<BTNode> queue = new ArrayDeque<>();
        while(Objects.nonNull(root) || !queue.isEmpty()){
            while(Objects.nonNull(root)){
                queue.push(root); // 添加到队头
                root = root.lchild;
            }
            root = queue.poll(); // 从队头取
            k--;
            if(k == 0){
                break;
            }
            root = root.rchild;
        }
        return (Integer)root.data;
    }
}
