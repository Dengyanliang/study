package com.deng.study.algorithm.tree;

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

        String nodeStr = bTree.getNodeStr(bTree.getRootNode());
        System.out.println(nodeStr);

        int level = bTree.getLevel(bTree, 'G');
        System.out.println("节点所在层次：" + level);

        bTree.preFront();
        System.out.println("-----------------");

        String ancestor = bTree.getAncestor(bTree, 'F');
        System.out.println("节点的祖先：" + ancestor);
    }
}
