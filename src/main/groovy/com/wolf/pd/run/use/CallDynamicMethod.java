package com.wolf.pd.run.use;

import com.wolf.pd.run.other.DynamicGroovyClass;

/**
 * Description: java中调用groovy的动态方法
 * Created on 2021/5/2 10:11 PM
 *
 * @author 李超
 * @version 0.0.1
 */
public class CallDynamicMethod {
    public static void main(String[] args) {
        groovy.lang.GroovyObject instance = new DynamicGroovyClass();
        // 动态调用groovy中的任意方法
        Object result1 = instance.invokeMethod("squeak", new Object[]{});// invokeMethod可以用来调用groovy中使用元编程动态定义的方法
        System.out.println("Received: " + result1);

        Object result2 = null;
        try {
            result2 = instance.invokeMethod("quack", new Object[]{"like", "a", "duck"});// 可能出异常
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("Received: " + result2);
    }
}

