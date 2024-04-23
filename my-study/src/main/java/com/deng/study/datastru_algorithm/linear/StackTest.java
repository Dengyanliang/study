package com.deng.study.datastru_algorithm.linear;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/7 15:10
 */
public class StackTest {


    @Test
    public void test括号的最大嵌套深度(){
//        String s =  "(1+(2*3)+((8)/4))+1";
        String s = "(1)+((2))+(((3)))";
        Stack<Character> stack = new Stack<>();
        int maxDeep = 0,deep = 0;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if(c == '('){
                stack.push(c);
                deep++;
            }
            if(c == ')'){
                stack.pop();
                deep--;
            }
            if(maxDeep < deep){
                maxDeep = deep;
            }
        }
        System.out.println(maxDeep);

    }
}
