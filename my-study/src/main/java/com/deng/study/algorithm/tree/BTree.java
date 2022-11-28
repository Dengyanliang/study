package com.deng.study.algorithm.tree;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.util.*;

/**
 * @Desc:
 * @Author: dengyanliang
 * @Date: 2022-11-20 18:26:54
 */
@Data
public class BTree {
    private BTNode<Character> rootNode;
    private String bStr = "";
    private String ans = "";
    private int kSumCount = 0;

    /**
     * 创建二叉树
     * @param str
     */
    public void createBTree(String str){
        Stack<BTNode> st = new Stack<>();
        BTNode<Character> tempNode = null;
        char c;
        int i = 0;
        boolean flag = true; // 用于处理左右孩子
        while (i < str.length()){
            c = str.charAt(i);
            switch (c){
                case '(' :      // 刚刚新建的结点有孩子，因此需要将其进栈
                    st.push(tempNode);
                    flag = true;  // 这里很关键，一定不能少
                    break;
                case ')':       // 栈顶元素的子树处理完毕，退栈
                    st.pop();
                    break;
                case ',':      // 开始处理栈顶结点的右孩子
                    flag = false;
                    break;
                default:
                    tempNode = new BTNode<>();
                    tempNode.setData(c);
                    if (rootNode == null) {    // 没有建立根结点，则将新建的结点作为根结点
                        rootNode = tempNode;
                    } else {                   // 已经建立了根结点
                        if (flag) {            // 新结点p作为栈顶结点的左子树
                            if (!st.isEmpty()) {
                                st.peek().lchild = tempNode;
                            }
                        } else {               // 新结点p作为栈顶结点的右子树
                            if (!st.isEmpty()) {
                                st.peek().rchild = tempNode;
                            }
                        }
                    }
            }
            i++;
        }
    }

    public String getNodeStr(BTNode<Character> node){
        if(Objects.nonNull(node)){
            bStr += node.data;
            if(node.lchild != null || node.rchild != null){
                bStr += "(";
                getNodeStr(node.lchild);
                if(node.rchild != null)
                    bStr += ",";
                getNodeStr(node.rchild);
                bStr += ")";
            }
        }
        return bStr;
    }


    /**
     * 获取指定结点所在的层次：根结点所在层次计为1
     * @param bTree
     * @param x
     * @return
     */
    public int getLevel(BTree bTree, char x){
        return getLevel(bTree.rootNode, x, 1); // 从根开始遍历
    }

    private int getLevel(BTNode<Character> node,char x, int h){
        if(Objects.isNull(node)){
            return 0;   // 空树不能找到根结点
        }else{
            if(node.data == x){ // 根结点即为所找，返回其层次
                return h;
            }else{
                int level = getLevel(node.lchild, x, h + 1); // 在左子树中查找
                if(level != 0)
                    return level;  // 在左子树中找到了，返回其层次
                else
                    return getLevel(node.rchild,x,h+1); // 在右子树中查找
            }
        }
    }

    public int getLevel2(BTree bTree,char x){
        return getLevel2(bTree.rootNode,x);
    }

    private int getLevel2(BTNode<Character> node,char x){
        if(Objects.isNull(node)){
            return 0;
        }
        if(node.data == x){
            return 1;
        }
        int left = (Objects.isNull(node.lchild)) ? 0 : getLevel2(node.lchild,x);
        int right = (Objects.isNull(node.rchild)) ? 0 : getLevel2(node.rchild,x);
        return Math.max(left,right) + 1;
    }

    public void noRecursionPreOrder(){
        if(Objects.isNull(this.rootNode)){
            return;
        }
        this.rootNode.noRecursionPreOrder(this.rootNode);
    }

    public void noRecursionPreOrder2(){
        if(Objects.isNull(this.rootNode)){
            return;
        }
        this.rootNode.noRecursionPreOrder2(this.rootNode);
    }

    public void noRecursionMiddleOrder(){
        if(Objects.isNull(this.rootNode)){
            return;
        }
        this.rootNode.noRecursionMiddleOrder(this.rootNode);
    }
    public void noRecursionPostOrder(){
        if(Objects.isNull(this.rootNode)){
            return;
        }
        this.rootNode.noRecursionPostOrder(this.rootNode);
    }

    public void preFront(){
        System.out.println("先序遍历：");
        rootNode.preOrder(this.rootNode);
        System.out.println();
        System.out.println("先序遍历结束。。。");
    }

    /**
     * 层序遍历
     */
    public void levelOrder(){
        System.out.println("层序遍历：");
        rootNode.levelOrder(this.rootNode);
        System.out.println();
        System.out.println("层序遍历结束。。。");
    }

    public void trans(String str){

    }

    private void trans(){

    }

    /**
     * 求结点的所有祖先
     * @param bt 传入的树结构
     * @param x  结点
     * @return   结点的所有祖先
     */
    public String getAncestor(BTree bt, char x){
        getAncestor(bt.rootNode,x);
        return ans;
    }
    private boolean getAncestor(BTNode<Character> node, char x){
        if(Objects.isNull(node)){
            return false;
        }else{
            if(Objects.nonNull(node.lchild)){   // 如果存在左子树
                if(node.lchild.data == x){      // 左子树的结点值和入参结点相等，则把当前结点加入到祖先中
                    ans += node.data + " ";
                    return true;
                }
            }
            if(Objects.nonNull(node.rchild)){   // 如果存在右子树
                if(node.rchild.data == x){      // 右子树的结点值和入参结点相等，则把当前结点加入到祖先中
                    ans += node.data + " ";
                    return true;
                }
            }
            if (getAncestor(node.lchild, x) || getAncestor(node.rchild, x)) { // 祖先的祖先
                ans += node.data + " ";
                return true;
            }
        }
        return false;
    }

    /**
     * 使用集合的方式获取
     * @param bt 传入的树
     * @param x  要查询的结点
     * @return  查找结点的所有祖先
     */
    public String getAncestor2(BTree bt, char x){
        List<Character> path = new ArrayList<>();
        getAncestor2(bt.rootNode,x ,path);
        return ans;
    }

    private void getAncestor2(BTNode<Character> node, Character x, List<Character> path) {
        if (Objects.isNull(node)) {
            return;
        }
        path.add(node.data);
        if (node.data == x) {
            path.remove(path.size() - 1); // 相等时，删除当前结点，留下当前结点的所有父结点
            ans = path.toString();              // 给结果赋值
            return;
        }
        getAncestor2(node.lchild, x, path);
        getAncestor2(node.rchild, x, path);
        path.remove(path.size() - 1);
    }

    public String getAncestor3(BTree bTree, char x){
        char[] path = new char[100];
        int d = -1; // path[0..d]存放根结点到x的路径
        getAncestor3(bTree.rootNode, x, path, d);
        return ans;
    }

    private void getAncestor3(BTNode<Character> node, Character x, char[] path, int d) {
        if(Objects.isNull(node)){
            return;
        }
        // 不空时
        d++;
        path[d] = node.data;
        if(x == node.data){
            for (int i = 0; i < d; i++) { // 取0~~d-1的数据，也就是x的所有祖先
                ans += path[i] + " ";
            }
            return;
        }
        getAncestor3(node.lchild,x,path,d);  // 每一次递归，栈中存放的是当时的实参值
        getAncestor3(node.rchild,x,path,d);
    }

    /**
     * 使用非递归后续遍历获取x的所有祖先
     * @param bTree 当前树
     * @param x     要比对的值
     * @return      x的所有祖先
     */
    public String getAncestor4(BTree bTree, char x){
        Stack<BTNode<Character>> stack = new Stack<>();
        BTNode<Character> currentNode = bTree.rootNode;
        BTNode<Character> tempNode;
        boolean flag;
        do {
            while(Objects.nonNull(currentNode)){
                stack.push(currentNode);
                currentNode = currentNode.lchild;
            }
            tempNode = null;
            flag = true;
            while(!stack.isEmpty() && flag){
                currentNode = stack.peek();
                if(currentNode.rchild == tempNode){
                    if(currentNode.data == x){
                        stack.pop();    // 把当前结点删除。栈中剩余元素就是当前结点的所有祖先
                        while (!stack.isEmpty())
                            ans += stack.pop().data + " ";
                        return ans;
                    }else{
                        stack.pop();
                        tempNode = currentNode;
                    }
                }else{
                    currentNode = currentNode.rchild;
                    flag = false;
                }
            }

        } while (!stack.isEmpty());

        return "";
    }

    public int getWidth(BTree bTree){
        int maxLevel = 100;                   // 最大层数
        int[] w = new int[maxLevel];          // 存放每一层的结点个数
        getWidth(bTree.rootNode, 1, w);      // 从第1层遍历
        int width = 0;
        for (int i = 0; i < maxLevel; i++) {  // 求w中的最大元素
            if(width < w[i])
                width = w[i];
        }
        return width;
    }
    private void getWidth(BTNode<Character> node, int h, int[] w){
        if(Objects.isNull(node)){
            return;
        }
        w[h]++;                         // 第h层的结点个数加1
        getWidth(node.lchild,h+1,w); // 遍历左子树
        getWidth(node.rchild,h+1,w); // 遍历右子树
    }

    /**
     * 求第k层的结点个数
     * @param bTree 当前树
     * @param k     第k层
     * @return      第k层的结点总数
     */
    public int getKCount(BTree bTree,int k){
        kSumCount = 0;      // 每次调用该方法时，都要进行初始化，不然kSumCount在外层是个循环的情况下会累加
        getKCount(bTree.rootNode, 1, k);  // 从第1层开始遍历
        return kSumCount;
    }


    private void getKCount(BTNode<Character> node, int h, int k){
        if(Objects.isNull(node)){
            return;
        }
        if(h == k){
            kSumCount++;
        }
        getKCount(node.lchild,h+1,k);
        getKCount(node.rchild,h+1,k);
    }

    /**
     * 使用层序遍历获取k层的结点个数
     * @param bTree
     * @param k
     * @return
     */
    public int getKCount2(BTree bTree,int k){
        int count = 0;
        Queue<QNode<Character>> queue = new LinkedList<>();
        QNode<Character> currentNode;
        queue.offer(new QNode<Character>(1,bTree.rootNode));

        while(!queue.isEmpty()){
            currentNode = queue.poll();
            if(currentNode.level > k)
                return count;
            if(currentNode.level == k){
                count++;
            }else {
                if(Objects.nonNull(currentNode.node.lchild)){
                    queue.offer(new QNode<>(currentNode.level+1,currentNode.node.lchild));
                }
                if(Objects.nonNull(currentNode.node.rchild)){
                    queue.offer(new QNode<>(currentNode.level+1,currentNode.node.rchild));
                }
            }
        }
        return count;
    }

    public int getKCount3(BTree bTree,int k){
        int count = 0;
        Queue<BTNode<Character>> queue = new LinkedList<>();
        BTNode<Character> currentNode,tempNode = null;
        int curl = 1;                   // 当前层次，从1开始
        BTNode<Character> last;         // 当前层的最右结点
        last = bTree.rootNode;          // 第一层的最右结点，也就是根结点
        queue.offer(bTree.rootNode);    // 根结点进队
        while (!queue.isEmpty()){
            if(curl > k)                // 当层号大于k时返回count，不再继续
                return count;
            currentNode = queue.poll(); // 出队一个结点
            if(curl == k){              // 当前结点是第k层的结点，加1
                count++;
            }
            if(Objects.nonNull(currentNode.lchild)){
                tempNode = currentNode.lchild;
                queue.offer(tempNode);
            }
            if(Objects.nonNull(currentNode.rchild)){
                tempNode = currentNode.rchild;
                queue.offer(tempNode);
            }
            if(currentNode == last){     // 当前层的所有结点处理完毕
                last = tempNode;         // 让last指向下一层的最右结点
                curl++;
            }
        }
        return count;
    }
}
