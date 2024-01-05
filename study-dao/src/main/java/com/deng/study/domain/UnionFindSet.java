package com.deng.study.domain;

/**
 * @Desc: 构造无向图，并根据无向图确定链各个顶点之间的连接关系，因此可以使用并查集求解
 *          这里还不是很明白为啥要这么构造
 * @Auther: dengyanliang
 * @Date: 2023/7/13 21:34
 */
public class UnionFindSet {
    public int[] fa;

    public UnionFindSet(int n) {
        this.fa = new int[n];
        for (int i = 0; i < n; i++) {
            fa[i] = i;
        }
    }

    public int find(int x){
        if(this.fa[x] != x){
            return this.fa[x] = this.find(fa[x]);
        }
        return x;
    }
    public void union(int x,int y){
        int x_fa = this.find(x); // 查找索引x对应的值
        int y_fa = this.find(y); // 查找索引y对应的值
        if(x_fa != y_fa){
            this.fa[y_fa] = x_fa; // 把这两个索引对应的值更新为一样
        }
    }
}
