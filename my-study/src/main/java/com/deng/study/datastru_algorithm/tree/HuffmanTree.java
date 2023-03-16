package com.deng.study.datastru_algorithm.tree;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @Desc: 哈夫曼树
 * @Author: dengyanliang
 * @Date: 2022-12-03 20:02:30
 */
public class HuffmanTree<T> {

    private String str;                 // 传入的字符串
    private double[] weights;           // 权值数组
    private int leafCount;              // 节点个数，跟权值数据总数保持一致
    private int maxNodeCount;           // 结点总数
    private HuffmanNode[] huffmanNodes; // 存放哈夫曼结点
    private String[] huffmanCodes;      // 存放哈夫曼编码

    public HuffmanTree(String str, double[] weights) {
        this.str = str;
        this.weights = weights;
        this.leafCount = weights.length;
        this.maxNodeCount = 2 * leafCount - 1; // 哈夫曼树的结点总数，最多只有 2*leafCount-1个
        this.huffmanNodes = new HuffmanNode[maxNodeCount];
        this.huffmanCodes = new String[leafCount];
    }

    public void createHuffmanTree() {
        Comparator<HuffmanNode> comparator = new Comparator<HuffmanNode>() {
            @Override
            public int compare(HuffmanNode o1, HuffmanNode o2) {
                return Double.compare(o1.getWeight(), o2.getWeight());
            }
        };
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(maxNodeCount, comparator);

        // 创建哈夫曼的叶子结点
        for (int i = 0; i < leafCount; i++) {
            huffmanNodes[i] = new HuffmanNode();
            huffmanNodes[i].weight = weights[i];
            huffmanNodes[i].data = str.charAt(i);

            queue.offer(huffmanNodes[i]);
        }

        // 创建哈夫曼树
        // 从leafCount开始，是因为叶子结点进队后，索引下标刚好是[0...leafCount-1]
        for (int i = leafCount; i < maxNodeCount; i++) {
            HuffmanNode node1 = queue.poll();  // 出队两个权值最小的结点
            HuffmanNode node2 = queue.poll();

            huffmanNodes[i] = new HuffmanNode();
            node1.parent = huffmanNodes[i];
            node2.parent = huffmanNodes[i];

            huffmanNodes[i].weight = node1.weight + node2.weight; // 新结点等于出队两个结点之和

            huffmanNodes[i].lchild = node1;
            node1.flag = true;                   // 左孩子为true

            huffmanNodes[i].rchild = node2;
            node2.flag = false;                 // 右孩子为false

            queue.offer(huffmanNodes[i]);
        }
    }

    public void createHuffmanCode() {
        for (int i = 0; i < leafCount; i++) {
            HuffmanNode node = huffmanNodes[i];
            StringBuilder str = new StringBuilder();
            while(Objects.nonNull(node.parent)){
                if(node.flag){
                    str.append("0");
                }else{
                    str.append("1");
                }
                node = node.parent;
            }
            huffmanCodes[i] = reverse(str.toString());
        }
    }

    private String reverse(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        StringBuilder tempStr = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            tempStr.append(str.charAt(i));
        }
        return tempStr.toString();
    }

    public void displayHufuffman() {
        for (int i = 0; i < leafCount; i++) {
            System.out.println(huffmanNodes[i].data + " " + huffmanCodes[i]);
        }
    }
}
