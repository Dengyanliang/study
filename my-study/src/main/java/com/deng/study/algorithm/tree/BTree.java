package com.deng.study.algorithm.tree;

        import lombok.Data;

        import java.util.Objects;
        import java.util.Stack;

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
                case '(' :      // 刚刚新建的节点有孩子，因此需要将其进栈
                    st.push(tempNode);
                    flag = true;  // 这里很关键，一定不能少
                    break;
                case ')':       // 栈顶元素的子树处理完毕，退栈
                    st.pop();
                    break;
                case ',':      // 开始处理栈顶节点的右孩子
                    flag = false;
                    break;
                default:
                    tempNode = new BTNode<>();
                    tempNode.setData(c);
                    if (rootNode == null) {    // 没有建立根节点，则将新建的节点作为根节点
                        rootNode = tempNode;
                    } else {                   // 已经建立了根节点
                        if (flag) {            // 新节点p作为栈顶节点的左子树
                            if (!st.isEmpty()) {
                                st.peek().lchild = tempNode;
                            }
                        } else {               // 新节点p作为栈顶节点的右子树
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
     * 获取指定节点所在的层次：根节点所在层次计为1
     * @param bTree
     * @param x
     * @return
     */
    public int getLevel(BTree bTree, char x){
        return getLevel(bTree.rootNode, x, 1); // 从根开始遍历
    }

    private int getLevel(BTNode<Character> node,char x, int h){
        if(Objects.isNull(node)){
            return 0;   // 空树不能找到根节点
        }else{
            if(node.data == x){ // 根节点即为所找，返回其层次
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

    public void preFront(){
        System.out.println("先序遍历：");
        preFront(this.getRootNode());
    }

    public void preFront(BTNode node){
        System.out.print(node.data + " ");
        if(node.lchild != null){
            preFront(node.lchild);
        }
        if(node.rchild != null){
            preFront(node.rchild);
        }
    }

    public void trans(String str){

    }

    private void trans(){

    }

    /**
     * 求节点的所有祖先
     * @param bt 传入的树结构
     * @param x  节点
     * @return   节点的所有祖先
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
                if(node.lchild.data == x){      // 左子树的节点值和入参节点相等，则把当前节点加入到祖先中
                    ans += node.data + " ";
                    return true;
                }
            }
            if(Objects.nonNull(node.rchild)){   // 如果存在右子树
                if(node.rchild.data == x){      // 右子树的节点值和入参节点相等，则把当前节点加入到祖先中
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



}
