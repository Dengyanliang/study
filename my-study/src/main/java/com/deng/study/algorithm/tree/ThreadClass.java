package com.deng.study.algorithm.tree;

import java.util.Objects;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-12-03 11:17:49
 */
public class ThreadClass {
    BTNode b; // 二叉树的跟结点
    BTNode root; // 线索二叉树的头结点
    BTNode pre;  // 用于中序线索化，指向中序前驱结点
    String bStr;

    public void createThread(){
        root = new BTNode();
        root.ltag = 0;
        root.rtag = 1;

        if(b == null){
            root.lchild = root;
            root.rchild = null;
        }else{
            root.lchild = b;
            pre = root;
            createThread(b);
            pre.rchild = root;
            pre.rtag = 1;
            root.rchild = pre;
        }
    }

    public void createThread(BTNode p){
        if(Objects.nonNull(p)){
            createThread(p.lchild);
            if(p.lchild == null){
                p.lchild = pre;
                p.ltag = 1;
            }else{
                p.ltag = 0;
            }

            if(p.rchild == null){
                pre.lchild = p;
                pre.rtag = 1;
            }else{
                pre.rtag = 0;
            }
            pre = p;
            createThread(p.rchild);
        }
    }

}
