package com.deng.study.java.aviator;


import com.deng.study.domain.BaseContext;
import com.deng.study.domain.User;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AviatorTest {

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
        List<String> variableNames = compileExp.getVariableNames();
        for (String variableName : variableNames) {
            System.out.println("-----variableName:" + variableName); // 取得所有的变量名
        }

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

    @Test
    public void test4(){
        AviatorEvaluatorInstance instance = AviatorEvaluator.getInstance();
        instance.addFunction(new ReqFunction());

        String expression = "$(req.name) == 'zhangsan'";
        Expression complieExp = instance.compile(expression);

        List<String> variableNames = complieExp.getVariableNames();
        for (String variableName : variableNames) {
            System.out.println("----variableName:"+variableName);
        }

        User user = User.getSimpleUser();
        BaseContext<User> context = new BaseContext<>(user);

        Object result = complieExp.execute(context.getRunData());
        System.out.println(result);
    }


    static class ReqFunction extends AbstractFunction{

        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1) {
            Object value = FunctionUtils.getJavaObject(arg1,env);
            return AviatorRuntimeJavaType.valueOf(value);
        }

        @Override
        public String getName() {
            return "$";
        }
    }

    @Test
    public void test5(){
        String expression = "'createInternalOrderNewRule'";
        Expression complieExp = AviatorEvaluator.compile(expression);

        BaseContext context = new BaseContext(new Object());
        Object execute = complieExp.execute(context.getRunData());
        System.out.println(execute); // createInternalOrderNewRule 处理完之后，去掉了 ''
    }

}
