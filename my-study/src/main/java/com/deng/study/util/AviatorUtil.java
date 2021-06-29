package com.deng.study.util;


import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AviatorUtil {

    /**
     * 带入参数求和 execute用法
     * 直接执行
     */
    @Test
    public void test1(){
        String expression = "a + b + c";
        Map<String,Object> map = new HashMap<>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        long result = (long)AviatorEvaluator.execute(expression, map);
        System.out.println("result:"+result);
    }



    /**
     * 带入参数求和 compile用法
     * 先生成Expression，再执行
     */
    @Test
    public void test2(){
        String expression = "a + b + c";
        // 这样设置后，下次编译同样的表达式，aviator会从全局缓存中取出已经编译好的结果，不需要动态编译，提高了效率
        Expression compileExp = AviatorEvaluator.compile(expression);

        Map<String,Object> map = new HashMap<>();
        map.put("a",1);
        map.put("b",2);
        map.put("c",3);
        long result = (long)compileExp.execute(map);
        System.out.println("result:"+result);
    }

    /**
     * 自定义函数
     */
    @Test
    public void test3(){
        AviatorEvaluator.addFunction(new MinFuction());
        String expression = "min(a,b)";
        Expression complieExp = AviatorEvaluator.compile(expression,true);
        Map<String,Object> evn = new HashMap<>();
        evn.put("a",99.3);
        evn.put("b",50);
        Object execute = complieExp.execute(evn);
        System.out.println(execute);
    }

    static class MinFuction extends AbstractFunction{
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2,env);

            return new AviatorBigInt(Math.min(left.doubleValue(),right.doubleValue()));
        }

        @Override
        public String getName() {
            return "min";
        }
    }





}
